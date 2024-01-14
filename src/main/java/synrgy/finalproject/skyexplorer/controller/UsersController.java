package synrgy.finalproject.skyexplorer.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import synrgy.finalproject.skyexplorer.exception.UsersNotFoundException;
import synrgy.finalproject.skyexplorer.model.dto.UsersDTO;
import synrgy.finalproject.skyexplorer.model.dto.request.LoginRequest;
import synrgy.finalproject.skyexplorer.model.dto.response.JwtResponse;
import synrgy.finalproject.skyexplorer.model.dto.response.SuccessResponse;
import synrgy.finalproject.skyexplorer.model.entity.Users;
import synrgy.finalproject.skyexplorer.security.jwt.JwtUtils;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsImpl;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsServiceImpl;
import synrgy.finalproject.skyexplorer.service.ResetPasswordService;
import synrgy.finalproject.skyexplorer.service.UsersService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerBasic(@Valid @RequestBody UsersDTO usersDTO) {
        try {
            Users savedUser = usersService.saveUser(usersDTO);
            if (savedUser != null) {
                usersService.sendOTPEmail(savedUser.getEmail());
                return SuccessResponse.generateResponse("succes", "Success user data registered", savedUser, HttpStatus.OK);
            } else {
                return SuccessResponse.generateResponse("fail", "Failed to register user. Please provide valid data.", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<Object> verifyOTP(@RequestParam String email, @RequestBody Map<String, String> requestBody) {
        try {
            String otpCode = requestBody.get("otpCode");
            Users user = usersService.findUserByEmail(email);

            if (user != null) {
                boolean isValidOTP = usersService.verifyOTP(user, otpCode);

                if (isValidOTP) {
                    return SuccessResponse.generateResponse("succes", "OTP verification successful. isOTPVerified updated to true.", isValidOTP, HttpStatus.OK);
                } else {
                    return SuccessResponse.generateResponse("fail", "Invalid OTP. Verification failed.", null, HttpStatus.BAD_REQUEST);
                }
            }

            return SuccessResponse.generateResponse("fail", "User not found with the provided email.", null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<Object> resendOTP(@RequestParam String email) {
        try {
            String newOTP = usersService.resendOTPEmail(email);
            if (newOTP != null) {
                return SuccessResponse.generateResponse("succes", "New OTP sent successfully", newOTP, HttpStatus.OK);
            } else {
                return SuccessResponse.generateResponse("fail", "User not found or OTP could not be sent", null, HttpStatus.NOT_FOUND);
            }
        }  catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @PostMapping("/setPassword")
    public ResponseEntity<Object> setPassword(@RequestParam String email, @RequestBody Map<String, String> requestBody) {
        try {
            String password = requestBody.get("password");
            Users user = usersService.setPassword(email, password);
            if (user != null && user.isRegistrationComplete()) {
                String role = user.getRole().getName();
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                String token = jwtUtils.generateToken(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
                JwtResponse jwtResponse = new JwtResponse(token, email, role);
                return SuccessResponse.generateResponse("succes", "Password set successfully. Registration completed.", jwtResponse, HttpStatus.OK);
            } else {
                return SuccessResponse.generateResponse("fail", "Failed to set password or user not found.", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<Object> requestResetPassword(@RequestBody UsersDTO usersDTO) {
        String email = usersDTO.getEmail();
        try {
            resetPasswordService.updateRessetPassword(email);
            return SuccessResponse.generateResponse("succes", "Link reset password telah dikirim ke email Anda. Silakan periksa inbox Anda.", email, HttpStatus.OK);
        } catch (UsersNotFoundException e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @RequestBody Map<String, String> requestBody) {
        String newPassword = requestBody.get("newPassword");

        Users users = resetPasswordService.get(token);

        if (users != null) {
            resetPasswordService.updatePassword(users, newPassword);
            return SuccessResponse.generateResponse("succes", "Password berhasil direset.", null, HttpStatus.OK);
        } else {
            return SuccessResponse.generateResponse("fail", "Token tidak valid atau telah kadaluwarsa.", null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager
                .authenticate( new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getUsername(), role);

        return SuccessResponse.generateResponse("succes", "Login successfully.", jwtResponse, HttpStatus.OK);
    }
}

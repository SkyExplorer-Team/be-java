package synrgy.finalproject.skyexplorer.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
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
import synrgy.finalproject.skyexplorer.model.provider.AuthProvider;
import synrgy.finalproject.skyexplorer.security.jwt.JwtUtils;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsImpl;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsServiceImpl;
import synrgy.finalproject.skyexplorer.service.ResetPasswordService;
import synrgy.finalproject.skyexplorer.service.UsersService;
import synrgy.finalproject.skyexplorer.service.Validator;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {


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

    @Autowired
    private Validator validator;

    @PostMapping("/register")
    @CrossOrigin(origins = {"http://be-java-production.up.railway.app/api", "https://be-java-production.up.railway.app", "http://localhost:3000"})
    public ResponseEntity<Object> registerBasic(@RequestBody UsersDTO req) {
        validator.validate(req);
        try {
            req.setAuthProvider(AuthProvider.local);
            Users savedUser = usersService.saveUser(req);
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
    @CrossOrigin(origins = {"http://be-java-production.up.railway.app", "https://be-java-production.up.railway.app"})
    public ResponseEntity<Object> verifyOTP(@RequestParam String email, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{\"otpCode\":\"Masukan OTP dari email\"}")
            )
    ) @RequestBody Map<String, String> requestBody) {
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
    @CrossOrigin(origins = {"http://be-java-production.up.railway.app", "https://be-java-production.up.railway.app"})
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
    @CrossOrigin(origins = {"http://be-java-production.up.railway.app", "https://be-java-production.up.railway.app"})
    public ResponseEntity<Object> setPassword(@RequestParam String email,  @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{\"password\":\"Masukan Password Terserah\"}")
            )
    ) @RequestBody Map<String, String> requestBody) {
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
    @CrossOrigin(origins = {"http://be-java-production.up.railway.app", "https://be-java-production.up.railway.app"})
    public ResponseEntity<Object> requestResetPassword(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{\"email\":\"Masukan email sama seperti register\"}")
            )
    ) @RequestBody UsersDTO usersDTO) {
        String email = usersDTO.getEmail();
        try {
            resetPasswordService.updateResetPassword(email);
            return SuccessResponse.generateResponse("succes", "Link reset password telah dikirim ke email Anda. Silakan periksa inbox Anda.", email, HttpStatus.OK);
        } catch (UsersNotFoundException e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reset-password")
    @CrossOrigin(origins = {"http://be-java-production.up.railway.app", "https://be-java-production.up.railway.app"})
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{\"newPassword\":\"Masukan Password Baru Terserah\"}")
            )
    ) @RequestBody Map<String, String> requestBody) {
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
    @CrossOrigin(origins = {"http://be-java-production.up.railway.app", "https://be-java-production.up.railway.app"})
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

package synrgy.finalproject.skyexplorer.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy.finalproject.skyexplorer.model.dto.UsersDTO;
import synrgy.finalproject.skyexplorer.model.dto.response.SuccessResponse;
import synrgy.finalproject.skyexplorer.model.entity.Users;
import synrgy.finalproject.skyexplorer.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerBasic(@Valid @RequestBody UsersDTO usersDTO) {
        try {
            Users savedUser = usersService.saveUser(usersDTO);
            if (savedUser != null) {
                usersService.sendOTPEmail(savedUser.getEmail());
                return SuccessResponse.generateResponse("200", "Success user data registered", savedUser, HttpStatus.OK);
            } else {
                return SuccessResponse.generateResponse("400", "Failed to register user. Please provide valid data.", null, HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException ex) {
            return SuccessResponse.generateResponse("500", ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<Object> verifyOTP(@RequestParam String email, @RequestParam String enteredOTP) {
        try {
            Users user = usersService.findUserByEmail(email);
            if (user != null) {
                boolean isValidOTP = usersService.verifyOTP(user, enteredOTP);
                if (isValidOTP) {
                    return SuccessResponse.generateResponse("200", "OTP verification successful. isOTPVerified updated to true.", user, HttpStatus.OK);
                } else {
                    return SuccessResponse.generateResponse("400", "Invalid OTP. Verification failed.", null, HttpStatus.BAD_REQUEST);
                }
            }
            return SuccessResponse.generateResponse("400", "User not found with the provided email.", null, HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException ex) {
            return SuccessResponse.generateResponse("500", ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<Object> resendOTP(@RequestParam String email) {
        try {
            String newOTP = usersService.resendOTPEmail(email);
            if (newOTP != null) {
                return SuccessResponse.generateResponse("200", "New OTP sent successfully", newOTP, HttpStatus.OK);
            } else {
                return SuccessResponse.generateResponse("404", "User not found or OTP could not be sent", null, HttpStatus.NOT_FOUND);
            }
        }  catch (IllegalArgumentException ex) {
            return SuccessResponse.generateResponse("500", ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/setPassword")
    public ResponseEntity<Object> setPassword(@RequestParam String email, @RequestParam String password) {
        try {
            Users user = usersService.setPassword(email, password);
            if (user != null && user.isRegistrationComplete()) {
                return SuccessResponse.generateResponse("200", "Password set successfully. Registration completed.", user, HttpStatus.OK);
            } else {
                return SuccessResponse.generateResponse("400", "Failed to set password or user not found.", null, HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException ex) {
            return SuccessResponse.generateResponse("500", ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

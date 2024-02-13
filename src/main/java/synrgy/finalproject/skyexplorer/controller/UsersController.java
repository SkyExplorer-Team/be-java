package synrgy.finalproject.skyexplorer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import synrgy.finalproject.skyexplorer.exception.UsersNotFoundException;
import synrgy.finalproject.skyexplorer.model.dto.TravelDocumentDTO;
import synrgy.finalproject.skyexplorer.model.dto.UsersDTO;
import synrgy.finalproject.skyexplorer.model.dto.request.LoginRequest;
import synrgy.finalproject.skyexplorer.model.dto.response.JwtResponse;
import synrgy.finalproject.skyexplorer.model.dto.response.SuccessResponse;
import synrgy.finalproject.skyexplorer.model.entity.TravelDocument;
import synrgy.finalproject.skyexplorer.model.entity.Users;
import synrgy.finalproject.skyexplorer.model.provider.AuthProvider;
import synrgy.finalproject.skyexplorer.repository.TravelDocumentRepository;
import synrgy.finalproject.skyexplorer.security.jwt.JwtUtils;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsImpl;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsServiceImpl;
import synrgy.finalproject.skyexplorer.service.ResetPasswordService;
import synrgy.finalproject.skyexplorer.service.TravelDocumentService;
import synrgy.finalproject.skyexplorer.service.UsersService;
import synrgy.finalproject.skyexplorer.service.Validator;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private TravelDocumentService travelDocumentService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) request.getAttribute("userPrincipal");
//        String email = userDetails.getUsername();
        Users user = usersService.findUserByEmail(request.getUserPrincipal().getName());
        return SuccessResponse.generateResponse("success", "Success Retrived user data", user, HttpStatus.OK);


    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Object> deleteUsersByEmail(@PathVariable String email) {
        try {
            Users user = usersService.findUserByEmail(email);
            if (user != null) {
                usersService.deleteUsers(user);
                return SuccessResponse.generateResponse("success", "User with email " + email + " has been deleted successfully.", email, HttpStatus.OK);
            } else {
                return SuccessResponse.generateResponse("error", "User with email " + email + " not found.", email, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/personal/{id}")
    public ResponseEntity<Object> updateUsers(@PathVariable UUID id, @RequestBody UsersDTO userUpdate) {
        try {
            Users updateUsers = usersService.UpdateUser(id, userUpdate);

            if (updateUsers != null) {
                return SuccessResponse.generateResponse("success", "User has been update successfully.", updateUsers, HttpStatus.OK);
            } else {
                return SuccessResponse.generateResponse("error", "User not found.", updateUsers, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/document/{id}")
    public ResponseEntity<Object> updateDocument(@PathVariable UUID id, @RequestBody TravelDocumentDTO userDocument) {
        try {
            TravelDocument updateDocument = travelDocumentService.updateDocument(id, userDocument);

            if (updateDocument != null) {
                return SuccessResponse.generateResponse("success", "Document has been update successfully.", updateDocument, HttpStatus.OK);
            } else {
                return SuccessResponse.generateResponse("error", "Document not found.", updateDocument, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

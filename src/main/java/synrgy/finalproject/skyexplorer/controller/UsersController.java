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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    @CrossOrigin(origins = {"http://be-java-production.up.railway.app", "https://be-java-production.up.railway.app"})
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) request.getAttribute("userPrincipal");
//        String email = userDetails.getUsername();
        Users user = usersService.findUserByEmail(request.getUserPrincipal().getName());
        return SuccessResponse.generateResponse("success", "Success Retrived user data", user, HttpStatus.OK);

    }
}

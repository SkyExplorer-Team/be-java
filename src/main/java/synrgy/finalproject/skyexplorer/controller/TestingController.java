package synrgy.finalproject.skyexplorer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import synrgy.finalproject.skyexplorer.model.entity.Users;
import synrgy.finalproject.skyexplorer.security.CurrentUser;
import synrgy.finalproject.skyexplorer.security.UserPrincipal;

@RestController
@Slf4j
public class TestingController {
    @Autowired
    private UsersController userRepository;

    @GetMapping("/user/me")
//    @PreAuthorize("hasRole('USER')")
    public Object getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userPrincipal;
//        return userRepository.findById(userPrincipal.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback(@AuthenticationPrincipal OAuth2User oauthUser) {
        log.info("OAuth2User {}", oauthUser);

        if (oauthUser != null && oauthUser instanceof DefaultOAuth2User) {
            // Extract user details
            String email = oauthUser.getAttribute("email");
            String name = oauthUser.getAttribute("name");
            String picture = oauthUser.getAttribute("picture");

            // Use the extracted information as needed

            return ResponseEntity.ok("Successfully received user information.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
    }
}

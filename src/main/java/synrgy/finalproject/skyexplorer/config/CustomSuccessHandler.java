package synrgy.finalproject.skyexplorer.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import synrgy.finalproject.skyexplorer.model.dto.UsersDTO;
import synrgy.finalproject.skyexplorer.model.entity.Role;
import synrgy.finalproject.skyexplorer.model.entity.Users;
import synrgy.finalproject.skyexplorer.model.provider.AuthProvider;
import synrgy.finalproject.skyexplorer.repository.UsersRepository;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsServiceImpl;

import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        if("google".equals(authenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attibutes = principal.getAttributes();
            String email = attibutes.get("email").toString();
            String name = attibutes.get("name").toString();
            String familyName = attibutes.get("family_name").toString();
            String imageUrl = attibutes.get("picture").toString();

            if (usersRepository.findByEmail(email) == null) {
                Users user = new Users();
                user.setEmail(email);
                user.setPassword("password");
                user.setSalutation("testing");
                user.setFistName(name);
                user.setLastName(familyName);
                user.setPhone("080000000000");
                user.setDob(LocalDate.now());
                user.setSubscribe(true);
                user.setImageUrl(imageUrl);
                user.setNational("Indonesia");
//                user.setRole(new Role().setId((UUID)"e933988a-71ae-4477-a7ff-75612b91e4f0")); // default  ROLE_USER
                user.setProvider(AuthProvider.google); // look if
                usersRepository.save(user);
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                DefaultOAuth2User newuser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("ROLE_USER")), attibutes,"sub");
                Authentication auth = new OAuth2AuthenticationToken(newuser, List.of(new SimpleGrantedAuthority("ROLE_USER")), "google");
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else{
//                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//                Users user = new Users();
//                DefaultOAuth2User newuser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("ROLE_USER")),
//                        attibutes,"sub");
//                Authentication auth = new OAuth2AuthenticationToken(newuser, List.of(new SimpleGrantedAuthority("ROLE_USER")), "google");
//                SecurityContextHolder.getContext().setAuthentication(auth);

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authenticated = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticated);
            }
            this.setAlwaysUseDefaultTargetUrl(true);
            this.setDefaultTargetUrl("http://localhost:8080/api/callback");
            this.onAuthenticationSuccess(request, response, authentication);
        }
    }

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        String redirectUrl= null;
//        log.info("onAuthenticationSuccess");
//        log.info("onAuthenticationSuccess {}", authentication);
//        if(authentication.getPrincipal() instanceof DefaultOAuth2User userDetail) {
//            String username =userDetail.getAttribute("email") != null ? userDetail.getAttribute("email"): userDetail.getAttribute("login");
//            if (usersRepository.findByEmail(username) == null) {
//                Users user = new Users();
//                user.setEmail(username);
//                user.setPassword("password");
//                user.setSalutation("testing");
//                user.setFistName("default");
//                user.setPhone("0852802302023");
//                user.setDob(LocalDate.now());
//                user.setSubscribe(true);
//                user.setLastName("default");
//                user.setNational("Indonesia");
//                usersRepository.save(user);
//            }
//        }
//        redirectUrl = "/callback";
//        new DefaultRedirectStrategy().sendRedirect(request,response, redirectUrl);
//    }
}

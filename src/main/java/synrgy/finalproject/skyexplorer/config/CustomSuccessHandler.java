package synrgy.finalproject.skyexplorer.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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
import org.springframework.web.util.UriComponentsBuilder;
import synrgy.finalproject.skyexplorer.model.dto.UsersDTO;
import synrgy.finalproject.skyexplorer.model.entity.Role;
import synrgy.finalproject.skyexplorer.model.entity.Users;
import synrgy.finalproject.skyexplorer.model.provider.AuthProvider;
import synrgy.finalproject.skyexplorer.repository.UsersRepository;
import synrgy.finalproject.skyexplorer.security.jwt.JwtUtils;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsServiceImpl;

import java.io.IOException;
import java.util.*;
import java.time.LocalDate;
import jakarta.servlet.http.Cookie;
import synrgy.finalproject.skyexplorer.utils.CookieUtils;

@Component
@Slf4j
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

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
                user.setFirstName(name);
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
//   coment dulu
//                DefaultOAuth2User newuser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("ROLE_USER")), attibutes,"sub");
//                Authentication auth = new OAuth2AuthenticationToken(newuser, List.of(new SimpleGrantedAuthority("ROLE_USER")), "google");
//                SecurityContextHolder.getContext().setAuthentication(auth);
            } else{
//                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//                Users user = new Users();
//                DefaultOAuth2User newuser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("ROLE_USER")),
//                        attibutes,"sub");
//                Authentication auth = new OAuth2AuthenticationToken(newuser, List.of(new SimpleGrantedAuthority("ROLE_USER")), "google");
//                SecurityContextHolder.getContext().setAuthentication(auth);
//                    coment dulu
//                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//                UsernamePasswordAuthenticationToken authenticated = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticated);
            }
            String targetUrl = determineTargetUrl(request, response, email);
            if (response.isCommitted()) {
                logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
                return;
            }

            getRedirectStrategy().sendRedirect(request, response, targetUrl);
//            this.setAlwaysUseDefaultTargetUrl(true);
//            this.setDefaultTargetUrl("http://localhost:8080/api/callback");
//            this.onAuthenticationSuccess(request, response, authentication);
        }
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, String email) {
//    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        Optional<String> redirectUri = CookieUtils.getCookie(request, "redirect_uri")
//                .map(Cookie::getValue);

//        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//            try {
//                throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
//            } catch (BadRequestException e) {
//                throw new RuntimeException(e);
//            }
//        }

//        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

//        String token = jwtUtils.generateToken(authentication);
        String token = jwtUtils.generateToken(email);
        System.out.println("here==============");
        return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect")
                .queryParam("token", token)
                .build().toUriString();
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

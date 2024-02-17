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
import synrgy.finalproject.skyexplorer.config.user.OAuth2UserInfo;
import synrgy.finalproject.skyexplorer.config.user.OAuth2UserInfoFactory;
import synrgy.finalproject.skyexplorer.model.dto.UsersDTO;
import synrgy.finalproject.skyexplorer.model.entity.Role;
import synrgy.finalproject.skyexplorer.model.entity.Users;
import synrgy.finalproject.skyexplorer.model.provider.AuthProvider;
import synrgy.finalproject.skyexplorer.repository.RoleRepository;
import synrgy.finalproject.skyexplorer.repository.UsersRepository;
import synrgy.finalproject.skyexplorer.security.HttpCookieOAuth2AuthorizationRequestRepository;
import synrgy.finalproject.skyexplorer.security.jwt.JwtUtils;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.time.LocalDate;
import jakarta.servlet.http.Cookie;
import synrgy.finalproject.skyexplorer.utils.CookieUtils;

import static synrgy.finalproject.skyexplorer.security.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@Slf4j
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = authenticationToken.getAuthorizedClientRegistrationId();
        if("google".equals(registrationId)) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attibutes = principal.getAttributes();
            OAuth2UserInfo auth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attibutes);
            Role role = roleRepository.findByName("ROLE_USER");

            if (usersRepository.findByEmail(auth2UserInfo.getEmail()) == null) {
                Users user = new Users();
                user.setEmail(auth2UserInfo.getEmail());
                user.setPassword("");
                user.setSalutation("");
                user.setFirstName(auth2UserInfo.getName());
                user.setLastName(auth2UserInfo.familyName());
                user.setPhone("");
                user.setDob(LocalDate.now());
                user.setSubscribe(false);
                user.setImageUrl(auth2UserInfo.getImageUrl());
                user.setNational(auth2UserInfo.locale());
                user.setRole(role); // default  ROLE_USER
                user.setProvider(AuthProvider.google); // look if
                user.setProviderId(auth2UserInfo.getId());
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
            String targetUrl = determineTargetUrl(request, response, auth2UserInfo.getEmail());
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
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        if (redirectUri.isPresent()) {
        log.info("url=== {}", redirectUri.get());
        log.info("url=== {}", isAuthorizedRedirectUri(redirectUri.get()));
        }
//        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//            try {
//                throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
//            } catch (BadRequestException e) {
//                throw new RuntimeException(e);
//            }
//        }
        String targetUrl = redirectUri.orElse("http://localhost:3000/oauth2/redirect");
//        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
//        String token = jwtUtils.generateToken(authentication);
        String token = jwtUtils.generateToken(email);
        log.info("here============== {} {}",targetUrl, token);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }
    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    log.info("00000000-00000000");
                    log.info("-------0 {}",authorizedURI);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}

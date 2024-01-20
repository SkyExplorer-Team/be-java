package synrgy.finalproject.skyexplorer.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import synrgy.finalproject.skyexplorer.config.CustomFailHandler;
import synrgy.finalproject.skyexplorer.config.CustomOAuth2UserService;
import synrgy.finalproject.skyexplorer.config.CustomSuccessHandler;
import synrgy.finalproject.skyexplorer.security.jwt.AuthEntryPointJwt;
import synrgy.finalproject.skyexplorer.security.jwt.AuthTokenFilter;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CustomSuccessHandler successHandler;

    @Autowired
    private CustomFailHandler failHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService ;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
                .authorizeRequests (auth ->
                        auth
//                                .requestMatchers("/users/login").permitAll()
//                                .requestMatchers("/users/reset-password").permitAll()
//                                .requestMatchers("/users/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/docs-api/**").permitAll()
                                .requestMatchers("/docs.html").permitAll()
                                .requestMatchers("/auth/**").permitAll()
//                                .requestMatchers("/users/register").permitAll()
                        .requestMatchers(request -> "/auth/register".equals(request.getServletPath())).permitAll()
//                        .requestMatchers(request -> "/users/register".equals(request.getServletPath())).permitAll()
                        .anyRequest().authenticated()
                ).formLogin(Customizer.withDefaults())
                .oauth2Login( oauth2 -> oauth2
//                        .loginPage("/oauth2/authorization/google").permitAll()
                        .authorizationEndpoint(a -> a.baseUri("/oauth2/authorize"))
                        .redirectionEndpoint(redirect -> redirect.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(successHandler)
                        .failureHandler(failHandler)
//                        .defaultSuccessUrl("/callback")
                );


        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);

        authenticationProvider.setPostAuthenticationChecks(new NoopPostAuthenticationChecks());
        return authenticationProvider;
    }

    @Bean
    public AuthTokenFilter authJwtTokenFilter(){
        return new AuthTokenFilter();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private static class NoopPostAuthenticationChecks implements UserDetailsChecker {
        @Override
        public void check(UserDetails user) {
            // Nonaktifkan kebijakan penguncian akun
        }
    }
}

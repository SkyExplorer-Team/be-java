//package synrgy.finalproject.skyexplorer.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.Arrays;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    private final long MAX_AGE_SECS = 3600;
//
//    @Value("${app.cors.allowedOrigins}")
//    private String[] allowedOrigins;
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        System.out.println("heelooo===");
//        System.out.println(Arrays.stream(allowedOrigins).toArray()); // [Ljava.lang.String;@68423388
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000","http://localhost:8080")
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(MAX_AGE_SECS);
//    }
//}
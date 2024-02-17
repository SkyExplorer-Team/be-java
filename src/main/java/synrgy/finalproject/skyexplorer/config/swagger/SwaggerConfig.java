package synrgy.finalproject.skyexplorer.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {@Server(url = "/api/", description = "Default server url bre")})
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("Sky Explorer REST API")
                        .description("Dokumentasi Rest API Skyexplorer everybody")
                        .version("1.0").contact(new Contact().name("Ersalomo and team skyexplorer")
                                .email( "www.baeldung.com").url("ersalomo2002@gmail.com"))
                        .license(new License().name("License of API").url("API license URL")));
    }
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}

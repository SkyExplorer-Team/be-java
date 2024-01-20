package synrgy.finalproject.skyexplorer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import synrgy.finalproject.skyexplorer.config.AppProperties;

import java.security.Principal;
import java.util.Map;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ConfigurationPropertiesScan({"synrgy.finalproject.skyexplorer.config"})
@EnableJpaAuditing
public class SkyexplorerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SkyexplorerApplication.class, args);
		System.out.println("Aplikasi Skyexplorer telah berhasil diluncurkan di http://localhost:8080");
	}
}

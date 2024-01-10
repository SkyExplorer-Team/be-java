package synrgy.finalproject.skyexplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableJpaAuditing
public class SkyexplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkyexplorerApplication.class, args);
		System.out.println("Aplikasi Skyexplorer telah berhasil diluncurkan di http://localhost:8080");
	}

}

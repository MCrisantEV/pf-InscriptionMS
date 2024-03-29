package mc.pf.ms.inscription.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

	@Bean
	@Qualifier("lines")
	public WebClient wcFamily() {
		return WebClient.create("http://localhost:8022/lines");
	}

	@Bean
	@Qualifier("courses")
	public WebClient wcCourse() {
		return WebClient.create("http://localhost:8021/courses");
	}
}

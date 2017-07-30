package co.com.kalah.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@SpringBootApplication
public class KalahGame {

	public static void main(String[] args) {
		SpringApplication.run(KalahGame.class, args);
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
}

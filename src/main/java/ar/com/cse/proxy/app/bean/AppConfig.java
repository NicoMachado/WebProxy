package ar.com.cse.proxy.app.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public Response response() {
		return new Response();
		
	}
}

package lc.alg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlgApplication implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>{

	public static void main(String[] args) {
		SpringApplication.run(AlgApplication.class, args);
	}

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		factory.setPort(80);	
	}

}

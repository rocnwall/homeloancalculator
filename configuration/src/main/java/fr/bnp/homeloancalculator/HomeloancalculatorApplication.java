package fr.bnp.homeloancalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
public class HomeloancalculatorApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(HomeloancalculatorApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//registry.addMapping("/**");
		registry.addMapping("/**").allowedMethods("GET", "PUT", "POST", "DELETE");
	}
}

package aiss.dailymotion_Miner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

@SpringBootApplication
public class DailymotionMinerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailymotionMinerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {return builder.build();}
}

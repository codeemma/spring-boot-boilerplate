package com.springboilerplate.springboilerplate;

import com.twilio.http.TwilioRestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
@EnableSwagger2
public class SpringBoilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoilerplateApplication.class, args);
	}

	@Bean
	public JavaMailSender getMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		//Using gmail
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("smartdocmaker");
		mailSender.setPassword("cooldocument");

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");//Prints out everything on screen

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}

	@Bean
	public TwilioRestClient getTwilioClient(){
		return new TwilioRestClient.Builder("AC847a11a3acd615dedf33e8ade3c0ee85","54e07fe2a03e56d562a1d76becfc2a66").build();
	}

	@Bean
	public RestOperations getRestoperation(){
		return new RestTemplate();
	}

	@Bean
	public Docket documentation() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(appInfo())
				.securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiKey apiKey() {
		return new ApiKey("authkey", "Authorization", "header");
	}

	private ApiInfo appInfo() {
		return new ApiInfoBuilder().title("REST API")
				.description("The rest api for digitrak").termsOfServiceUrl("")
				.contact(new Contact("Team 5", "", "eakinolayinka@gmail.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
				.version("0.0.1")
				.build();
	}
}

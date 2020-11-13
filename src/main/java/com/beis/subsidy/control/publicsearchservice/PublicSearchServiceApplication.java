package com.beis.subsidy.control.publicsearchservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * This is main class for spring boot based Public Search Service. 
 */
@SpringBootApplication
public class PublicSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicSearchServiceApplication.class, args);
	}

	/**
	 * This method is for open API documentation - used for API contracts. 
	 * @param appDesciption - Taken from pom file
	 * @param appVersion - taken from pom file
	 * @return OpenAPI - OpenAPI object
	 */
	@Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption, @Value("${application-version}") String appVersion) {
     return new OpenAPI()
          .info(new Info()
          .title("BEIS Subsidy Control - Public Search APIs")
          .version("1.0")
          .description("BEIS Subsidy Control - Public Search APIs for transparency database")
          .termsOfService("http://swagger.io/terms/")
          .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
	
}

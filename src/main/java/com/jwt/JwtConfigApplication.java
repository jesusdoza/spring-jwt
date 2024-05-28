package com.jwt;

import com.jwt.configs.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class JwtConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtConfigApplication.class, args);
	}

}

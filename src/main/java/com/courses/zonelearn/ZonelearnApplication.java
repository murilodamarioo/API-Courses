package com.courses.zonelearn;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "ZoneLearn Application",
				description = "API responsible for controlling the registration of courses and enrolled students",
				version = "1"
		)
)
public class  ZonelearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZonelearnApplication.class, args);
	}

}

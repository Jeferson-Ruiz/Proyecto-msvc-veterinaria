package com.jr.msvc.medicalcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcMedicalcontrolApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcMedicalcontrolApplication.class, args);
	}

}

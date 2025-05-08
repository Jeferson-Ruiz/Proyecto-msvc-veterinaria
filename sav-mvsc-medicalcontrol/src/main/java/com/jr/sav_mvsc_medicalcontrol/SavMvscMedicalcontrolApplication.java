package com.jr.sav_mvsc_medicalcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SavMvscMedicalcontrolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavMvscMedicalcontrolApplication.class, args);
	}

}

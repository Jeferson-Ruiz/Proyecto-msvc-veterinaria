package com.msvc.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcInvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcInvoiceApplication.class, args);
	}

}

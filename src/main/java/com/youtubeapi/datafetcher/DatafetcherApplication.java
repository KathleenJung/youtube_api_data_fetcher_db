package com.youtubeapi.datafetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DatafetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatafetcherApplication.class, args);
	}

}

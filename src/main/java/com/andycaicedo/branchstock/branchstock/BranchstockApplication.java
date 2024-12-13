package com.andycaicedo.branchstock.branchstock;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableR2dbcRepositories
@OpenAPIDefinition(info = @Info(title = "Branchstock-Application", version = "1.0", description = "Branchstock API"))
public class BranchstockApplication {

	public static void main(String[] args) {
		SpringApplication.run(BranchstockApplication.class, args);
	}


    @Bean  
    public ModelMapper modelMapper(){  
       return new ModelMapper();  
    }  

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
    
}

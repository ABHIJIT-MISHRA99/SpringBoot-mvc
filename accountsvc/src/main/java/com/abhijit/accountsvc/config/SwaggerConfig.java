package com.abhijit.accountsvc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        Contact contact=new Contact();
        contact.setEmail("mishraabhijit067@gmail.com");
        contact.setName("abhi");

        Info info = new Info().title("Springboot REST Services Demo project")
                .description("Project is for training purposes")
                .version("1.0")
                .contact(contact);

        return new OpenAPI().info(info);
    }


}

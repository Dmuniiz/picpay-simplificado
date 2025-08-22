package com.picpaysimplificado.infra;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("PicPay Simplificado ")
                .description("API Rest da aplicação PicPay simplificado,  nela é possível depositar e realizar transferências de dinheiro entre usuários.")
                .contact(new Contact()
                        .name("Time Backend")
                        .email("backend@picpay"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://picpay.simplificado/api/licenca")));
    }

}

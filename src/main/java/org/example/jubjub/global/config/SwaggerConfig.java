package org.example.jubjub.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // 1. 보안 스킴(Security Scheme) 정의: 우리는 JWT(Bearer)를 쓴다고 스웨거에 알려줌
        String jwtSchemeName = "jwtAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")               // Bearer 토큰
                        .bearerFormat("JWT"));          // 형식은 JWT

        return new OpenAPI()
                .info(new Info().title("JubJub Delivery API")
                        .version("1.0.0")
                        .description("배달 앱 백엔드 API 명세서입니다."))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
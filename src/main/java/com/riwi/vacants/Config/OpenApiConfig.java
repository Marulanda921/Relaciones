package com.riwi.vacants.Config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
//Todas las clases con esto se les considera configuracion
@Configuration
@OpenAPIDefinition(info = @Info (title = "Api para administrar empresas y vacantes de empresas", version = "1.0", description = "Documentacion de la api de vacantes y compañia"))
public class OpenApiConfig {


    //http://localhost:8080/api/v1/swagger-ui/index.html#/
}

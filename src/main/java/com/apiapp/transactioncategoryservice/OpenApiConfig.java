package com.apiapp.transactioncategoryservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(
	     name = "myOauth2Security",
	     type = SecuritySchemeType.HTTP,
	     scheme= "bearer",
	     bearerFormat = "jwt"
	   
	)


@OpenAPIDefinition(
	     info = @Info(
	               title = "Categorisation API",
	               version = "v1",
		       description = "This app provides REST APIs for Category entities",
		       contact = @Contact(
					name = "Arundhati",
					email = "",
					url = ""
				)
			)
	            )


public class OpenApiConfig {

	
	
	
	
}

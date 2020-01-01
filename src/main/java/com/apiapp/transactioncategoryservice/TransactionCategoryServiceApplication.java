package com.apiapp.transactioncategoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = { "com.apiapp" })

@EnableCaching
public class TransactionCategoryServiceApplication extends SpringBootServletInitializer  {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {

	
		
		
	
			SpringApplication.run(TransactionCategoryServiceApplication.class, args);
		

	}

	
	
	
	
	
	/**private static OAuthFlows getOAuthFlows() {
OAuthFlows oAuthFlows=new OAuthFlows();

		OAuthFlow implicit=new OAuthFlow();
		
		implicit.setAuthorizationUrl("http://localhost:8082/auth");
		return oAuthFlows;
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("Categorisation API") String appVersion) {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes( "bearer-key",new SecurityScheme().type(SecurityScheme.Type.OAUTH2).scheme("bearer").bearerFormat("JWT").in(In.HEADER).flows(getOAuthFlows())));
			    

						/**.addSecuritySchemes("bearer-key",schemasItem= @SecurityScheme(
							     name = "myOauth2Security",
							     type = SecuritySchemeType.OAUTH2,
							     in = SecuritySchemeIn.HEADER,
							     bearerFormat = "jwt",
							     flows = @OAuthFlows(
							               implicit = @OAuthFlow(
							                  authorizationUrl = "http://url.com/auth",
							                  scopes = {
							                     @OAuthScope(name = "read", description = "read access"),
							                     @OAuthScope(name = "write", description = "Write access")
							                  }
							             )
							     )
							))
						.info(new Info().title("Categorisation API").version(appVersion)
									.description("Categorisation API categorises the transactions into business categories")
									.termsOfService("http://swagger.io/terms/").license(new License().name("Apache 2.0").url(""))); **/
								
								
								
								
								
								
						     
					              
						    		  
						    		  /**implicit = @OAuthFlow(
					                  authorizationUrl = "http://url.com/auth",
					                  scopes = {
					                     @OAuthScope(name = "read", description = "read access"),
					                     @OAuthScope(name = "write", description = "Write access")
					                  }
					             )
					     )))) **/

				
				
				//addSecuritySchemes("basicScheme",	new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
				
	//} **/
	
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	    return builder.sources(TransactionCategoryServiceApplication.class);
	  }
}

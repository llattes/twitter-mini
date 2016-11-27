package net.lucianolattes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ApiDocsConfig {

  @Bean
  public Docket petApi() {
    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any()).build().apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    Contact contact = new Contact("Luciano Lattes", "https://github.com/llattes", "luciano.lattes@gmail.com");
    return new ApiInfo("twitter-mini", "Mini messaging service, inspired by Twitter", "0.0.1-SNAPSHOT", null, contact,
        null, null);
  }
}

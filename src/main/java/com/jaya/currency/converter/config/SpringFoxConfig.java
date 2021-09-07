package com.jaya.currency.converter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * OAS.
 */
@Configuration
@SuppressWarnings("unused")
public class SpringFoxConfig {

  /**
   * Scan package with annotation.
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.OAS_30)
        .select()
        .apis(RequestHandlerSelectors
            .basePackage("com.jaya.currency.converter"))
        .paths(PathSelectors.regex("/.*"))
        .build()
        .useDefaultResponseMessages(false)
        .apiInfo(apiEndPointsInfoIntegration());
  }

  private ApiInfo apiEndPointsInfoIntegration() {
    return new ApiInfoBuilder().title("Currency Converter")
        .description("Project responsible to convert money.")
        .contact(
            new Contact("Mário Falcão", "@mariofalcao.es", "mariofalcao.es"
                + "@gmail.com"))
        .version("0.0.1-SNAPSHOT")
        .build();
  }

  /**
   * Build Ui configuration.
   */
  @Bean
  public UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
        .deepLinking(true)
        .displayOperationId(true)
        .defaultModelsExpandDepth(1)
        .defaultModelExpandDepth(1)
        .defaultModelRendering(ModelRendering.EXAMPLE)
        .displayRequestDuration(false)
        .docExpansion(DocExpansion.NONE)
        .filter(true)
        .operationsSorter(OperationsSorter.ALPHA)
        .showExtensions(false)
        .tagsSorter(TagsSorter.ALPHA)
        .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
        .validatorUrl(null)
        .build();
  }

}
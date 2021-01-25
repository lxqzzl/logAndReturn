package com.test.ge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * swagger文档配置
 *
 * @author lxq
 */

@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //这里是controller所处的包名
                .apis(RequestHandlerSelectors.basePackage("com.test.ge.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    /**
     *    构建api文档的详细信息函数
     * @return ApiInfoBuilder
     */

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("")
                //描述
                .description("")
                .termsOfServiceUrl("")
                //版本号
                .version("v1")
                .build();
    }
}

package com.liucj.as.api.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置文件
 * 通过 http://localhost:端口号/swagger-ui.html访问
 *  http://localhost:5088/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String VERSION ="1.0.0";
    private static final String AUTHOR ="liucj";
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.liucj.as.api.controller")) //controller的包名
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(ApiIgnore.class)
                .tags(new Tag("Account","账号模块"));
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //设置文档标题
                .title("API文档")
                .description("移动党架构")
                .version(VERSION)
                .contact(new Contact(AUTHOR,"http://www.baidu.com","627107345@qq.com"))
                .build();
    }
}

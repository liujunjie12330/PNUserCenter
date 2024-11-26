package com.pn.service.config;


import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author:liujunjie
 * @version:1.0 Time:23:00
 * CreatedBy:IntelliJ IDEA
 * ClassName:SwaggerConfig
 */
@Component
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerConfig {

    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    private Boolean enable;

    /**
     * 项目应用名
     */
    private String applicationName;

    /**
     * 项目版本信息
     */
    private String applicationVersion;

    /**
     * 项目描述信息
     */
    private String applicationDescription;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")

                // 定义是否开启swagger，false为关闭，可以通过变量控制，线上关闭
                .enable(enable)

                //配置api文档元信息
                .apiInfo(apiInfo())

                // 选择哪些接口作为swagger的doc发布
                .select()

                //apis() 控制哪些接口暴露给swagger，
                // RequestHandlerSelectors.any() 所有都暴露
                // RequestHandlerSelectors.basePackage("net.xdclass.*")  指定包位置
                // withMethodAnnotation(ApiOperation.class)标记有这个注解 ApiOperation
                .apis(RequestHandlerSelectors.basePackage("com.pn.web.controller"))

                .paths(PathSelectors.any())

                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName)
                .description(applicationDescription)
                .contact(new Contact("Programming Navigation", "pn@163.com", "pn@163.com"))
                .version(applicationVersion)
                .build();
    }

}

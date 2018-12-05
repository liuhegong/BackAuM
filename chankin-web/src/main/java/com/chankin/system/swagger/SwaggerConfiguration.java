package com.chankin.system.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2 //开启swagger2
public class SwaggerConfiguration {
    /*
     *  swagger2的配置对象,交给spring管理
     * */
    @Bean
    public Docket demoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .apiInfo(apiInfo());  // 对所有路径进行监控
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("chankin", "", "chenjian1261@gmail.com");
        ApiInfo apiInfo = new ApiInfo(
                "BackAuM接口文档",//大标题
                "请勿随意测试删除类接口",//小标题
                "V1.0.0",//版本
                "",
                contact,//作者
                "Apache",//链接显示文字
                "http://www.apache.org/licenses/"//网站链接
        );
        return apiInfo;
    }
}

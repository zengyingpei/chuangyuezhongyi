package com.zyp.config;

import com.zyp.interceptor.AdminLoginInterceptor;
import com.zyp.interceptor.DoctorLoginInterceptor;
import com.zyp.interceptor.UserLoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("创悦中医项目接口文档")
                .version("2.0")
                .description("创悦中医项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zyp.controller"))    //注意点：包名不能写错，负责扫描不到
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //进行 静态资源映射，告诉boot框架，访问/doc.html的时候，是访问一个静态资源，而不是访问controller
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Autowired
    private UserLoginInterceptor userLoginInterceptor;
    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;
    @Autowired
    private DoctorLoginInterceptor doctorLoginInterceptor;

    protected void addInterceptors(InterceptorRegistry registry) {
//        log.info("开始注册自定义拦截器...");
//        registry.addInterceptor(userLoginInterceptor)
//                .addPathPatterns("/api/user/**")
//                .excludePathPatterns("/api/user/user/login");
//
//        registry.addInterceptor(adminLoginInterceptor)
//                .addPathPatterns("/api/admin/**")
//                .excludePathPatterns("/api/admin/admin/login");
//
//        registry.addInterceptor(doctorLoginInterceptor)
//                .addPathPatterns("/api/doctor/**")
//                .excludePathPatterns("/api/doctor/doctor/login");
    }

}
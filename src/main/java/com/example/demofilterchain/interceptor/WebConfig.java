package com.example.demofilterchain.interceptor;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class WebConfig {
    // đã là 1 filter bean, nếu không có chỉ định yêu cầu gì về thứ tự hay gì thì mặc định bất cứ request nào cũng cần đi qua

    @Bean
    public FilterRegistrationBean<MyFilter> myFilterRegistration() {
        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter());
        registrationBean.addUrlPatterns("/*"); // Apply the filter to all URLs
        registrationBean.setOrder(1); // Set the execution order

        return registrationBean;
    }
}


package org.okten.may2024.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DemoBeansConfig {

    @Primary
    @Bean
    public String demoBean1() {
        return "Demo Bean 1";
    }

    @Bean
    public String demoBean2() {
        return "Demo Bean 2";
    }

    @Bean
    public Integer demoBeanInt() {
        return 42;
    }
}

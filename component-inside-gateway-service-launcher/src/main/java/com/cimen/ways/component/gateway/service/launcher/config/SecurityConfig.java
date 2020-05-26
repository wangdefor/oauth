package com.cimen.ways.component.gateway.service.launcher.config;

import com.cimen.ways.component.gateway.service.launcher.properties.FilterIgnorePropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @ClassName SecurityConfig
 * @Description 安全配置类
 * @Date 2020/5/11 17:02
 * @Author wangyong
 * @Version 1.0
 **/
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {


    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        String[] ignores = new String[filterIgnorePropertiesConfig.getUrls().size()];
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(filterIgnorePropertiesConfig.getUrls().toArray(ignores)).permitAll()
                .anyExchange().authenticated();
        http.oauth2ResourceServer().jwt();
        return http.build();
    }

}

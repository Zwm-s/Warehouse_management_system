package com.wms.config;

import com.wms.filters.JwtLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
* Security配置
* */
@Configuration
@EnableWebSecurity//开启Sercurity
public class SecurityConfig {

    /*
    * 获取AuthenticationConfiguration
    * 简单的说，这个类的作用就是用来创建ProviderManager，
    * ProviderManager是一个AuthenticationManager实现，用于管理所有AuthenticationProvider实现的一个管理器
    * */
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    JwtLoginFilter jwtLoginFilter;
    //配置过滤器链
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //设置拦截资源，login放行，配置的资源路径放行规则通常是全局生效的
        http.authorizeHttpRequests((auth) -> {
                    auth.requestMatchers(HttpMethod.POST,"/login").permitAll()
                            .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE,"/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/**").permitAll()
                            .anyRequest()
                            .authenticated();
                }
        );

        //禁用默认登录表单
        http.formLogin(AbstractHttpConfigurer::disable);
        //禁用登出界面
        http.logout(AbstractHttpConfigurer::disable);
        //禁用session
        http.sessionManagement(AbstractHttpConfigurer::disable);
        //禁用httpBasic，即是HTTP基本认证
        http.httpBasic(AbstractHttpConfigurer::disable);
        //禁用csrf保护
        http.csrf(AbstractHttpConfigurer::disable);
        //禁用跨域拦截
        http.cors(AbstractHttpConfigurer::disable);
        //添加自定义过滤器覆盖UsernamePasswordAuthenticationFilter
        // at: ⽤来某个 filter 替换过滤器链中哪个 filter
        // before: 放在过滤器链中哪个 filter 之前
        // after: 放在过滤器链中那个 filter 之后
        http.addFilterAt(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /*
    * 密码编码器，Security不允许密码明文认证
    * */
    @Bean
    public PasswordEncoder getPwdEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    * 将AuthenticationManager放到全局
    * */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

}
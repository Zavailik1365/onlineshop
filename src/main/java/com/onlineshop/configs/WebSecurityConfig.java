package com.onlineshop.configs;

import com.onlineshop.dao.entitys.Role;
import com.onlineshop.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailService userSevice;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserDetailService userSevice) {
        this.userSevice = userSevice;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/",
                        "/registration",
                        "/static/**")
                    .permitAll()
                .antMatchers("/admin/**")
                    .access("hasRole('ROLE_ADMIN')") // TODO не рабоает ограничение по ролям
                .anyRequest()
                    .authenticated()
            .and()
                .exceptionHandling()
                    .accessDeniedPage("/403")
            .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
                .csrf()
                .disable(); // Придумать что делать с csrf
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSevice)
                .passwordEncoder(passwordEncoder);
    }
}
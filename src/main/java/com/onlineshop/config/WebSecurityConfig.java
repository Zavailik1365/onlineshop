package com.onlineshop.config;

import com.onlineshop.service.UserDetailService;
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

    private final UserDetailService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint authEntryPoint;

    @Autowired
    public WebSecurityConfig(
            UserDetailService userService,
            PasswordEncoder passwordEncoder,
            AuthenticationEntryPoint authEntryPoint) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authEntryPoint = authEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/",
                        "/registration",
                        "/js/**")
                    .permitAll()
                .antMatchers("/admin/**")
                    .access("hasRole('ROLE_ADMIN')")
                .anyRequest()
                    .authenticated()
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
                .disable()
                .httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}
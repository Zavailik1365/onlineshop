package com.onlineshop.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "online-shop/registration")
                    .permitAll()
                .antMatchers("/online-shop/admin/**")
                    .access("hasAnyAuthority('ADMIN')")
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
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        String usersByUsernameQuery;
        String authoritiesByUsernameQuery;

        usersByUsernameQuery =
                "SELECT" +
                    " username," +
                    " password," +
                    " active" +
                " FROM usr" +
                " WHERE" +
                    " username=?";

        authoritiesByUsernameQuery =
                "SELECT" +
                    " usr.username," +
                    " user_role.roles" +
                " FROM usr usr inner join user_role user_role on" +
                    " usr.id = user_role.user_id where usr.username=?";

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery(usersByUsernameQuery)
                .authoritiesByUsernameQuery(authoritiesByUsernameQuery);
    }
}
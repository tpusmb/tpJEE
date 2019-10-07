package com.big_fat_package.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Resources
        http.authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/videos/**").permitAll()
                .antMatchers("/images/**").permitAll();

        // Pages roles
        http.authorizeRequests()
                .antMatchers("/**").permitAll();

        // Errors
        http.exceptionHandling().accessDeniedPage("/403");
    }
}

package com.big_fat_package.configuration;

import com.big_fat_package.utilities.RoleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

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

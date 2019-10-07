package com.pneedle.configuration;

import com.pneedle.utilities.RoleData;
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

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

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
                .antMatchers("/").permitAll()
                .antMatchers("/login").not().authenticated()
                .antMatchers("/registration").not().authenticated()
                .antMatchers("/getUsers/**").hasAuthority(RoleData.ADMIN)
                .antMatchers("/dashboard/robots/**").hasAuthority(RoleData.ADMIN)
                .antMatchers("/dashboard/licence/**").hasAnyAuthority(RoleData.ADMIN, RoleData.COMPANY, RoleData.EMPLOYEE)
                .antMatchers("/dashboard/rent-request/**").hasAnyAuthority(RoleData.ADMIN, RoleData.COMPANY)
                .antMatchers("/dashboard/company/**").hasAuthority(RoleData.ADMIN)
                .antMatchers("/dashboard/employee/**").hasAuthority(RoleData.COMPANY)
                .antMatchers("/dashboard/**").hasAnyAuthority(RoleData.ADMIN, RoleData.COMPANY, RoleData.EMPLOYEE).anyRequest().authenticated();

        // Login
        http.authorizeRequests().and()
                .formLogin().loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/dashboard")
                .usernameParameter("email")
                .passwordParameter("password");

        // Logout
        http.authorizeRequests().and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");

        // Errors
        http.exceptionHandling().accessDeniedPage("/403");
    }
}

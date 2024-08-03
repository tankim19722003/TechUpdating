package com.techupdating.techupdating.configurations;

import com.techupdating.techupdating.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
//@EnableWebMvc
public class SecurityConfigurations {

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfigurations(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(requests ->
            requests
                    .requestMatchers(HttpMethod.GET,"/api/v1/dev_updating/login").permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/v1/dev_updating/processLoginForm").permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/v1/dev_updating/processRegisterForm").permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/v1/dev_updating/register").permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/v1/dev_updating/admin/login").permitAll()

                    .requestMatchers(HttpMethod.POST,"/api/v1/dev_updating/admin/processAdminLoginForm").permitAll()


//                    post
                    .requestMatchers(HttpMethod.POST,"/api/v1/dev_updating/admin/show_post_creating").hasRole("USER")

                    .requestMatchers(HttpMethod.GET,"/bootstrap/**").permitAll()
                    .requestMatchers(HttpMethod.GET,"/JS/**").permitAll()
                    .requestMatchers(HttpMethod.GET,"/CSS/**").permitAll()
                    .requestMatchers(HttpMethod.GET,"/images/**").permitAll()
                    .anyRequest().authenticated()

        )
        .formLogin(form ->
                form
                        .loginPage("/api/v1/dev_updating/admin/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successForwardUrl("/api/v1/dev_updating/admin/post/show_post_creating")
                        .permitAll()
        )
        .logout(logout -> logout.permitAll());
        return http.build();
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}

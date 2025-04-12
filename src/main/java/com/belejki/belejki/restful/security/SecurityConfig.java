package com.belejki.belejki.restful.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/reminders").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/reminders/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/reminders").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/reminders").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/reminders/**").hasRole("USER")

                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/authorities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/authorities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/authorities/**").hasRole("ADMIN")

                        //define paths for recipe
                        .requestMatchers(HttpMethod.GET, "/recipes/**").hasRole("USER")

                        //define paths for ingridients
                        .requestMatchers(HttpMethod.GET, "/ingredients/**").hasRole("USER")

                        //define paths for friendships

                        //define paths for recipeingridients

                        //define paths for shoppingitems

                        //define paths for wishes
        );

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(csrf -> csrf.disable());
        return httpSecurity.build();
    }
}

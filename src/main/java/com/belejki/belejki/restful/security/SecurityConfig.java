package com.belejki.belejki.restful.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
                        //ADMIN access:
                        .requestMatchers(HttpMethod.GET, "/").hasRole("ADMIN")

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

                        //USER access:
                        .requestMatchers(HttpMethod.GET, "/reminders").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/reminders/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/reminders").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/reminders").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/reminders/**").hasRole("USER")

                        //define paths for recipe
                        .requestMatchers(HttpMethod.GET, "/recipes/**").hasRole("USER")

                        //define paths for ingredients
                        .requestMatchers(HttpMethod.GET, "/ingredients/**").hasRole("USER")

                        //define paths for friendships
                        .requestMatchers(HttpMethod.GET, "/friendships/**").hasRole("USER")


                        //define paths for recipeIngredients
                        .requestMatchers(HttpMethod.GET, "/recipeIngredients/**").hasRole("USER")


                        //define paths for shoppingItems
                        .requestMatchers(HttpMethod.GET, "/shoppingItems/**").hasRole("USER")

                        //define paths for wishes
                        .requestMatchers(HttpMethod.GET, "/wishes/**").hasRole("USER")


                );

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}

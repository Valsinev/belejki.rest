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
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/users/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/authorities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/authorities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/authorities/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/authorities/**").hasRole("ADMIN")

                        //USER access:
                        .requestMatchers(HttpMethod.GET, "/reminders").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/reminders/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/reminders").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/reminders/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/reminders/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/reminders/**").hasRole("USER")

                        //define paths for recipe
                        .requestMatchers(HttpMethod.GET, "/recipes").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/recipes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/recipes").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/recipes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/recipes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/recipes/**").hasRole("USER")

                        //define paths for ingredients
                        .requestMatchers(HttpMethod.GET, "/ingredients").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/ingredients/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/ingredients").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/ingredients/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/ingredients/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/ingredients/**").hasRole("USER")

                        //define paths for friendships
                        .requestMatchers(HttpMethod.GET, "/friendships").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/friendships/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/friendships").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/friendships/**").hasRole("USER")


                        //define paths for recipeIngredients
                        .requestMatchers(HttpMethod.GET, "/recipeIngredients").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/recipeIngredients/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/recipeIngredients").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/recipeIngredients/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/recipeIngredients/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/recipeIngredients/**").hasRole("USER")


                        //define paths for shoppingItems
                        .requestMatchers(HttpMethod.GET, "/shoppingItems").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/shoppingItems/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/shoppingItems").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/users/*/shoppingItems").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/shoppingItems/**").hasRole("USER")

                        //define paths for wishes
                        .requestMatchers(HttpMethod.GET, "/wishes").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/wishes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/wishes").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/wishes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/wishes/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/wishes/**").hasRole("USER")


                );

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}

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
                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/authorities/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/authorities/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/authorities/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/authorities/{id}").hasRole("ADMIN")

                        //USER access:

                        .requestMatchers(HttpMethod.GET, "/reminders").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/reminders/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/reminders").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/reminders/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/reminders/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/reminders/{id}").hasRole("USER")
//                        .requestMatchers(HttpMethod.GET, "/users/*/reminders").hasRole("USER")
//                        .requestMatchers(HttpMethod.POST, "/users/*/reminders").hasRole("USER")
//                        .requestMatchers(HttpMethod.PUT, "/users/*/reminders").hasRole("USER")
//                        .requestMatchers(HttpMethod.DELETE, "/users/*/reminders").hasRole("USER")
//                        .requestMatchers(HttpMethod.PATCH, "/users/*/reminders").hasRole("USER")


                        //define paths for recipe
                        .requestMatchers(HttpMethod.GET, "/recipes").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/recipes/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/recipes").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/recipes/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/recipes/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/recipes/{id}").hasRole("USER")

                        //define paths for ingredients
                        .requestMatchers(HttpMethod.GET, "/ingredients").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/ingredients/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/ingredients").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/ingredients/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/ingredients/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/ingredients/{id}").hasRole("USER")

                        //define paths for friendships
                        .requestMatchers(HttpMethod.GET, "/friendships").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/friendships/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/friendships").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/friendships/{id}").hasRole("USER")


                        //define paths for recipeIngredients
                        .requestMatchers(HttpMethod.GET, "/recipeIngredients").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/recipeIngredients/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/recipeIngredients").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/recipeIngredients/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/recipeIngredients/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/recipeIngredients/{id}").hasRole("USER")


                        //define paths for shoppingItems
                        .requestMatchers(HttpMethod.GET, "/shoppingItems").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/shoppingItems/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/shoppingItems").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/shoppingItems/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/users/{username}/shoppingItems").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/users/{id}/shoppingItems").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}/shoppingItems").hasRole("USER")

                        //define paths for wishes
                        .requestMatchers(HttpMethod.GET, "/wishes").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/wishes/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/wishes").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/wishes/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/wishes/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/wishes/{id}").hasRole("USER")


                );

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}

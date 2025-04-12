package com.belejki.belejki.restful.datainit;

import com.belejki.belejki.restful.entity.*;
import com.belejki.belejki.restful.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    @PostConstruct
    @Transactional
    public void populateData() {
        // Create user
//        User user = new User("venio@abv.bg", "Venislav", "Stoyanov", "{noop}1q2w3e");
//        user.setEnabled(true);
//        user.setLastLogin(LocalDate.now());
//        user.setAuthorities(List.of(new Authority(null, user, "ROLE_ADMIN")));
//        userRepository.save(user); // make sure user is managed

    }
}

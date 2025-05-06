package com.belejki.belejki.restful.repository;
import com.belejki.belejki.restful.entity.Ingredient;
import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import com.belejki.belejki.restful.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RecipeIngredientRepositoryTest {


    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testCreation() {

    }

    @Test
    public void testCreateUser() {
    }
}

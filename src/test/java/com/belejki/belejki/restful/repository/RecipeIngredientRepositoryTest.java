package com.belejki.belejki.restful.repository;
import com.belejki.belejki.restful.ingredient.repository.IngredientRepository;
import com.belejki.belejki.restful.recipe.repository.RecipeRepository;
import com.belejki.belejki.restful.recipeIngredient.repository.RecipeIngredientRepository;
import com.belejki.belejki.restful.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

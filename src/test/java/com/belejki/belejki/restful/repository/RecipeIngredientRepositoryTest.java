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

        User user = new User("test@mail.bg", "TestPesho", "TestPeshev", "1q2w3e");
        user.setEnabled(true);
        user.setLastLogin(LocalDate.now());
        userRepository.save(user);

        Ingredient potato = new Ingredient(null, "potatos");
        Ingredient savedPotato = ingredientRepository.save(potato);
        Recipe musaka = new Recipe(null, "musaka", "make musaka", user, new HashSet<>());
        Recipe savedMusaka = recipeRepository.save(musaka);

        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(savedMusaka)
                .ingredient(savedPotato)
                .quantity("1kg")
                .build();

        RecipeIngredient savedRI = recipeIngredientRepository.save(recipeIngredient);
        Optional<RecipeIngredient> found = recipeIngredientRepository.findById(savedRI.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getRecipe().getName()).isEqualTo("musaka");
        assertThat(found.get().getIngredient().getName()).isEqualTo("potatos");
        assertThat(found.get().getQuantity()).isEqualTo("1kg");
    }

    @Test
    public void testCreateUser() {
    }
}

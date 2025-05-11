package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.dto.IngredientDto;
import com.belejki.belejki.restful.entity.Ingredient;
import com.belejki.belejki.restful.exception.IngredientNotFoundException;
import com.belejki.belejki.restful.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Page<Ingredient> findAll(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }

    public Ingredient findByName(String name) {

        return ingredientRepository.findByNameIgnoreCase(name).orElseThrow(() -> new IngredientNotFoundException("No ingredient found for name: " + name));
    }

    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new IngredientNotFoundException("No ingredient found for id: " + id));
    }

    public Ingredient save(IngredientDto ingredient) {

        Optional<Ingredient> byName = ingredientRepository.findByName(ingredient.getName());
        Ingredient newIngredient = new Ingredient();
        if (byName.isEmpty()) {
            newIngredient.setName(ingredient.getName());
            return ingredientRepository.save(newIngredient);
        } else return byName.get();
    }

    public Ingredient findOrCreateByName(String ingredientName) {

        return ingredientRepository.findByName(ingredientName)
                .orElseGet(()-> {
                    Ingredient ingredient = new Ingredient(ingredientName);
                    return ingredientRepository.save(ingredient);
                        }
                );
    }

    public Ingredient delete(Ingredient ingredient) {
        checkIfIngredientBelongsToRecipe(ingredient);
        ingredientRepository.delete(ingredient);
        return ingredient;
    }

    public Ingredient deleteById(Long id) {
        Ingredient byId = findById(id);
        return delete(byId);
    }


    public Ingredient deleteByName(String name) {
        Ingredient byName = findByName(name);
        return delete(byName);
    }


    private static void checkIfIngredientBelongsToRecipe(Ingredient byId) {
        if (!byId.getRecipeIngredients().isEmpty()) {
            List<Long> usedInRecipeIds = byId.getRecipeIngredients().stream().map(recipeIngredient -> recipeIngredient.getRecipe().getId()).toList();
            throw new RuntimeException("Ingredient is used in recipes:" + usedInRecipeIds);
        }
    }

}

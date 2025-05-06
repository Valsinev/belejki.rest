package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.entity.Ingredient;
import com.belejki.belejki.restful.exception.IngredientNotFoundException;
import com.belejki.belejki.restful.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Page<Ingredient> findAll(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }

    public Ingredient findByName(String name) {
        Ingredient byName = ingredientRepository.findByName(name);
        if (byName == null) {
            throw new IngredientNotFoundException(name);
        }
        return byName;
    }

    public Ingredient save(Ingredient ingredient) {

        Ingredient existing = ingredientRepository.findByName(ingredient.getName());

        if (existing == null) {
            existing = ingredientRepository.save(ingredient);
        }
        return existing;
    }
}

package com.belejki.belejki.restful.recipeIngredient.service;

import com.belejki.belejki.restful.ingredient.domain.Ingredient;
import com.belejki.belejki.restful.ingredient.repository.IngredientRepository;
import com.belejki.belejki.restful.recipe.domain.Recipe;
import com.belejki.belejki.restful.recipeIngredient.domain.RecipeIngredient;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientDto;
import com.belejki.belejki.restful.shared.exception.RecipeIngredientNotFoundException;
import com.belejki.belejki.restful.recipeIngredient.repository.RecipeIngredientRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository , ModelMapper modelMapper) {
        this.recipeIngredientRepository = recipeIngredientRepository;
	    this.modelMapper = modelMapper;
    }


    public RecipeIngredientDto findById(Long id) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new RecipeIngredientNotFoundException("Recipe ingredient not found for id: " + id));
        return modelMapper.map(recipeIngredient, RecipeIngredientDto.class);
    }

    @Override
    public Page<RecipeIngredientDto> findAll(Pageable pageable) {
        Page<RecipeIngredient> all = recipeIngredientRepository.findAll(pageable);
        return all.map((element) -> modelMapper.map(element, RecipeIngredientDto.class));
    }

    @Override
    public Page<RecipeIngredientDto> findAllByRecipe(Recipe recipe, Pageable pageable) {
        Page<RecipeIngredient> allByRecipe = recipeIngredientRepository.findAllByRecipe(recipe, pageable);
        return allByRecipe.map((element) -> modelMapper.map(element, RecipeIngredientDto.class));
    }

    @Override
    public Page<RecipeIngredientDto> findAllByRecipe_IdAndRecipe_User_Username(Long recipeId, String username, Pageable pageable) {
        Page<RecipeIngredient> allByRecipeIdAndRecipeUserUsername = recipeIngredientRepository.findAllByRecipe_IdAndRecipe_User_Username(recipeId, username, pageable);
        return allByRecipeIdAndRecipeUserUsername.map((element) -> modelMapper.map(element, RecipeIngredientDto.class));
    }

    @Transactional
    @Override
    public void delete(RecipeIngredientDto recipeIngredient) {
        recipeIngredientRepository.deleteById(recipeIngredient.getId());
    }

    @Transactional
    public void deleteById(Long id) {
        recipeIngredientRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByRecipe(Recipe recipe) {
        recipeIngredientRepository.deleteAllByRecipe_Id(recipe.getId());
    }

    @Transactional
    @Override
    public void deleteAllByRecipe_Id(Long recipeId) {
        recipeIngredientRepository.deleteAllByRecipe_Id(recipeId);
    }


}

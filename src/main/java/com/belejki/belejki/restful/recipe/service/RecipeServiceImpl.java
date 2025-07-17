package com.belejki.belejki.restful.recipe.service;

import com.belejki.belejki.restful.recipe.domain.Recipe;
import com.belejki.belejki.restful.recipe.web.dto.RecipeRequestDto;
import com.belejki.belejki.restful.recipe.web.dto.RecipeResponseDto;
import com.belejki.belejki.restful.recipeIngredient.domain.RecipeIngredient;
import com.belejki.belejki.restful.shared.exception.user.UserNotFoundException;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.user.repository.UserRepository;
import com.belejki.belejki.restful.shared.exception.RecipeNotFoundException;
import com.belejki.belejki.restful.recipe.repository.RecipeRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository, ModelMapper modelMapper) {
		this.recipeRepository = recipeRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}


    //Post


    @Override
    public RecipeResponseDto save(String username, RecipeRequestDto recipeDto) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("[Recipe.Service.save]: User not found for username: " + username));
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);

        user.addRecipe(recipe);
        recipe.setUser(user);
        Recipe saved = recipeRepository.save(recipe);
        return modelMapper.map(saved, RecipeResponseDto.class);
    }

    @Override
    public RecipeResponseDto findByIdAndUser_Username(Long id, String username) {
        Recipe byIdAndUserUsername = recipeRepository.findByIdAndUser_Username(id, username)
                .orElseThrow(() -> new RecipeNotFoundException("[Recipe]: Recipe not found for id: " + id));
        return modelMapper.map(byIdAndUserUsername, RecipeResponseDto.class);

    }


    //Get

    public Page<RecipeResponseDto> findAllByUser_Id(Long id, Pageable pageable) {
        Page<Recipe> allByUserId = recipeRepository.findAllByUser_Id(id, pageable);
        return allByUserId.map((element) -> modelMapper.map(element, RecipeResponseDto.class));
    }


    @Override
    public void delete(RecipeRequestDto recipe) {
        recipeRepository.deleteById(recipe.getId());
    }

    @Override
    public void deleteByIdAndUser_Username(Long id, String username) {
        recipeRepository.deleteByIdAndUser_Username(id, username);
    }

    @Override
    public void deleteAllByUser_Id(Long id) {
        recipeRepository.deleteAllByUser_Id(id);
    }

    @Override
    public void deleteAllByUser_Username(String username) {
        recipeRepository.deleteAllByUser_Username(username);
    }

    public Page<RecipeResponseDto> findAllByUser_Username(String username, Pageable pageable) {
        Page<Recipe> allByUserUsername = recipeRepository.findAllByUser_Username(username, pageable);
        return allByUserUsername.map((element) -> modelMapper.map(element, RecipeResponseDto.class));
    }

    @Override
    public Page<RecipeResponseDto> findAllByNameContainingAndUser_Username(String recipeName, String username, Pageable pageable) {
        Page<Recipe> allByNameContainingAndUserUsername = recipeRepository.findAllByNameContainingAndUser_Username(recipeName, username, pageable);
        return allByNameContainingAndUserUsername.map((element) -> modelMapper.map(element, RecipeResponseDto.class));
    }

    public Page<RecipeResponseDto> findRecipesByAllIngredientNamesAndUser_Username(List<String> ingredients, String username, Pageable pageable) {
        Page<Recipe> recipesByAllIngredientNamesAndUsername = recipeRepository.findRecipesByAllIngredientNamesAndUsername(ingredients, ingredients.size(), username, pageable);
        return recipesByAllIngredientNamesAndUsername.map((element) -> modelMapper.map(element, RecipeResponseDto.class));
    }

    @Override
    public Page<RecipeResponseDto> findAll(Pageable pageable) {
        Page<Recipe> all = recipeRepository.findAll(pageable);
        return all.map((element) -> modelMapper.map(element, RecipeResponseDto.class));
    }

    @Override
    public Page<RecipeResponseDto> findAllByNameContainingIgnoreCase(String recipeName, Pageable pageable) {
        Page<Recipe> allByNameContainingIgnoreCase = recipeRepository.findAllByNameContainingIgnoreCase(recipeName, pageable);
        return allByNameContainingIgnoreCase.map((element) -> modelMapper.map(element, RecipeResponseDto.class));
    }

    public RecipeResponseDto findById(@NonNull Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException("Recipe not found for id: " + recipeId));
        return modelMapper.map(recipe, RecipeResponseDto.class);
    }

    //Delete

    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

}

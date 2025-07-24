package com.belejki.belejki.restful.recipe.service;

import com.belejki.belejki.restful.friendship.domain.Friendship;
import com.belejki.belejki.restful.friendship.repository.FriendshipRepository;
import com.belejki.belejki.restful.ingredient.domain.Ingredient;
import com.belejki.belejki.restful.ingredient.repository.IngredientRepository;
import com.belejki.belejki.restful.recipe.domain.Recipe;
import com.belejki.belejki.restful.recipe.web.dto.FriendRecipesByIngredientsAndUsernameDto;
import com.belejki.belejki.restful.recipe.web.dto.FriendRecipesByUsernameDto;
import com.belejki.belejki.restful.recipe.web.dto.RecipeDto;
import com.belejki.belejki.restful.shared.exception.FriendshipNotFoundException;
import com.belejki.belejki.restful.shared.exception.user.UserNotFoundException;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.user.repository.UserRepository;
import com.belejki.belejki.restful.shared.exception.RecipeNotFoundException;
import com.belejki.belejki.restful.recipe.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final IngredientRepository ingredientRepository;
    private final FriendshipRepository friendshipRepository;

    @Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository, ModelMapper modelMapper, IngredientRepository ingredientRepository, FriendshipRepository friendshipRepository) {
		this.recipeRepository = recipeRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	    this.ingredientRepository = ingredientRepository;
	    this.friendshipRepository = friendshipRepository;
    }


    //Post


    @Override
    public RecipeDto save(String username, RecipeDto recipeDto) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("[Recipe.Service.save]: User not found for username: " + username));

        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        recipe.getRecipeIngredients().forEach(recipeIngredient -> {
            //search for already existing ingredient
            String ingredientName = recipeIngredient.getIngredient().getName();
            Optional<Ingredient> existingIngredient = ingredientRepository.findByName(ingredientName);
            //if ingredient is not found make new one
            Ingredient ingredient = existingIngredient.orElseGet(() -> {
                Ingredient newIngredient = new Ingredient(ingredientName);
                return ingredientRepository.save(newIngredient); // <-- persist before assigning!
            });

            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setRecipe(recipe);
        });

        user.addRecipe(recipe);
        recipe.setUser(user);
        Recipe saved = recipeRepository.save(recipe);
        return modelMapper.map(saved, RecipeDto.class);
    }

    @Override
    public RecipeDto findByIdAndUser_Username(Long id, String username) {
        Recipe byIdAndUserUsername = recipeRepository.findByIdAndUser_Username(id, username)
                .orElseThrow(() -> new RecipeNotFoundException("[Recipe]: Recipe not found for id: " + id));
        return modelMapper.map(byIdAndUserUsername, RecipeDto.class);

    }


    //Get


    public RecipeDto findById(@NonNull Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException("Recipe not found for id: " + recipeId));
        return modelMapper.map(recipe, RecipeDto.class);
    }

    public Page<RecipeDto> findAllByUser_Id(Long id, Pageable pageable) {
        Page<Recipe> allByUserId = recipeRepository.findAllByUser_Id(id, pageable);
        return allByUserId.map((element) -> modelMapper.map(element, RecipeDto.class));
    }



    public Page<RecipeDto> findAllByUser_Username(String username, Pageable pageable) {
        Page<Recipe> allByUserUsername = recipeRepository.findAllByUser_Username(username, pageable);
        return allByUserUsername.map((element) -> modelMapper.map(element, RecipeDto.class));
    }

    @Override
    public Page<RecipeDto> findAllByNameContainingAndUser_Username(String recipeName, String username, Pageable pageable) {
        Page<Recipe> allByNameContainingAndUserUsername = recipeRepository.findAllByNameContainingAndUser_Username(recipeName, username, pageable);
        return allByNameContainingAndUserUsername.map((element) -> modelMapper.map(element, RecipeDto.class));
    }

    public Page<RecipeDto> findRecipesByAllIngredientNamesAndUser_Username(List<String> ingredients, String username, Pageable pageable) {
        Page<Recipe> recipesByAllIngredientNamesAndUsername = recipeRepository.findRecipesByAllIngredientNamesAndUsername(ingredients, ingredients.size(), username, pageable);
        return recipesByAllIngredientNamesAndUsername.map((element) -> modelMapper.map(element, RecipeDto.class));
    }

    @Override
    public Page<RecipeDto> findAll(Pageable pageable) {
        Page<Recipe> all = recipeRepository.findAll(pageable);
        return all.map((element) -> modelMapper.map(element, RecipeDto.class));
    }

    @Override
    public Page<RecipeDto> findAllByNameContainingIgnoreCase(String recipeName, Pageable pageable) {
        Page<Recipe> allByNameContainingIgnoreCase = recipeRepository.findAllByNameContainingIgnoreCase(recipeName, pageable);
        return allByNameContainingIgnoreCase.map((element) -> modelMapper.map(element, RecipeDto.class));
    }


    @Override
    public Page<RecipeDto> findAllFriendRecipesByName(FriendRecipesByUsernameDto friendRecipesByUsernameDto, String username, Pageable pageable) {
        //search if the user has friend with that username
        Optional<Friendship> foundFriend = friendshipRepository.findByUser_UsernameAndFriend_Username(username, friendRecipesByUsernameDto.getFriendUsername());
        if (foundFriend.isEmpty()) {
            throw new FriendshipNotFoundException("[RecipeService.findAllFriendRecipesByName] The user is not friend with the searched user.");
        }
        Page<Recipe> allByNameContainingIgnoreCase = recipeRepository.findAllByNameContainingIgnoreCase(friendRecipesByUsernameDto.getRecipeName(), pageable);
        return allByNameContainingIgnoreCase.map((element) -> modelMapper.map(element, RecipeDto.class));
    }

    @Override
    public Page<RecipeDto> findAllFriendRecipesByIngredients(FriendRecipesByIngredientsAndUsernameDto friendRecipesRequestDto, String username, Pageable pageable) {
        //search if the user has friend with that username
        Optional<Friendship> foundFriend = friendshipRepository.findByUser_UsernameAndFriend_Username(username, friendRecipesRequestDto.getFriendUsername());
        if (foundFriend.isEmpty()) {
            throw new FriendshipNotFoundException("[RecipeService.findAllFriendRecipesByName] The user is not friend with the searched user.");
        }
        List<String> ingredients = friendRecipesRequestDto.getIngredients();
        Page<Recipe> recipesByAllIngredientNamesAndUsername = recipeRepository.findRecipesByAllIngredientNamesAndUsername(ingredients, ingredients.size(), friendRecipesRequestDto.getFriendUsername(), pageable);
        return recipesByAllIngredientNamesAndUsername.map((element) -> modelMapper.map(element, RecipeDto.class));
    }

    //Delete

    @Transactional
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }


    @Transactional
    @Override
    public void delete(RecipeDto recipe) {
        recipeRepository.deleteById(recipe.getId());
    }

    @Transactional
    @Override
    public void deleteByIdAndUser_Username(Long id, String username) {
        recipeRepository.deleteByIdAndUser_Username(id, username);
    }

    @Transactional
    @Override
    public void deleteAllByUser_Id(Long id) {
        recipeRepository.deleteAllByUser_Id(id);
    }

    @Transactional
    @Override
    public void deleteAllByUser_Username(String username) {
        recipeRepository.deleteAllByUser_Username(username);
    }


}

package com.belejki.belejki.restful.exception;

public class RecipeIngredientNotFoundException extends RuntimeException {
    public RecipeIngredientNotFoundException(String message) {
        super(message);
    }
}

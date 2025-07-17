package com.belejki.belejki.restful.shared.exception;

public class RecipeIngredientNotFoundException extends RuntimeException {
    public RecipeIngredientNotFoundException(String message) {
        super(message);
    }
}

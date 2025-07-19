package com.belejki.belejki.restful.recipeIngredient.domain;

import com.belejki.belejki.restful.ingredient.domain.Ingredient;
import com.belejki.belejki.restful.recipe.domain.Recipe;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@Table(name = "recipe_ingredients")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "quantity")
    private String quantity;


    public RecipeIngredient(Ingredient ingredient, String quantity, Recipe recipe) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "ingredient=" + ingredient +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return Objects.equals(id, that.id) && Objects.equals(ingredient, that.ingredient) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredient, quantity);
    }
}

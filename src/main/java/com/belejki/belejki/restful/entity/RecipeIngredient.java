package com.belejki.belejki.restful.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "quantity")
    private String quantity;


    public RecipeIngredient(Ingredient ingredient, String quantity, Recipe recipe) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.recipe = recipe;
    }
}

package com.belejki.belejki.restful.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String howToMake;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<RecipeIngredient> recipeIngredients;

    public Recipe() {
        this.recipeIngredients = new HashSet<>();
    }

    public void addIngredient(Ingredient ingredient, String quantity) {
        RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient, quantity, this);
        recipeIngredients.add(recipeIngredient);
    }

}

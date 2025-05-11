package com.belejki.belejki.restful.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients;

    public Recipe() {
        this.recipeIngredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient, String quantity) {
        RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient, quantity, this);
        recipeIngredients.add(recipeIngredient);
    }

}

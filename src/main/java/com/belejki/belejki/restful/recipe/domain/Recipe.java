package com.belejki.belejki.restful.recipe.domain;

import com.belejki.belejki.restful.recipeIngredient.domain.RecipeIngredient;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.ingredient.domain.Ingredient;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Column(columnDefinition = "TEXT", nullable = false)
    private String instructions;
    @Column(name = "video_link", length = 2000)
    private String videoLink;
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

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", videoLink='" + videoLink + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) && Objects.equals(name, recipe.name) && Objects.equals(instructions, recipe.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, instructions);
    }
}

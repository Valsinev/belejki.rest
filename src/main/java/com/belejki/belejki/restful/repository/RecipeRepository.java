package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findByName(String name);

    List<Recipe> findByUser_Username(String username);
    List<Recipe> findByUser(User user);

    @Query("""
            SELECT r FROM Recipe r
            JOIN r.recipeIngredients ri
            JOIN ri.ingredient i
            WHERE i.name IN :ingredientNames
            AND r.user = :user
            GROUP BY r
            HAVING COUNT(DISTINCT i.name) = :ingredientCount
            """)
    Page<Recipe> findRecipesByAllIngredientNamesAndUser(@Param("ingredientNames") List<String> ingredientNames,
                                                        @Param("ingredientCount") long ingredientCount,
                                                        @Param("user")User user,
                                                        Pageable pageable);



        @Query("""
                SELECT r FROM Recipe r
                JOIN r.recipeIngredients ri
                JOIN ri.ingredient i
                WHERE i.name IN :ingredientNames
                AND r.user.username = :username
                GROUP BY r
                HAVING COUNT(DISTINCT i.name) = :ingredientCount""")
        Page<Recipe> findRecipesByAllIngredientNamesAndUsername(@Param("ingredientNames") List<String> ingredientNames,
                                                                @Param("ingredientCount") long ingredientCount,
                                                                @Param("username") String username,
                                                                Pageable pageable);


    Page<Recipe> findAllByNameContainingAndUser_Username(String name, String username, Pageable pageable);
    Page<Recipe> findAllByNameContainingAndUser(String name, User user, Pageable pageable);
}

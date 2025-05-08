package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.IngredientDto;
import com.belejki.belejki.restful.entity.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public IngredientDto toDto(Ingredient ingredient) {
        IngredientDto dto = new IngredientDto();
        dto.setName(ingredient.getName());
        dto.setId(ingredient.getId());
        return dto;
    }
}

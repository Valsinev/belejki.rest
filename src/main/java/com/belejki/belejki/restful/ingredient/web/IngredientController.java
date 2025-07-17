package com.belejki.belejki.restful.ingredient.web;

import com.belejki.belejki.restful.ingredient.service.IngredientService;
import com.belejki.belejki.restful.ingredient.web.dto.IngredientRequestDto;
import com.belejki.belejki.restful.ingredient.web.dto.IngredientResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	//region POST METHODS

    @PostMapping("/user/ingredients")
    public ResponseEntity<IngredientResponseDto> save(@Valid @RequestBody IngredientRequestDto ingredient) {
        IngredientResponseDto ingredientRequestDto = ingredientService.save(ingredient);

        return ResponseEntity.ok(ingredientRequestDto);
    }

    //endregion

    //region GET METHODS

    @GetMapping("/admin/ingredients")
    public ResponseEntity<Page<IngredientResponseDto>> findAll(Pageable pageable) {
        Page<IngredientResponseDto> all = ingredientService.findAll(pageable);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/admin/ingredient")
    public ResponseEntity<IngredientResponseDto> findByName(@Valid @RequestBody IngredientRequestDto dto,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        IngredientResponseDto byName = ingredientService.findByName(dto.getName());
        return ResponseEntity.ok(byName);
    }

    @GetMapping("/admin/ingredients/{id}")
    public ResponseEntity<IngredientResponseDto> findById(@PathVariable Long id) {
        IngredientResponseDto byId = ingredientService.findById(id);
        return ResponseEntity.ok(byId);
    }

    //endregion

    //region DELETE METHODS

    @DeleteMapping("/admin/ingredients/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ingredientService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/admin/ingredients/{name}")
    public ResponseEntity<Void> deleteByName(@PathVariable String name) {
        ingredientService.deleteByName(name);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/admin/ingredients/clear")
    public ResponseEntity<Page<Void>> deleteAllWithEmptyRecipeIngredients() {
        ingredientService.deleteAllWithoutRecipeIngredients();
        return ResponseEntity.ok().build();
    }

    //endregion
}

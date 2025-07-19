package com.belejki.belejki.restful.ingredient.web;

import com.belejki.belejki.restful.ingredient.service.IngredientService;
import com.belejki.belejki.restful.ingredient.web.dto.IngredientDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	//region POST METHODS

    @PostMapping("/user/ingredients")
    public ResponseEntity<IngredientDto> save(@Valid @RequestBody IngredientDto ingredient, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        IngredientDto ingredientRequestDto = ingredientService.save(ingredient);

        return ResponseEntity.ok(ingredientRequestDto);
    }

    //endregion

    //region GET METHODS

    @GetMapping("/admin/ingredients")
    public ResponseEntity<Page<IngredientDto>> findAll(Pageable pageable) {
        Page<IngredientDto> all = ingredientService.findAll(pageable);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/admin/ingredient")
    public ResponseEntity<IngredientDto> findByName(@Valid @RequestBody IngredientDto dto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<IngredientDto> byName = ingredientService.findByName(dto.getName());

	    return byName.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/admin/ingredients/{id}")
    public ResponseEntity<IngredientDto> findById(@PathVariable Long id) {
        IngredientDto byId = ingredientService.findById(id);
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

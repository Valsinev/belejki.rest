package com.belejki.belejki.restful.shoppingItem.service;

import com.belejki.belejki.restful.shoppingItem.web.dto.ShoppingItemDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ShoppingItemService {
	ShoppingItemDto save(@Valid ShoppingItemDto dto, String username);

	Page<ShoppingItemDto> findAllByUser_Id(Long userId, Pageable pageable);

	Page<ShoppingItemDto> findAllByUser_Username(String username, Pageable pageable);

	ShoppingItemDto findByIdAndUser_Username(Long id, String username);

	void deleteByIdAndUser_Username(Long id, String username);

	void deleteAllByUsername(String username);

	void deleteById(Long id);

	BigDecimal findSumOfAllItemsPriceOfUserByUsername(String username);
}

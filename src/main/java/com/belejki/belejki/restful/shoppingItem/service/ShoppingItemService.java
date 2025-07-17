package com.belejki.belejki.restful.shoppingItem.service;

import com.belejki.belejki.restful.shoppingItem.web.dto.ShoppingItemRequestDto;
import com.belejki.belejki.restful.shoppingItem.web.dto.ShoppingItemResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShoppingItemService {
	ShoppingItemResponseDto save(@Valid ShoppingItemRequestDto dto, String username);

	Page<ShoppingItemResponseDto> findAllByUser_Id(Long userId, Pageable pageable);

	Page<ShoppingItemResponseDto> findAllByUser_Username(String username, Pageable pageable);

	ShoppingItemResponseDto findByIdAndUser_Username(Long id, String username);

	void deleteByIdAndUser_Username(Long id, String username);

	void deleteAllByUsername(String username);

	void deleteById(Long id);

}

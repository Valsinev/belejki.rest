package com.belejki.belejki.restful.shoppingItem.service;

import com.belejki.belejki.restful.shared.exception.ShoppingItemNotFoundException;
import com.belejki.belejki.restful.shared.exception.user.UserNotFoundException;
import com.belejki.belejki.restful.shoppingItem.domain.ShoppingItem;
import com.belejki.belejki.restful.shoppingItem.repository.ShoppingItemRepository;
import com.belejki.belejki.restful.shoppingItem.web.dto.ShoppingItemDto;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShoppingItemServiceImpl implements ShoppingItemService{

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final ShoppingItemRepository shoppingItemRepository;

	@Autowired
	public ShoppingItemServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ShoppingItemRepository shoppingItemRepository) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.shoppingItemRepository = shoppingItemRepository;
	}

	@Override
	public ShoppingItemDto save(ShoppingItemDto dto, String username) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		ShoppingItem item = modelMapper.map(dto, ShoppingItem.class);
		item.setUser(user);
		ShoppingItem saved = shoppingItemRepository.save(item);
		ShoppingItemDto savedResponse = modelMapper.map(saved, ShoppingItemDto.class);
		return savedResponse;
	}

	@Override
	public Page<ShoppingItemDto> findAllByUser_Id(Long userId, Pageable pageable) {

		Page<ShoppingItem> allByUserId = shoppingItemRepository.findAllByUser_Id(userId, pageable);

		return allByUserId.map(shoppingItem -> modelMapper.map(shoppingItem, ShoppingItemDto.class));
	}

	@Override
	public Page<ShoppingItemDto> findAllByUser_Username(String username, Pageable pageable) {
		Page<ShoppingItem> allByUserUsername = shoppingItemRepository.findAllByUser_Username(username, pageable);
		return allByUserUsername.map(shoppingItem -> modelMapper.map(shoppingItem, ShoppingItemDto.class));
	}

	@Override
	public ShoppingItemDto findByIdAndUser_Username(Long id, String username) {
		ShoppingItem shoppingItem = shoppingItemRepository.findByIdAndUser_Username(id, username)
				.orElseThrow(() -> new ShoppingItemNotFoundException("No shopping item found."));
		return modelMapper.map(shoppingItem, ShoppingItemDto.class);
	}

	@Override
	public BigDecimal findSumOfAllItemsPriceOfUserByUsername(String username) {
		BigDecimal sumOfAllItemsPrice = shoppingItemRepository.getSumOfAllItemsPrice(username);
		if (sumOfAllItemsPrice == null) return BigDecimal.ZERO;
		return sumOfAllItemsPrice;
	}

	@Transactional
	@Override
	public void deleteByIdAndUser_Username(Long id, String username) {

		shoppingItemRepository.deleteByIdAndUser_Username(id, username);
	}

	@Transactional
	@Override
	public void deleteAllByUsername(String username) {
		shoppingItemRepository.deleteAllByUser_Username(username);
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		shoppingItemRepository.deleteById(id);
	}

}

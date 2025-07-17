package com.belejki.belejki.restful.wish.service;

import com.belejki.belejki.restful.shared.exception.user.UserNotFoundException;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.user.repository.UserRepository;
import com.belejki.belejki.restful.wish.web.dto.WishRequestDto;
import com.belejki.belejki.restful.wish.domain.Wish;
import com.belejki.belejki.restful.shared.exception.WishNotFoundException;
import com.belejki.belejki.restful.wish.repository.WishRepository;
import com.belejki.belejki.restful.wish.web.dto.WishResponseDto;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class WishServiceImpl implements WishService {
	private final WishRepository wishRepository;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public WishServiceImpl(WishRepository wishRepository, UserRepository userRepository, ModelMapper modelMapper) {
		this.wishRepository = wishRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}


	@Override
	public WishResponseDto save(WishRequestDto dto, String username) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found for username: " + username));

		Wish wish = modelMapper.map(dto, Wish.class);

		this.coupleWishToUser(wish, user);

		Wish saved = wishRepository.save(wish);
		return modelMapper.map(saved, WishResponseDto.class);
	}

	@Override
	public Page<WishResponseDto> findAllByUser_UsernameOrderByApproximatePriceDesc(String username, Pageable pageable) {

		Page<Wish> allByUserUsernameOrderByApproximatePriceDesc = wishRepository.findAllByUser_UsernameOrderByApproximatePriceDesc(username, pageable);

		return allByUserUsernameOrderByApproximatePriceDesc.map(wish -> modelMapper.map(wish, WishResponseDto.class));
	}

	@Override
	public WishResponseDto findByIdAndUsername(Long id, String username) {
		Wish wish = wishRepository.findByIdAndUser_Username(id, username)
				.orElseThrow(() -> new WishNotFoundException("Wish not found"));

		return modelMapper.map(wish, WishResponseDto.class);
	}

	@Override
	public Page<WishResponseDto> findAllByUser_IdOrderByApproximatePriceDesc(Long id, Pageable pageable) {
		Page<Wish> allByUserIdOrderByApproximatePriceDesc = wishRepository.findAllByUser_IdOrderByApproximatePriceDesc(id, pageable);

		return allByUserIdOrderByApproximatePriceDesc.map(wish -> modelMapper.map(wish, WishResponseDto.class));
	}

	@Override
	public Page<WishResponseDto> findAllByDescriptionContainingAndUser_UsernameOrderByApproximatePriceDesc(String description, String username, Pageable pageable) {
		Page<Wish> allByDescriptionContainingAndUserUsernameOrderByApproximatePriceDesc = wishRepository.findAllByDescriptionContainingAndUser_UsernameOrderByApproximatePriceDesc(description, username, pageable);
		return allByDescriptionContainingAndUserUsernameOrderByApproximatePriceDesc.map(wish -> modelMapper.map(wish, WishResponseDto.class));
	}

	@Override
	public Page<WishResponseDto> findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(Long price, String username, Pageable pageable) {
		Page<Wish> allByApproximatePriceLessThanEqualAndUserUsernameOrderByApproximatePriceDesc = wishRepository.findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(price, username, pageable);
		return allByApproximatePriceLessThanEqualAndUserUsernameOrderByApproximatePriceDesc.map(wish -> modelMapper.map(wish, WishResponseDto.class));
	}

	@Override
	public WishResponseDto update(WishRequestDto dto, String username) {

		Wish wish = wishRepository.findByIdAndUser_Username(dto.getId(), username)
				.orElseThrow(() -> new WishNotFoundException("Wish not found."));
		wish.setDescription(dto.getDescription());
		wish.setApproximatePrice(dto.getApproximatePrice());
		wish.setLink(dto.getLink());
		Wish saved = wishRepository.save(wish);
		return modelMapper.map(saved, WishResponseDto.class);
	}

	@Override
	public WishResponseDto patchWish(WishRequestDto dto, String username) {
		Wish wish = wishRepository.findByIdAndUser_Username(dto.getId(), username)
				.orElseThrow(() -> new WishNotFoundException("Wish not Found."));

		if (dto.getDescription() != null) {
			wish.setDescription(dto.getDescription());
		}
		if (dto.getApproximatePrice() != null) {
			wish.setApproximatePrice(dto.getApproximatePrice());
		}
		if (dto.getLink() != null) {
			wish.setLink(dto.getLink());
		}

		Wish saved = wishRepository.save(wish);

		return modelMapper.map(saved, WishResponseDto.class);
	}

	@Override
	public void delete(Long id, String username) {

		wishRepository.deleteByIdAndUser_Username(id, username);
	}

	@Override
	public void deleteAllByUserId(Long id) {

		wishRepository.deleteAllByUser_Id(id);
	}

	@Override
	public void deleteAllByUser_Username(String username) {

		wishRepository.deleteAllByUser_Username(username);
	}

	private void coupleWishToUser(Wish wish, User user) {
		wish.setUser(user);
		user.addWish(wish);
	}

	public WishResponseDto update(@Valid WishRequestDto dto) {
		Wish wish = wishRepository.findById(dto.getId())
				.orElseThrow(() -> new WishNotFoundException("[Wish.Service.update] Wish not found by id: " + dto.getId()));
		wish.setDescription(dto.getDescription());
		wish.setApproximatePrice(dto.getApproximatePrice());
		wish.setLink(dto.getLink());
		Wish saved = wishRepository.save(wish);
		return modelMapper.map(saved, WishResponseDto.class);
	}

	public WishResponseDto patchWish(@Valid WishRequestDto dto) {
		Long id = dto.getId();
		Wish wish = wishRepository.findById(id)
				.orElseThrow(() -> new WishNotFoundException("Wish not found for id: " + id));

		// Update only fields that are provided
		if (dto.getDescription() != null) {
			wish.setDescription(dto.getDescription());
		}

		if (dto.getApproximatePrice() != null) {
			wish.setApproximatePrice(dto.getApproximatePrice());
		}

		if (dto.getLink() != null) {
			wish.setLink(dto.getLink());
		}

		Wish saved = wishRepository.save(wish);
		return modelMapper.map(saved, WishResponseDto.class);
	}

}

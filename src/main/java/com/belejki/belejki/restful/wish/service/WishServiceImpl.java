package com.belejki.belejki.restful.wish.service;

import com.belejki.belejki.restful.friendship.domain.Friendship;
import com.belejki.belejki.restful.friendship.repository.FriendshipRepository;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipDto;
import com.belejki.belejki.restful.shared.exception.FriendshipNotFoundException;
import com.belejki.belejki.restful.shared.exception.user.UserNotFoundException;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.user.repository.UserRepository;
import com.belejki.belejki.restful.wish.web.dto.WishDto;
import com.belejki.belejki.restful.wish.domain.Wish;
import com.belejki.belejki.restful.shared.exception.WishNotFoundException;
import com.belejki.belejki.restful.wish.repository.WishRepository;
import com.belejki.belejki.restful.wish.web.dto.WishPatchDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class WishServiceImpl implements WishService {
	private final WishRepository wishRepository;
	private final UserRepository userRepository;
	private final FriendshipRepository friendshipRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public WishServiceImpl(WishRepository wishRepository, UserRepository userRepository, FriendshipRepository friendshipRepository, ModelMapper modelMapper) {
		this.wishRepository = wishRepository;
		this.userRepository = userRepository;
		this.friendshipRepository = friendshipRepository;
		this.modelMapper = modelMapper;
	}


	@Override
	public WishDto save(WishDto dto, String username) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found for username: " + username));

		Wish wish = modelMapper.map(dto, Wish.class);

		this.coupleWishToUser(wish, user);

		Wish saved = wishRepository.save(wish);
		return modelMapper.map(saved, WishDto.class);
	}

	@Override
	public Page<WishDto> findAllByUser_UsernameOrderByApproximatePriceDesc(String username, Pageable pageable) {

		Page<Wish> allByUserUsernameOrderByApproximatePriceDesc = wishRepository.findAllByUser_UsernameOrderByApproximatePriceDesc(username, pageable);

		return allByUserUsernameOrderByApproximatePriceDesc.map(wish -> modelMapper.map(wish, WishDto.class));
	}

	@Override
	public WishDto findByIdAndUsername(Long id, String username) {
		Wish wish = wishRepository.findByIdAndUser_Username(id, username)
				.orElseThrow(() -> new WishNotFoundException("Wish not found"));

		return modelMapper.map(wish, WishDto.class);
	}

	@Override
	public Page<WishDto> findAllByUser_IdOrderByApproximatePriceDesc(Long id, Pageable pageable) {
		Page<Wish> allByUserIdOrderByApproximatePriceDesc = wishRepository.findAllByUser_IdOrderByApproximatePriceDesc(id, pageable);

		return allByUserIdOrderByApproximatePriceDesc.map(wish -> modelMapper.map(wish, WishDto.class));
	}

	@Override
	public Page<WishDto> findAllByDescriptionContainingAndUser_UsernameOrderByApproximatePriceDesc(String description, String username, Pageable pageable) {
		Page<Wish> allByDescriptionContainingAndUserUsernameOrderByApproximatePriceDesc = wishRepository.findAllByDescriptionContainingAndUser_UsernameOrderByApproximatePriceDesc(description, username, pageable);
		return allByDescriptionContainingAndUserUsernameOrderByApproximatePriceDesc.map(wish -> modelMapper.map(wish, WishDto.class));
	}

	@Override
	public Page<WishDto> findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(Long price, String username, Pageable pageable) {
		Page<Wish> allByApproximatePriceLessThanEqualAndUserUsernameOrderByApproximatePriceDesc = wishRepository.findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(price, username, pageable);
		return allByApproximatePriceLessThanEqualAndUserUsernameOrderByApproximatePriceDesc.map(wish -> modelMapper.map(wish, WishDto.class));
	}

	@Override
	public WishDto update(WishDto dto, String username) {

		Wish wish = wishRepository.findByIdAndUser_Username(dto.getId(), username)
				.orElseThrow(() -> new WishNotFoundException("[Wish.Service.update] Wish not found by id: " + dto.getId()));
		wish.setDescription(dto.getDescription());
		wish.setApproximatePrice(dto.getApproximatePrice());
		wish.setLink(dto.getLink());
		Wish saved = wishRepository.save(wish);
		return modelMapper.map(saved, WishDto.class);
	}

	@Override
	public WishDto patchWish(WishPatchDto dto, String username) {
		Wish wish = wishRepository.findByIdAndUser_Username(dto.getId(), username)
				.orElseThrow(() -> new WishNotFoundException("[WishService.patchWish] Wish not Found."));

		String linkPattern = "https?:\\/\\/[\\w\\-\\.~:\\/?#\\[\\]@!$&'()*+,;=%]+";

		if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
			wish.setDescription(dto.getDescription());
		}
		if (dto.getApproximatePrice() != null && dto.getApproximatePrice() > 0) {
			wish.setApproximatePrice(dto.getApproximatePrice());
		}
		if (dto.getLink() != null && dto.getLink().matches(linkPattern)) {
			wish.setLink(dto.getLink());
		}

		Wish saved = wishRepository.save(wish);

		return modelMapper.map(saved, WishDto.class);
	}

	@Transactional
	@Override
	public void deleteByIdAndUser_Username(Long id, String username) {

		wishRepository.deleteByIdAndUser_Username(id, username);
	}

	@Transactional
	@Override
	public void deleteAllByUserId(Long id) {

		wishRepository.deleteAllByUser_Id(id);
	}

	@Transactional
	@Override
	public void deleteAllByUser_Username(String username) {

		wishRepository.deleteAllByUser_Username(username);
	}

	@Override
	public Page<WishDto> findAllFriendWishes(String friendUsername, String username, Pageable pageable) {
		Friendship byUserUsernameAndFriendUsername = friendshipRepository.findByUser_UsernameAndFriend_Username(username, friendUsername)
				.orElseThrow(() -> new FriendshipNotFoundException("[WishService.findAllFriendWishes] Friendship not found."));

		Page<Wish> allFriendWishes = wishRepository.findAllByUser_UsernameOrderByApproximatePriceDesc(friendUsername, pageable);
		return allFriendWishes.map((element) -> modelMapper.map(element, WishDto.class));
	}

	private void coupleWishToUser(Wish wish, User user) {
		wish.setUser(user);
		user.addWish(wish);
	}



}

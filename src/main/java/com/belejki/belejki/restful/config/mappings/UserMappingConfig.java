package com.belejki.belejki.restful.config.mappings;

import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.user.web.dto.UserDetailsResponseDto;
import com.belejki.belejki.restful.user.web.dto.UserDetailsShortDto;
import com.belejki.belejki.restful.user.web.dto.UserRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Configuration
public class UserMappingConfig {

	@Autowired
	public void configureUserMappings(ModelMapper modelMapper) {
		modelMapper.createTypeMap(User.class, UserDetailsResponseDto.class)
				.addMappings(mapper -> {

					mapper.map(User::getId, UserDetailsResponseDto::setId);
					mapper.map(User::getUsername, UserDetailsResponseDto::setUsername);
					mapper.map(User::getFirstName, UserDetailsResponseDto::setFirstName);
					mapper.map(User::getLastName, UserDetailsResponseDto::setLastName);
					mapper.map(User::isEnabled, UserDetailsResponseDto::setEnabled);
					mapper.map(User::getLastLogin, UserDetailsResponseDto::setLastLogin);
					mapper.map(User::isSetForDeletion, UserDetailsResponseDto::setSetForDeletion);
					mapper.map(User::getLocale, UserDetailsResponseDto::setLocale);
					mapper.map(User::getAuthorities, UserDetailsResponseDto::setAuthorities);
				});

		modelMapper.createTypeMap(UserRequestDto.class, User.class)
				.addMappings(mapper -> {

					mapper.map(UserRequestDto::getId, User::setId);
					mapper.map(UserRequestDto::getUsername, User::setUsername);
					mapper.map(UserRequestDto::getFirstName, User::setFirstName);
					mapper.map(UserRequestDto::getLastName, User::setLastName);
					mapper.map(UserRequestDto::isEnabled, User::setEnabled);
					mapper.map(UserRequestDto::getLastLogin, User::setLastLogin);
					mapper.map(UserRequestDto::isSetForDeletion, User::setSetForDeletion);
					mapper.map(UserRequestDto::getLocale, User::setLocale);
					mapper.map(UserRequestDto::getFriendships, User::setFriendships);
					mapper.map(UserRequestDto::getAuthorities, User::setAuthorities);
					mapper.map(UserRequestDto::getReminders, User::setReminders);
					mapper.map(UserRequestDto::getWishList, User::setWishList);
					mapper.map(UserRequestDto::getShoppingItems, User::setShoppingItems);
					mapper.map(UserRequestDto::getRecipes, User::setRecipes);
					mapper.map(UserRequestDto::getConfirmationToken, User::setConfirmationToken);
					mapper.map(UserRequestDto::getTokenExpiry, User::setTokenExpiry);
					// etc...
				});

		modelMapper.createTypeMap(User.class, UserDetailsShortDto.class)
				.addMappings(mapper -> {

					mapper.map(User::getId, UserDetailsShortDto::setId);
					mapper.map(User::getUsername, UserDetailsShortDto::setUsername);
					mapper.map(User::getFirstName, UserDetailsShortDto::setFirstName);
					mapper.map(User::getLastName, UserDetailsShortDto::setLastName);
				});
	}
}



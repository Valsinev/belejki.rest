package com.belejki.belejki.restful.user.service;

import com.belejki.belejki.restful.config.security.JwtTokenProvider;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.user.repository.UserRepository;
import com.belejki.belejki.restful.user.web.dto.LoginRequestDto;
import com.belejki.belejki.restful.user.web.dto.LoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoginServiceImpl implements LoginService {


	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public LoginServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public LoginResponseDto authenticate(LoginRequestDto request) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		User user = userRepository.findByUsername(userDetails.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		user.setLocale(request.getLocale());
		user.setLastLogin(LocalDate.now());
		userRepository.save(user);

		String token = jwtTokenProvider.generateToken(userDetails);
		return new LoginResponseDto(token, user);
	}
}

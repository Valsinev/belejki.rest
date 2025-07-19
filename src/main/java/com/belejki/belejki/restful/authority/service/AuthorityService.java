package com.belejki.belejki.restful.authority.service;

import com.belejki.belejki.restful.authority.domain.UserRoles;
import com.belejki.belejki.restful.authority.web.dto.AuthorityDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorityService {
	AuthorityDto save(@Valid AuthorityDto authorityDto);

	Page<AuthorityDto> findAll(Pageable pageable);

	AuthorityDto findById(Long id);

	Page<AuthorityDto> findAllByUser_Username(String username, Pageable pageable);

	Page<AuthorityDto> findAllByAuthority(UserRoles role, Pageable pageable);

	void deleteById(Long id);

	void deleteAllByUser_Username(String username);

	void deleteByUser_IdAndAuthority(Long id, UserRoles userRole);

	void deleteByUser_UsernameAndAuthority(@NotBlank String userUsername, @NotNull UserRoles authority);
}

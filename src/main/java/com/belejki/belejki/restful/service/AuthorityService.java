package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.dto.AuthorityDto;
import com.belejki.belejki.restful.entity.Authority;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.AuthorityAlreadyExistsException;
import com.belejki.belejki.restful.exception.AuthorityNotFoundException;
import com.belejki.belejki.restful.exception.AuthorityWrongFormatException;
import com.belejki.belejki.restful.exception.UserNotFoundException;
import com.belejki.belejki.restful.mapper.AuthorityMapper;
import com.belejki.belejki.restful.repository.AuthorityRepository;
import com.belejki.belejki.restful.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final AuthorityMapper authorityMapper;

    @Autowired
    public AuthorityService(AuthorityRepository repository, UserRepository userRepository, AuthorityMapper authorityMapper) {
        this.authorityRepository = repository;
        this.userRepository = userRepository;
        this.authorityMapper = authorityMapper;
    }


    public Authority save(AuthorityDto authorityDto, Long userId) {

        //check if authority belongs to UserRoles enumeration
        if (!checkIfValidRoleString(authorityDto.getAuthority())) {
            throw new AuthorityWrongFormatException(authorityDto.getAuthority());
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not Found."));

        //check if authority already exists for this user
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        List<String> roles = authorities.stream().map(auth -> auth.getAuthority()).toList();
        checkIfAuthorityExists(authorityDto.getAuthority(), roles);

        Authority authority = authorityMapper.toEntity(authorityDto);
        user.addAuthority(authority);
        authority.setUser(user);
        userRepository.save(user);
        return authority;
    }


    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }


    public Authority findById(Long id) {
        return authorityRepository.findById(id).orElseThrow(()-> new AuthorityNotFoundException("Authority not found with id: " + id));
    }

    public Page<Authority> findAllByAuthority(String role, Pageable pageable) {
        return authorityRepository.findAllByAuthorityAndUserIsNotNull(role, pageable);
    }

    public Authority updateById(Long id, AuthorityDto authorityDto) {

        if (!checkIfValidRoleString(authorityDto.getAuthority())) {
            throw new AuthorityWrongFormatException(authorityDto.getAuthority());
        }

        //check if authority exists
        Authority existing = findById(id);
        //check if user is present
        User user = userRepository.findById(authorityDto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found."));

        List<String> roles = user.getRoles().stream().map(Authority::getAuthority).toList();
        checkIfAuthorityExists(authorityDto.getAuthority(), roles);
        Authority updatedAuthority = authorityMapper.toEntity(authorityDto);

        existing.setUser(user);
        existing.setAuthority(updatedAuthority.getAuthority());
        authorityRepository.save(existing);
        return existing;
    }


    public Authority delete(@Valid AuthorityDto authority) {
        Authority byId = findById(authority.getId());
        authorityRepository.delete(byId);
        return byId;
    }

    public Authority deleteById(Long id) {
        Authority authority = findById(id);
        authorityRepository.delete(authority);
        return authority;
    }

    public List<Authority> deleteAllByUser_Username(String username) {
        List<Authority> founded = authorityRepository.findAllByUser_Username(username);
        authorityRepository.deleteAll(founded);
        return founded;
    }




    private boolean checkIfValidRoleString(@NotBlank String authority) {
        return Arrays.stream(UserRoles.values())
                .anyMatch(r -> r.name().equals(authority));
    }

    private static void checkIfAuthorityExists(String authorityRole, List<String> roles) {
        if (roles.contains(authorityRole)) {
            throw new AuthorityAlreadyExistsException(authorityRole);
        }
    }
}

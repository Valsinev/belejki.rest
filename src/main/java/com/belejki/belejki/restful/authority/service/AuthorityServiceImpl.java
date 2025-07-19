package com.belejki.belejki.restful.authority.service;

import com.belejki.belejki.restful.authority.web.dto.AuthorityDto;
import com.belejki.belejki.restful.authority.domain.Authority;
import com.belejki.belejki.restful.shared.exception.AuthorityAlreadyExistsException;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.authority.domain.UserRoles;
import com.belejki.belejki.restful.shared.exception.AuthorityNotFoundException;
import com.belejki.belejki.restful.shared.exception.AuthorityWrongFormatException;
import com.belejki.belejki.restful.shared.exception.user.UserNotFoundException;
import com.belejki.belejki.restful.authority.repository.AuthorityRepository;
import com.belejki.belejki.restful.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
	public AuthorityServiceImpl(AuthorityRepository authorityRepository, UserRepository userRepository, ModelMapper modelMapper) {
		this.authorityRepository = authorityRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}


	public AuthorityDto save(AuthorityDto authorityDto) {

        String username = authorityDto.getUserUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("[Authority] User not Found for username " + username));
        Authority authority = modelMapper.map(authorityDto, Authority.class);
        authority.setUser(user);

        List<Authority> allByUser = authorityRepository.findAllByUser(user);
        if (allByUser.contains(authority)) {
            throw new AuthorityAlreadyExistsException("[Authority] Authority already exists for user: " + username);
        }

        user.addAuthority(authority);

        authorityRepository.save(authority);
        return modelMapper.map(authority, AuthorityDto.class);
    }

    @Override
    public Page<AuthorityDto> findAll(Pageable pageable) {
        Page<Authority> all = authorityRepository.findAll(pageable);
        return all.map((element) -> modelMapper.map(element, AuthorityDto.class));
    }



    public AuthorityDto findById(Long id) {
        Authority authority = authorityRepository.findById(id).orElseThrow(() -> new AuthorityNotFoundException("Authority not found with id: " + id));
        return modelMapper.map(authority, AuthorityDto.class);
    }

    @Override
    public Page<AuthorityDto> findAllByUser_Username(String username, Pageable pageable) {
        Page<Authority> allByUserUsername = authorityRepository.findAllByUser_Username(username, pageable);
        return allByUserUsername.map((element) -> modelMapper.map(element, AuthorityDto.class));
    }

    public Page<AuthorityDto> findAllByAuthority(UserRoles role, Pageable pageable) {
        Page<Authority> allByAuthority = authorityRepository.findAllByAuthority(role, pageable);
        return allByAuthority.map((element) -> modelMapper.map(element, AuthorityDto.class));
    }

    @Transactional
    public void delete(@Valid AuthorityDto authority) {
        authorityRepository.deleteById(authority.getId());
    }

    @Transactional
    public void deleteById(Long id) {
        authorityRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByUser_Username(String username) {
        authorityRepository.deleteAllByUser_Username(username);
    }

    @Transactional
    @Override
    public void deleteByUser_IdAndAuthority(Long id, UserRoles userRole) {
        authorityRepository.deleteByUser_IdAndAuthority(id, userRole);
    }

    @Transactional
    @Override
    public void deleteByUser_UsernameAndAuthority(String username, UserRoles userRole) {
        authorityRepository.deleteByUser_UsernameAndAuthority(username, userRole);
    }

}

package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.AuthorityDto;
import com.belejki.belejki.restful.entity.Authority;
import com.belejki.belejki.restful.exception.UserNotFoundException;
import com.belejki.belejki.restful.mapper.AuthorityMapper;
import com.belejki.belejki.restful.repository.AuthorityRepository;
import com.belejki.belejki.restful.service.AuthorityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorityController {
    private final AuthorityService authorityService;
    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    @Autowired
    public AuthorityController(AuthorityService authorityService, AuthorityRepository authorityRepository, AuthorityMapper authorityMapper) {
        this.authorityService = authorityService;
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
    }


    //region POST METHODS

    @PostMapping("/admin/authorities/id/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorityDto saveAuthority(@Valid @RequestBody AuthorityDto authority,
                                      @PathVariable Long userId,
                                      Authentication authentication) {
        checkForPermission(authentication);
        return authorityMapper.toDtoLong(authorityService.save(authority, userId));
    }

    //endregion

    //region GET METHODS
    @GetMapping("/admin/authorities")
    public Page<AuthorityDto> findAllAuthorities(Pageable pageable){
        return authorityRepository.findAllByUserIsNotNull(pageable).map(authorityMapper::toDtoLong);

    }

    @GetMapping("/admin/authorities/id/{id}")
    public AuthorityDto findById(@PathVariable Long id) {
        return authorityMapper.toDtoLong(authorityService.findById(id));
    }

    @GetMapping("/admin/authorities/null")
    public Page<Authority> findAllWithNullUser(Pageable pageable){
        return authorityRepository.findAllByUserIsNull(pageable);

    }

    @GetMapping("/admin/authorities/{username}")
    public Page<AuthorityDto> findAllByUserId(@PathVariable String username, Pageable pageable) {
        return authorityRepository.findAllByUser_Username(username, pageable).map(authorityMapper::toDtoLong);
    }

    @GetMapping("/admin/authorities/role/{role}")
    public Page<AuthorityDto> findAllByAuthority(@PathVariable String role, Pageable pageable) {
        return authorityService.findAllByAuthority(role, pageable).map(authorityMapper::toDtoLong);
    }
    //endregion

    //region PUT METHODS

    @PutMapping("/admin/authorities/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorityDto updateById(@PathVariable Long id,
                                   @Valid @RequestBody AuthorityDto authorityDto,
                                   Authentication authentication) {
        if (authorityDto.getUserId() == null) {
            throw new UserNotFoundException("Expecting non null field 'userId'.");
        }
        checkForPermission(authentication);

        return authorityMapper.toDtoLong(authorityService.updateById(id, authorityDto));
    }
    //

    //region DELETE METHODS

    @DeleteMapping("/admin/authorities/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorityDto> deleteById(@PathVariable Long id,
                                                Authentication authentication) {
        checkForPermission(authentication);
        AuthorityDto dtoLong = authorityMapper.toDtoLong(authorityService.deleteById(id));
        return ResponseEntity.ok(dtoLong);
    }

    @DeleteMapping("/admin/authorities/user/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuthorityDto>> deleteAllByUser_Username(@PathVariable String username,
                                                                       Authentication authentication) {
        checkForPermission(authentication);
        List<AuthorityDto> deleted = authorityService.deleteAllByUser_Username(username).stream().map(authorityMapper::toDtoLong).toList();
        return ResponseEntity.ok(deleted);
    }


    //endregion



    private static void checkForPermission(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        if (!isAdmin) {
            throw new AccessDeniedException("You are not allowed to delete users.");
        }
    }
}

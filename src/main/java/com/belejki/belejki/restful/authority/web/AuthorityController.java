package com.belejki.belejki.restful.authority.web;

import com.belejki.belejki.restful.authority.domain.UserRoles;
import com.belejki.belejki.restful.authority.service.AuthorityService;
import com.belejki.belejki.restful.authority.web.dto.AuthorityDto;
import com.belejki.belejki.restful.shared.AuthService;
import com.belejki.belejki.restful.shared.exception.AuthorityAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorityController {

    private final AuthorityService authorityService;
    private final AuthService authService;

	public AuthorityController(AuthorityService authorityService, AuthService authService) {
		this.authorityService = authorityService;
		this.authService = authService;
	}


	//region POST METHODS

    @PostMapping("/admin/authorities")
    public ResponseEntity<AuthorityDto> saveAuthority(@Valid @RequestBody AuthorityDto authorityDto,
                                      BindingResult bindingResult,
                                      Authentication authentication) {

        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            AuthorityDto saved = authorityService.save(authorityDto);
            return ResponseEntity.ok(saved);
        } catch (AuthorityAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //endregion

    //region GET METHODS
    @GetMapping("/admin/authorities")
    public ResponseEntity<Page<AuthorityDto>> findAll(Authentication authentication, Pageable pageable){

        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<AuthorityDto> all = authorityService.findAll(pageable);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/admin/authorities/id/{id}")
    public ResponseEntity<AuthorityDto> findById(@PathVariable Long id, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AuthorityDto byId = authorityService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @GetMapping("/admin/authorities/{username}")
    public ResponseEntity<Page<AuthorityDto>> findAllByUser_Username(@PathVariable String username, Authentication authentication, Pageable pageable) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<AuthorityDto> allByUser_Username = authorityService.findAllByUser_Username(username, pageable);
        return ResponseEntity.ok(allByUser_Username);
    }

    @GetMapping("/admin/authorities/role/{role}")
    public ResponseEntity<Page<AuthorityDto>> findAllByAuthority(@PathVariable UserRoles role, Authentication authentication, Pageable pageable) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<AuthorityDto> allByAuthority = authorityService.findAllByAuthority(role, pageable);
        return ResponseEntity.ok(allByAuthority);
    }
    //endregion


    //region DELETE METHODS

    @DeleteMapping("/admin/authorities/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id,
                                                Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        authorityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/authorities/user/{username}")
    public ResponseEntity<Void> deleteAllByUser_Username(@PathVariable String username,
                                                                       Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        authorityService.deleteAllByUser_Username(username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/authorities")
    public ResponseEntity<Page<Void>> deleteUserRole(@Valid @RequestBody AuthorityDto authorityDto, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        authorityService.deleteByUser_UsernameAndAuthority(authorityDto.getUserUsername(), authorityDto.getAuthority());
        return ResponseEntity.ok().build();
    }


    //endregion

}

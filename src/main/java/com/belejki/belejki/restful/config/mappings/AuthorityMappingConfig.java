package com.belejki.belejki.restful.config.mappings;

import com.belejki.belejki.restful.authority.domain.Authority;
import com.belejki.belejki.restful.authority.web.dto.AuthorityDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorityMappingConfig {

	@Autowired
	public void configureAuthorityMappings(ModelMapper modelMapper) {

		modelMapper.createTypeMap(Authority.class, AuthorityDto.class)
				.addMappings(mapping -> {
					mapping.map(Authority::getId, AuthorityDto::setId);
					mapping.map(authority -> authority.getUser().getUsername(), AuthorityDto::setUser);
					mapping.map(Authority::getAuthority, AuthorityDto::setAuthority);
				});

		modelMapper.createTypeMap(AuthorityDto.class, Authority.class)
				.addMappings(mapping -> {
					mapping.skip(Authority::setUser);

					mapping.map(AuthorityDto::getId, Authority::setId);
					mapping.map(AuthorityDto::getAuthority, Authority::setAuthority);
				});

	}
}

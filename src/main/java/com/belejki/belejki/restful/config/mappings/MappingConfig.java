package com.belejki.belejki.restful.config.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MappingConfig {


	@Bean
	public ModelMapper getMapper() {
		ModelMapper modelMapper = new ModelMapper();

		//turn off default mapping
		modelMapper.getConfiguration()
				.setAmbiguityIgnored(true)
				.setSkipNullEnabled(true)
				.setImplicitMappingEnabled(false);

		return modelMapper;
	}

}

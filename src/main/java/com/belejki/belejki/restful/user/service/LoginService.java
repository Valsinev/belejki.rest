package com.belejki.belejki.restful.user.service;

import com.belejki.belejki.restful.user.web.dto.LoginRequestDto;
import com.belejki.belejki.restful.user.web.dto.LoginResponseDto;
import jakarta.validation.Valid;

public interface LoginService {
	LoginResponseDto authenticate(@Valid LoginRequestDto request);
}

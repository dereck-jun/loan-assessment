package com.loan.assessment.auth.controller;

import com.loan.assessment.auth.dto.request.RegisterRequest;
import com.loan.assessment.auth.dto.response.RegisterResponse;
import com.loan.assessment.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/v1/users/register")
	public ResponseEntity<RegisterResponse> userRegister(
		@RequestBody RegisterRequest request, HttpHeaders headers
	) {
		RegisterResponse response = authService.userRegistration(
			request.getEmail(),
			request.getPassword(),
			request.getConfirmPassword(),
			request.getName(),
			request.getPhoneNumber(),
			request.getNickname()
		);

		headers.add("Authorization", response.getAccessToken());
		headers.add("refresh_token", response.getRefreshToken());

		return ResponseEntity.ok().headers(headers).body(response);
	}
}

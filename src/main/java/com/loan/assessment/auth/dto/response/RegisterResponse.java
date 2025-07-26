package com.loan.assessment.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterResponse {

	private Long userId;
	private String accessToken;
	private String refreshToken;

	@Builder
	private RegisterResponse(Long userId, String accessToken, String refreshToken) {
		this.userId = userId;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}

package com.loan.assessment.auth.service;

import com.loan.assessment.auth.dto.response.RegisterResponse;
import com.loan.assessment.global.jwt.JwtProvider;
import com.loan.assessment.user.entity.User;
import com.loan.assessment.user.repository.UserJpaRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.loan.assessment.user.entity.User.createNewUser;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserJpaRepository userJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	private final JwtProvider jwtProvider;

	@Transactional
	public RegisterResponse userRegistration(String email, String password, String confirmPassword, String name,
		String phoneNumber, String nickname) {
		if (!password.equals(confirmPassword)) {
			throw new RuntimeException("Passwords do not match");
		}

		if (userJpaRepository.existsByEmail(email)) {
			throw new RuntimeException("Email already exists");
		}

		if (StringUtils.hasText(nickname)) {
			if (userJpaRepository.existsByNickname(nickname)) {
				throw new RuntimeException("Nickname already exists");
			}
		} else {
			nickname = email.substring(0, email.indexOf("@")) + "_" + UUID.randomUUID().toString().substring(0, 7);
		}

		User newUser = userJpaRepository.save(
			createNewUser(email, passwordEncoder.encode(password), name, nickname, phoneNumber)
		);

		String accessToken = jwtProvider.createAccessToken(newUser.getId(), newUser.getRole().getValue());
		String refreshToken = jwtProvider.createRefreshToken(newUser.getId(), newUser.getRole().getValue());

		tokenService.saveRefreshToken(newUser.getId(), refreshToken);

		return RegisterResponse.builder()
			.userId(newUser.getId())
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}

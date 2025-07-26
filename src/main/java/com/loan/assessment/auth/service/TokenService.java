package com.loan.assessment.auth.service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
	private static final String REFRESH_TOKEN_PREFIX = "REFRESH_TOKEN:";
	private static final long REFRESH_TOKEN_EXPIRES_TIME = 60 * 60 * 24 * 14;	// 초 단위
	private final RedisTemplate<String, Object> redisTemplate;

	public void saveRefreshToken(Long userId, String refreshToken) {
		String key = REFRESH_TOKEN_PREFIX + userId;

		redisTemplate.opsForValue()
			.set(key, refreshToken, REFRESH_TOKEN_EXPIRES_TIME, TimeUnit.SECONDS);
	}

	public String getRefreshToken(Long userId) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		return Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
	}

	public boolean hasRefreshToken(Long userId) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		return redisTemplate.hasKey(key);
	}

	public void deleteRefreshToken(Long userId) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		redisTemplate.delete(key);
	}

	public void extendExpiration(Long userId) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		redisTemplate.expire(key, REFRESH_TOKEN_EXPIRES_TIME, TimeUnit.SECONDS);
	}

	public long getExpiration(Long userId) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}
}

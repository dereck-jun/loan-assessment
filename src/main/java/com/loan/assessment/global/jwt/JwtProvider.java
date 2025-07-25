package com.loan.assessment.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

	private static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 30;
	private static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 14;
	private static final String PREFIX_BEARER = "Bearer ";

	@Value("${jwt.secret.key}")
	private String secretKey;
	private Key key;

	@PostConstruct
	public void init() {
		byte[] decoded = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(decoded);
	}

	private String generateToken(Long userId, String role, long exp) {
		String jti = UUID.randomUUID().toString();
		Date now = new Date();
		Date end = new Date(now.getTime() + exp);
		return PREFIX_BEARER + Jwts.builder()
			.subject(String.valueOf(userId))
			.claim("jti", jti)
			.claim("role", role)
			.issuer("loan-assessment")
			.signWith(key)
			.issuedAt(now)
			.expiration(end)
			.compact();
	}

	public String createAccessToken(Long userId, String role) {
		return generateToken(userId, role, ACCESS_TOKEN_VALIDITY);
	}

	public String createRefreshToken(Long userId, String role) {
		return generateToken(userId, role, REFRESH_TOKEN_VALIDITY);
	}

	public Claims getClaims(String token) {
		return Jwts.parser()
			.verifyWith((SecretKey) key)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}

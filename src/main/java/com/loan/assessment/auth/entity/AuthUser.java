package com.loan.assessment.auth.entity;

import com.loan.assessment.user.enums.UserRole;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthUser {

	private Long userId;
	private String email;
	private List<GrantedAuthority> authorities;

	public AuthUser(Long userId, String email, UserRole role) {
		this.userId = userId;
		this.email = email;
		this.authorities = List.of(new SimpleGrantedAuthority(role.getValue()));
	}
}

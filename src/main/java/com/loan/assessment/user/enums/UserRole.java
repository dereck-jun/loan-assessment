package com.loan.assessment.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String value;

	public static UserRole of(String input) {
		for (UserRole role : values()) {
			if (role.value.equals(input)) {
				return role;
			}
		}
		throw new RuntimeException("입력한 값에 해당하는 UserRole을 찾을 수 없습니다.");
	}
}

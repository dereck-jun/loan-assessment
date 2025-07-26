package com.loan.assessment.user.entity;

import com.loan.assessment.global.common.entity.BaseTime;
import com.loan.assessment.user.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;

	private String email;

	private String password;

	private String name;

	private String nickname;

	private String phoneNumber;

	private UserRole role;

	@Builder(access = AccessLevel.PROTECTED)
	private User(String email, String password, String name, String nickname, String phoneNumber, UserRole role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public static User createNewUser(String email, String password, String name, String nickname, String phoneNumber) {
		return User.builder()
			.email(email)
			.password(password)
			.name(name)
			.nickname(nickname)
			.phoneNumber(phoneNumber)
			.role(UserRole.USER)
			.build();
	}

	public static User createNewAdmin(String email, String password, String name, String nickname, String phoneNumber) {
		return User.builder()
			.email(email)
			.password(password)
			.name(name)
			.nickname(nickname)
			.phoneNumber(phoneNumber)
			.role(UserRole.ADMIN)
			.build();
	}
}

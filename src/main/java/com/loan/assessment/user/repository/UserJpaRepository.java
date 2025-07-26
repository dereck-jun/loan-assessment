package com.loan.assessment.user.repository;

import com.loan.assessment.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	// 전체 유저 대상 이메일 중복 검사
	@Query("select count(u) > 0 from User u where u.email = :email")
	boolean existsByEmail(@Param("email") String email);

	// 전체 유저 대상 닉네임 중복 검사
	@Query("select count(u) > 0 from User u where u.nickname = :nickname")
	boolean existsByNickname(@Param("nickname") String nickname);

	// 활성 유저 대상 유저 단건 검색
	@Query("select u from User u where u.id = :userId and u.deletedAt is null")
	Optional<User> findActiveUserById(@Param("userId") Long userId);

}

package jp.mirageworld.spring.bbs.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

/**
 * 権限.
 */
public enum Roles implements GrantedAuthority {

	ROLE_USER,
	ROLE_ADMIN(ROLE_USER),
	USER(ROLE_USER),
	ADMIN(ROLE_ADMIN);

	@Getter
	List<Roles> authorities;

	@Getter
	final String authority;

	private Roles(Roles... roles) {
		this.authorities = new ArrayList<>();
		this.authorities.add(this);
		this.authorities.addAll(Arrays.asList(roles));
		this.authority = name();
	}

}

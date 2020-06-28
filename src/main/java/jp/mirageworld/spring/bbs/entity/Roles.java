package jp.mirageworld.spring.bbs.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * 権限.
 */
public enum Roles implements GrantedAuthority {

	ROLE_USER,
	ROLE_ADMIN;

	@Override
	public String getAuthority() {
		return name();
	}

}

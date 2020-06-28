package jp.mirageworld.spring.bbs.entity;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class Users
		implements UserDetails {

	String id;

	String username;

	String email;

	String password;

	@Override
	public List<Roles> getAuthorities() {
		return Arrays.asList(Roles.ROLE_USER);
	}

	@Override
	public boolean isAccountNonExpired() {
		// アカウントの有効期限（無制限）
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// アカウントのロック（しない）
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// パスワードの有効期限（無制限）
		return true;
	}

	@Override
	public boolean isEnabled() {
		// アカウントの有効判定（常に有効）
		return true;
	}
}

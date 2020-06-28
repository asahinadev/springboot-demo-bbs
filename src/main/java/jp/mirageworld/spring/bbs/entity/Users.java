package jp.mirageworld.spring.bbs.entity;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * ユーザー情報
 * 
 * @author alice
 *
 */
@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@Document("users")
public class Users
		implements UserDetails {

	public Users() {
		authorities = Arrays.asList(Roles.ROLE_USER);
		accountNonExpired = true;
		accountNonLocked = true;
		credentialsNonExpired = true;
		enabled = true;
	}

	/**
	 * ID.
	 */
	String id;

	/**
	 * アカウント.
	 */
	String username;

	/**
	 * メールアドレス（アカウント代替）.
	 */
	String email;

	/**
	 * パスワード.
	 */
	String password;

	/**
	 * 権限.
	 */
	final List<Roles> authorities;

	/**
	 * アカウント有効期間内.
	 */
	final boolean accountNonExpired;

	/**
	 * アカウントアンロック状態.
	 */
	final boolean accountNonLocked;

	/**
	 * パスワード有効期間内.
	 */
	final boolean credentialsNonExpired;

	/**
	 * アカウント有効状態.
	 */
	final boolean enabled;
}

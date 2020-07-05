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
		authorities = Arrays.asList(Roles.USER);
		accountNonExpired = true;
		accountNonLocked = true;
		credentialsNonExpired = true;
		enabled = true;
	}

	String id;
	String username;
	String email;
	String password;
	List<Roles> authorities;
	boolean accountNonExpired;
	boolean accountNonLocked;
	boolean credentialsNonExpired;
	boolean enabled;
}

package jp.mirageworld.spring.bbs.form;

import lombok.Data;

/**
 * ログインフォーム.
 */
@Data
public class LoginForm {

	/**
	 * アカウント.
	 */
	private String username;

	/**
	 * パスワード.
	 */
	private String password;

}

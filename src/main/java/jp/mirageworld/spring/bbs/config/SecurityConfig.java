package jp.mirageworld.spring.bbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * セキュリティ設定.
 */
@EnableWebFluxSecurity
public class SecurityConfig {

	/**
	 * [bean] パスワードエンコーダー.
	 * 
	 * @return {@link PasswordEncoder}
	 */
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * [bean] セキュリティ設定.
	 * 
	 * @param http {@link ServerHttpSecurity}
	 * @return {@link SecurityWebFilterChain}
	 * @throws Exception 設定時エラー
	 */
	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
		return http
				// 認証関連
				.authorizeExchange()
				.pathMatchers("/abouts**").permitAll()
				.pathMatchers("/policy**").permitAll()
				.pathMatchers("/login**").permitAll()
				.pathMatchers("/signup**").permitAll()
				.pathMatchers("/webjars**").permitAll()
				.pathMatchers("/assets/**").permitAll()
				.anyExchange().authenticated()
				.and()

				// 匿名ユーザー（ログイン画面へのリダイレクトがされないため無効化
				// .anonymous()
				// .and()

				// BASIC 認証設定（基本無効）
				.httpBasic()
				.disable()

				// FORM 認証設定（基本有効）
				.formLogin()
				.loginPage("/login")
				.and()

				// ログアウト
				.logout()
				.logoutUrl("/logout")
				.and()

				// CORS（無効）
				.cors()
				.disable()

				// CSRF（無効）
				.csrf()
				.disable()

				// 確定
				.build();
	}
}
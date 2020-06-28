package jp.mirageworld.spring.bbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
		return http
				// 認証可否
				.authorizeExchange()
				.pathMatchers("/abouts**").permitAll()
				.pathMatchers("/policy**").permitAll()
				.pathMatchers("/login**").permitAll()
				.pathMatchers("/signup**").permitAll()
				.pathMatchers("/webjars**").permitAll()
				.pathMatchers("/assets/**").permitAll()
				.anyExchange().authenticated()
				.and()

				// 匿名ユーザー
				// .anonymous()
				// .and()

				// BASIC 認証
				.httpBasic()
				.disable()

				// FORM 認証
				.formLogin()
				.loginPage("/login")
				.and()

				// ログアウト
				.logout()
				.logoutUrl("/logout")
				.and()

				// Cross-Origin Resource Sharing
				.cors()
				.disable()

				// Cross Site Request Forgeries
				.csrf()
				.disable()

				.build();
	}
}
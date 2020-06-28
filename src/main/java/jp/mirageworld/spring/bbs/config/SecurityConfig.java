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
				// �F�؉�
				.authorizeExchange()
				.pathMatchers("/abouts**").permitAll()
				.pathMatchers("/policy**").permitAll()
				.pathMatchers("/login**").permitAll()
				.pathMatchers("/signup**").permitAll()
				.pathMatchers("/webjars**").permitAll()
				.pathMatchers("/assets/**").permitAll()
				.anyExchange().authenticated()
				.and()

				// �������[�U�[
				// .anonymous()
				// .and()

				// BASIC �F��
				.httpBasic()
				.disable()

				// FORM �F��
				.formLogin()
				.loginPage("/login")
				.and()

				// ���O�A�E�g
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
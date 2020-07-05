package jp.mirageworld.spring.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jp.mirageworld.spring.bbs.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class LoginService
		implements ReactiveUserDetailsService {

	@Autowired
	UsersRepository usersRepository;

	@Override
	public Mono<UserDetails> findByUsername(String username) {

		log.debug("username => {}", username);

		return Mono.<UserDetails>create((e) -> {
			e.success(usersRepository.findByUsername(username));
		}).or(Mono.create((e) -> {
			e.success(usersRepository.findByEmail(username));
		}));
	}
}

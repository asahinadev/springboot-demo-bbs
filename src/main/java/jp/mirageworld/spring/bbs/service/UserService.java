package jp.mirageworld.spring.bbs.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.mirageworld.spring.bbs.entity.Users;
import jp.mirageworld.spring.bbs.form.UserForm;
import jp.mirageworld.spring.bbs.repository.UsersRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService
		implements UserDetailsService {

	public static final int START_PAGE = 0;

	public static final int DEFAULT_SIZE = 20;

	@Autowired
	UsersRepository users;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public Users loadUserByUsername(String username)
			throws UsernameNotFoundException {

		return findByUsername(username)
				.or(findByEmail(username))
				.doOnError(e -> new UsernameNotFoundException(""))
				.block();
	}

	public Mono<Users> insert(UserForm form) {

		Users users = new Users();
		BeanUtils.copyProperties(form, users, "password");
		users.setPassword(encoder.encode(form.getPassword()));

		return insert(users);
	}

	public Mono<Users> insert(Users entity) {

		return Mono.create(e -> {
			e.success(users.insert(entity));
		});
	}

	public Mono<Users> update(Users users, UserForm form) {

		BeanUtils.copyProperties(form, users, "password");
		return update(users);
	}

	public Mono<Users> update(Users entity) {

		return Mono.create(e -> {
			e.success(users.save(entity));
		});
	}

	public Mono<Users> delete(String id) {

		return delete(findById(id));
	}

	public Mono<Users> delete(UserForm form) {

		return delete(findByUsername(form.getUsername()));
	}

	public Mono<Users> delete(Users users) {
		return delete(Mono.create(e -> {
			e.success(users);
		}));
	}

	public Mono<Users> deleteById(String id) {
		return findById(id).doOnNext(users::delete);
	}

	public Mono<Users> delete(Mono<Users> user) {
		return user.doOnNext(users::delete);
	}

	public Mono<Users> findById(String id) {
		return Mono.fromCallable(() -> {
			return users.findById(id).get();
		});
	}

	public Mono<Users> findByUsername(String username) {
		return Mono.fromCallable(() -> {
			return users.findByUsername(username);
		});
	}

	public Mono<Users> findByEmail(String email) {
		return Mono.fromCallable(() -> {
			return users.findByEmail(email);
		});
	}

	public Flux<Users> findAll(Pageable pageable) {
		return Flux.fromIterable(users.findAll(pageable));
	}
}

package jp.mirageworld.spring.bbs.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jp.mirageworld.spring.bbs.entity.Users;
import jp.mirageworld.spring.bbs.exception.UniqueElementsException;
import jp.mirageworld.spring.bbs.form.UserForm;
import jp.mirageworld.spring.bbs.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ユーザー情報サービス.
 */
@Slf4j
@Service
public class UsersService {

	public static final int START_PAGE = 0;

	public static final int DEFAULT_SIZE = 20;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * 登録.
	 * 
	 * @param form 情報（フォーム）
	 * @return 結果
	 */
	public Mono<Users> insert(UserForm form) {
		log.debug("form {}", form);

		// username の重複をチェック
		findByUsername(form.getUsername()).doAfterTerminate(() -> {
			new UniqueElementsException("username", "アカウントが重複しています");
		});

		// email の重複をチェック
		findByEmail(form.getEmail()).and(e -> {
			new UniqueElementsException("email", "メールアドレスが重複しています");
		});

		Users entity = new Users();
		BeanUtils.copyProperties(form, entity, "password");
		entity.setPassword(passwordEncoder.encode(form.getPassword()));

		return insert(entity);
	}

	/**
	 * 登録.
	 * 
	 * @param entity 情報（実態）
	 * @return 結果
	 */
	public Mono<Users> insert(Users entity) {
		log.debug("entity {}", entity);
		return Mono.just(usersRepository.insert(entity));
	}

	/**
	 * 更新
	 * 
	 * @param entity 情報（実態）
	 * @param form   情報（フォーム）
	 * @return 結果
	 */
	public Mono<Users> update(Users entity, UserForm form) {
		log.debug("form {}", form);
		findByUsername(form.getUsername(), entity.getId()).doOnNext(e -> {
			new UniqueElementsException("username", "アカウントが重複しています");
		});
		findByEmail(form.getEmail(), entity.getId()).doOnNext(e -> {
			new UniqueElementsException("email", "メールアドレスが重複しています");
		});
		BeanUtils.copyProperties(form, entity, "password");
		if (!StringUtils.isEmpty(form.getPassword())) {
			entity.setPassword(passwordEncoder.encode(form.getPassword()));
		}
		return update(entity);
	}

	/**
	 * 更新
	 * 
	 * @param entity 情報（実態）
	 * @return 結果
	 */
	public Mono<Users> update(Users entity) {
		log.debug("entity {}", entity);
		return Mono.just(usersRepository.save(entity));
	}

	/**
	 * 削除
	 * 
	 * @param id 情報（ID)
	 * @return 結果
	 */
	public Mono<Users> delete(String id) {
		log.debug("id {}", id);
		return delete(findById(id));
	}

	/**
	 * 削除
	 * 
	 * @param form 情報（フォーム）
	 * @return
	 */
	public Mono<Users> delete(UserForm form) {
		log.debug("form {}", form);
		return delete(findByUsername(form.getUsername()));
	}

	/**
	 * 削除
	 * 
	 * @param entity 情報（実態）
	 * @return 結果
	 */
	public Mono<Users> delete(Users entity) {
		return delete(Mono.just(entity));
	}

	/**
	 * 削除.
	 * 
	 * @param user 情報（実態）
	 * @return 結果
	 */
	public Mono<Users> delete(Mono<Users> user) {
		return user.doOnNext(usersRepository::delete);
	}

	/**
	 * 検索
	 * 
	 * @param id {@link Users#getId()}
	 * @return 結果
	 */
	public Mono<Users> findById(String id) {
		return Mono.just(usersRepository.findById(id).get());
	}

	/**
	 * 検索
	 * 
	 * @param username {@link Users#getUsername()}
	 * @return 結果
	 */
	public Mono<Users> findByUsername(String username) {
		return Mono.just(usersRepository.findByUsername(username));
	}

	/**
	 * 検索
	 * 
	 * @param username {@link Users#getUsername()}
	 * @param notId    含めないID
	 * @return 結果
	 */
	public Mono<Users> findByUsername(String username, String notId) {
		return Mono.just(usersRepository.findByUsernameAndIdNot(username, notId));
	}

	/**
	 * 検索
	 * 
	 * @param email {@link Users#getEmail()}
	 * @return 結果
	 */
	public Mono<Users> findByEmail(String email) {
		return Mono.just(usersRepository.findByEmail(email));
	}

	/**
	 * 検索
	 * 
	 * @param email {@link Users#getEmail()}
	 * @param notId 含めないID
	 * @return 結果
	 */
	public Mono<Users> findByEmail(String email, String notId) {
		return Mono.just(usersRepository.findByEmailAndIdNot(email, notId));
	}

	/**
	 * 検索
	 * 
	 * @param pageable {@link Pageable}
	 * @return 結果
	 */
	public Flux<Users> findAll() {
		return Flux.fromIterable(usersRepository.findAll());
	}
}

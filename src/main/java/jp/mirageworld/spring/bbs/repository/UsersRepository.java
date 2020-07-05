package jp.mirageworld.spring.bbs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import jp.mirageworld.spring.bbs.entity.Users;

/**
 * ユーザー情報処理.
 */
public interface UsersRepository
		extends MongoRepository<Users, String> {

	/**
	 * アカウント検索.
	 * 
	 * @param username 対象アカウント
	 * 
	 * @return 対象
	 */
	public Users findByUsername(String username);

	/**
	 * アカウント検索.
	 * 
	 * @param username 対象アカウント
	 * @param id       含めない ID
	 * 
	 * @return 対象
	 */
	public Users findByUsernameAndIdNot(String username, String id);

	/**
	 * アカウント検索.
	 * 
	 * @param email 対象メールアドレス.
	 * 
	 * @return 対象
	 */
	public Users findByEmail(String email);

	/**
	 * アカウント検索.
	 * 
	 * @param email 対象メールアドレス.
	 * @param id    含めない ID
	 * 
	 * @return 対象
	 */
	public Users findByEmailAndIdNot(String email, String id);

}

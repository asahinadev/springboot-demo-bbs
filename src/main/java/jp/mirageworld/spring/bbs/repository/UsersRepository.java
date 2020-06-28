package jp.mirageworld.spring.bbs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import jp.mirageworld.spring.bbs.entity.Users;

public interface UsersRepository
		extends MongoRepository<Users, String> {

	public Users findByUsername(String username);

	public Users findByEmail(String email);

}

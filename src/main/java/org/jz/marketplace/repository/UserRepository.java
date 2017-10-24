package org.jz.marketplace.repository;

import java.util.List;

import org.jz.marketplace.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByUsername(String username);
	List<User> findTop10ByOrderByUserId();
	
}
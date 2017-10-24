package org.jz.marketplace.repository;

import java.util.List;

import org.jz.marketplace.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByUsername(String username);
	List<User> findTop25ByOrderByUserId();
	
}
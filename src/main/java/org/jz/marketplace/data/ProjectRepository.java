package org.jz.marketplace.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "projects", path = "projects")
@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	List<Project> findProjectsBySellerOrderByDeadlineAsc(@Param("seller") User seller);
	List<Project> findProjectsByOrderByDeadlineAsc();
}
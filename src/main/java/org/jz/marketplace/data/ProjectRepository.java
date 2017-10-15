package org.jz.marketplace.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "projects", path = "projects")
@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

}
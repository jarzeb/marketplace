package org.jz.marketplace.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectArchive extends CrudRepository<Project, Long> {

}
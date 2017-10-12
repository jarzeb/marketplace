package org.jz.marketplace.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "bids", path = "bids")
public interface BidRepository extends CrudRepository<Bid, Long> {
	
	Bid findTopBidByProjectOrderByAmountAsc(@Param("project") Project project);
	
}
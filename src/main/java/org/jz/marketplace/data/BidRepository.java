package org.jz.marketplace.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "bids", path = "bids")
public interface BidRepository extends CrudRepository<Bid, Long> {
	
	List<Bid> findBidsByProjectOrderByAmountDesc(@Param("project") Project project);
	Bid findFirstBidByProjectOrderByAmount(@Param("project") Project project);
}
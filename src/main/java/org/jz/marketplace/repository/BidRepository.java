package org.jz.marketplace.repository;

import java.util.List;

import org.jz.marketplace.model.Bid;
import org.jz.marketplace.model.Project;
import org.jz.marketplace.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel = "bids", path = "bids")
@RepositoryRestResource(exported = false)
public interface BidRepository extends CrudRepository<Bid, Long> {

	List<Bid> findTop100BidsByBuyerOrderByBidDateTimeAsc(@Param("buyer") User buyer);
	List<Bid> findTop100BidsByProjectOrderByBidDateTimeAsc(@Param("project") Project project);
	
	Bid findFirstBidByProjectOrderByAmount(@Param("project") Project project);

	// used for archiving old bids
	List<Bid> findBidsByProject(@Param("project") Project project);
}
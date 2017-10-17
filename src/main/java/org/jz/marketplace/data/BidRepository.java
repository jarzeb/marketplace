package org.jz.marketplace.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "bids", path = "bids")
@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {

	List<Bid> findTop100BidsByBuyerOrderByBidDateTimeAsc(@Param("buyer") User buyer);
	List<Bid> findTop100BidsByProjectOrderByBidDateTimeAsc(@Param("project") Project project);
	
	Bid findFirstBidByProjectOrderByAmount(@Param("project") Project project);

	// used for archiving old bids
	List<Bid> findBidsByProject(@Param("project") Project project);
}
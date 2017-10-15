package org.jz.marketplace.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "bids", path = "bids")
@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {
	
	List<Bid> findBidsByProjectOrderByAmountDesc(@Param("project") Project project);
	
	Bid findFirstBidByProjectOrderByAmount(@Param("project") Project project);
	
	List<Bid> findBidsByBuyerOrderByBidDateTimeDesc(@Param("buyer") User buyer);
}
package org.jz.marketplace.controller;

import java.util.List;
import java.util.Map;

import org.jz.marketplace.data.Bid;
import org.jz.marketplace.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidController {
		
		@Autowired
		private BidService bidService;

		@RequestMapping(value = "/createBid", method = RequestMethod.POST)
		public Map<String,String> createBid(@RequestBody Bid bid) {			
			Map<String,String> result = bidService.createBid(bid);
			return result;
		}
		
		@RequestMapping(value = "/findBidsByBuyerId", method = RequestMethod.GET)
		public List<Map<String,String>> findBidsByBuyerId(@Param("buyerId") long buyerId) {
			List<Map<String,String>> result = bidService.findBidsByBuyerId(buyerId);
			return result;
		}
		
}

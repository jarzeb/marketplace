package org.jz.marketplace.controller;

import java.util.Map;

import org.jz.marketplace.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidController {
		
		@Autowired
		private BidService bidService;

		@RequestMapping(value = "/createBid", method = RequestMethod.POST)
		public Map<String,String> createBid(
				@Param("projectId") long projectId,
				@Param("buyerId") long buyerId,
				@Param("amount") int amount) {
			
			Map<String,String> result = bidService.createBid(projectId, buyerId, amount);
			
			return result;
		}
		
}

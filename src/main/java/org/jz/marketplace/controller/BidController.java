package org.jz.marketplace.controller;

import java.util.List;
import java.util.Map;

import org.jz.marketplace.model.Bid;
import org.jz.marketplace.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidController {
		
		@Autowired
		private BidService bidService;

		@RequestMapping(value = "/createBid", method = RequestMethod.POST)
		public ResponseEntity<Map<String,String>> createBid(@RequestBody Bid bid) {			
			Map<String,String> result = bidService.createBid(bid);
			HttpStatus httpStatus = result.containsKey("error") ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
			return new ResponseEntity<Map<String,String>>(result, httpStatus);
		}
		
		@RequestMapping(value = "/findBidsByBuyerId", method = RequestMethod.GET)
		public ResponseEntity<List<Map<String,String>>> findBidsByBuyerId(@Param("buyerId") long buyerId) {
			List<Map<String,String>> result = bidService.findBidsByBuyerId(buyerId);
			return new ResponseEntity<List<Map<String,String>>>(result, HttpStatus.OK);
		}
		
}

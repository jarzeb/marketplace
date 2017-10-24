package org.jz.marketplace.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.jz.marketplace.dao.DataConnector;
import org.jz.marketplace.model.Bid;
import org.jz.marketplace.model.Bid.BidBuilder;
import org.jz.marketplace.model.Project;
import org.jz.marketplace.model.Project.BillingType;
import org.jz.marketplace.model.Project.ProjectBuilder;
import org.jz.marketplace.model.User;
import org.jz.marketplace.model.User.UserBuilder;

public class BidServiceTest {

	private DataConnector mockDataConnector;
	BidService bidService;
	Bid bid;
	Bid lowestBid;
	
	@Before
	public void setUp() {
		mockDataConnector = EasyMock.createNiceMock(DataConnector.class);
		EasyMock.expect(mockDataConnector.getBidRepository()).andReturn(null);
		EasyMock.expect(mockDataConnector.getProjectRepository()).andReturn(null);
		EasyMock.expect(mockDataConnector.getUserRepository()).andReturn(null);

		User seller = new UserBuilder().isBuyer(false).isSeller(true).username("testseller").build();
		User buyer = new UserBuilder().isBuyer(true).isSeller(false).username("testbuyer").build();
		
		Project project = new ProjectBuilder().billingType(BillingType.FIXED).deadline(LocalDateTime.now().plusDays(1))
				.description("Test Project").projectDateTime(LocalDateTime.now()).seller(seller).startingAmount(100).build();

		bid = new BidBuilder().amount(50).bidDateTime(LocalDateTime.now()).buyer(buyer).project(project).build();
		lowestBid = new BidBuilder().amount(100).bidDateTime(LocalDateTime.now()).buyer(buyer).project(project).build();
		
		EasyMock.replay(mockDataConnector);
		bidService = new BidService(mockDataConnector);
	}
	
	@Test
	public void testValidateBidSuccessfully() {
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertFalse(result.containsKey(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithNullBid() {
		bid = null;
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_BID_NOT_FOUND, result.get(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithNullProject() {
		bid.setProject(null);
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_PROJECT_NOT_FOUND, result.get(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithProjectDeadlinePassed() {
		bid.getProject().setDeadline(LocalDateTime.now().minusMinutes(1));
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_PROJECT_DEADLINE_PASSED, result.get(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithNullBuyer() {
		bid.setBuyer(null);
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_BUYER_NOT_FOUND, result.get(BidService.ERROR_KEY));
	}
	
	@Test
	public void testValidateBidWithBuyerSameUserAsSeller() {
		bid.getProject().getSeller().setIsBuyer(true);
		bid.setBuyer(bid.getProject().getSeller());

		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_BUYER_SAME_AS_SELLER, result.get(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithUserIsNotBuyer() {
		bid.getBuyer().setIsBuyer(false);
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_USER_IS_NOT_BUYER, result.get(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithAmountEqualsZero() {
		bid.setAmount(0);
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_BID_AMOUNT_MUST_BE_GT_ZERO, result.get(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithAmountLessThanZero() {
		bid.setAmount(-1);
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_BID_AMOUNT_MUST_BE_GT_ZERO, result.get(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithAmountEqualToLowestBid() {
		bid.setAmount(lowestBid.getAmount());
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_BID_MUST_BE_LT_CURRENT_BID, result.get(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithAmountGreaterThanLowestBid() {
		bid.setAmount(lowestBid.getAmount()+1);
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_BID_MUST_BE_LT_CURRENT_BID, result.get(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithAmountLessThanStartingAmount() {
		lowestBid = null;
		bid.setAmount(bid.getProject().getStartingAmount()-1);
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertFalse(result.containsKey(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithAmountEqualToStartingAmount() {
		lowestBid = null;
		bid.setAmount(bid.getProject().getStartingAmount());
		
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertFalse(result.containsKey(BidService.ERROR_KEY));
	}

	@Test
	public void testValidateBidWithAmountGreaterThanStartingAmount() {
		lowestBid = null;
		bid.setAmount(bid.getProject().getStartingAmount()+1);
		Map<String,String> result = bidService.validateBid(bid, lowestBid, new HashMap<>());
		assertEquals(BidService.ERR_BID_MUST_BE_LE_STARTING_BID, result.get(BidService.ERROR_KEY));
	}
	
}

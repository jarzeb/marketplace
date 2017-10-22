package org.jz.marketplace.adminservice;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.jz.marketplace.adminservice.ArchiveService;
import org.jz.marketplace.dao.DataConnector;
import org.jz.marketplace.model.Bid;
import org.jz.marketplace.model.Project;
import org.jz.marketplace.model.User;
import org.jz.marketplace.model.Bid.BidBuilder;
import org.jz.marketplace.model.Project.BillingType;
import org.jz.marketplace.model.Project.ProjectBuilder;
import org.jz.marketplace.model.User.UserBuilder;
import org.jz.marketplace.repository.BidRepository;
import org.jz.marketplace.repository.ProjectRepository;

public class ArchiveServiceTest {

	private DataConnector mockDataConnector;
	
	private BidRepository mockBidRepo;
	private ProjectRepository mockProjectRepo;
	private List<Project> projectList;
	private List<Bid> bidList;
	
	@Before
	public void setUp() {
		mockDataConnector = EasyMock.createNiceMock(DataConnector.class);
		mockBidRepo = EasyMock.createNiceMock(BidRepository.class);
		mockProjectRepo = EasyMock.createNiceMock(ProjectRepository.class);
		EasyMock.expect(mockDataConnector.getBidRepository()).andReturn(mockBidRepo);
		EasyMock.expect(mockDataConnector.getProjectRepository()).andReturn(mockProjectRepo);
		
		projectList = new ArrayList<>();
		User testSeller = new UserBuilder().isBuyer(false).isSeller(true).username("testseller").build();
		User testBuyer = new UserBuilder().isBuyer(true).isSeller(false).username("testbuyer").build();
		
		Project p = new ProjectBuilder().billingType(BillingType.FIXED).deadline(LocalDateTime.now().minusSeconds(1))
				.description("Test Project").projectDateTime(LocalDateTime.now()).seller(testSeller).build();
		projectList.add(p);
		
		Bid b1 = new BidBuilder().amount(100).bidDateTime(LocalDateTime.now()).buyer(testBuyer).project(p).build();
		Bid b2 = new BidBuilder().amount(50).bidDateTime(LocalDateTime.now()).buyer(testBuyer).project(p).build();
		bidList = new ArrayList<>();
		bidList.add(b1);
		bidList.add(b2);

		EasyMock.expect(mockProjectRepo.findTop100ProjectsByOrderByDeadlineAsc()).andReturn(projectList);
		EasyMock.expect(mockBidRepo.findBidsByProject(p)).andReturn(bidList);

		EasyMock.replay(mockDataConnector);
		EasyMock.replay(mockBidRepo);
		EasyMock.replay(mockProjectRepo);
	}
	
	@Test
	public void testArchiveCompletedProjects() {
		
		ArchiveService archiveService = new ArchiveService(mockDataConnector);
		Map<String,String> result = archiveService.archiveCompletedProjects();
		
		assertEquals("2", result.get("bidsArchived"));
		assertEquals("1", result.get("projectsArchived"));
	}
}

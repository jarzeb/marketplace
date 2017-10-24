package org.jz.marketplace.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDateTime;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.jz.marketplace.dao.DataConnector;
import org.jz.marketplace.model.Project;
import org.jz.marketplace.model.Project.BillingType;
import org.jz.marketplace.model.Project.ProjectBuilder;
import org.jz.marketplace.model.User;
import org.jz.marketplace.model.User.UserBuilder;
import org.jz.marketplace.repository.ProjectRepository;
import org.jz.marketplace.repository.UserRepository;

public class ProjectServiceTest {

	private DataConnector mockDataConnector;
	ProjectService projectService;
	Project project;
	
	@Before
	public void setUp() {
		mockDataConnector = EasyMock.createNiceMock(DataConnector.class);
		ProjectRepository mockProjectRepository = EasyMock.createNiceMock(ProjectRepository.class);
		UserRepository mockUserRepository = EasyMock.createNiceMock(UserRepository.class);

		EasyMock.expect(mockDataConnector.getBidRepository()).andReturn(null);
		EasyMock.expect(mockDataConnector.getProjectRepository()).andReturn(mockProjectRepository);
		EasyMock.expect(mockDataConnector.getUserRepository()).andReturn(mockUserRepository);
		EasyMock.replay(mockDataConnector);
		
		User seller = new UserBuilder().userId(1).isBuyer(false).isSeller(true).username("testseller").build();
		
		project = new ProjectBuilder().billingType(BillingType.FIXED).deadline(LocalDateTime.now().plusDays(1))
				.description("Test Project").projectDateTime(LocalDateTime.now()).seller(seller).startingAmount(100).build();

		EasyMock.expect(mockProjectRepository.save(project)).andReturn(project);
		EasyMock.replay(mockProjectRepository);

		EasyMock.expect(mockUserRepository.findOne((long)1)).andReturn(seller);
		EasyMock.replay(mockUserRepository);
		
		projectService = new ProjectService(mockDataConnector);
	}
	
	@Test
	public void testCreateProjectSuccessfully() {
		Map<String,String> result = projectService.createProject(project);
		assertFalse(result.containsKey(BidService.ERROR_KEY));
	}

	@Test
	public void testCreateProjectWithInvalidSeller() {
		project.getSeller().setUserId(2);
		
		Map<String,String> result = projectService.createProject(project);
		assertEquals(ProjectService.ERR_USER_NOT_FOUND, result.get(ProjectService.ERROR_KEY));
	}

	@Test
	public void testCreateProjectWithNullSeller() {
		project.setSeller(null);
		
		Map<String,String> result = projectService.createProject(project);
		assertEquals(ProjectService.ERR_USER_NOT_FOUND, result.get(ProjectService.ERROR_KEY));
	}
	
	@Test
	public void testCreateProjectWithUserIsNotSeller() {
		project.getSeller().setIsSeller(false);
		
		Map<String,String> result = projectService.createProject(project);
		assertEquals(ProjectService.ERR_USER_NOT_SELLER, result.get(ProjectService.ERROR_KEY));		
	}
	
	@Test
	public void testCreateProjectWithStartingAmountLessThanOne() {
		project.setStartingAmount(0);

		Map<String,String> result = projectService.createProject(project);
		assertEquals(ProjectService.ERR_STARTING_AMOUNT_MUST_BE_BLANK_OR_GE_1, result.get(ProjectService.ERROR_KEY));		
	}
	
	@Test
	public void testCreateProjectWithDeadlineInThePast() {
		project.setDeadline(LocalDateTime.now().minusDays(1));

		Map<String,String> result = projectService.createProject(project);
		assertEquals(ProjectService.ERR_DEADLINE_CANNOT_BE_IN_PAST, result.get(ProjectService.ERROR_KEY));				
	}

}

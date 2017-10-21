package org.jz.marketplace.controller;

import java.util.List;
import java.util.Map;

import org.jz.marketplace.model.Project;
import org.jz.marketplace.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/createProject", method = RequestMethod.POST)
	public ResponseEntity<Map<String,String>> createProject(@RequestBody Project project) {
		Map<String,String> result = projectService.createProject(project);		
		HttpStatus httpStatus = result.containsKey("error") ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
		return new ResponseEntity<Map<String,String>>(result, httpStatus);
	}

	@RequestMapping(value = "/projectDetail", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getProjectDetail(@Param("projectId") long projectId) {
		Map<String,Object> result = projectService.getProjectDetail(projectId);
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/projectList", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String,String>>> projectList() {
		List<Map<String,String>> result = projectService.getProjectList();
		return new ResponseEntity<List<Map<String,String>>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/projectListByBuyer", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String,String>>> projectListByBuyerr(@Param("buyerId") long buyerId) {
		List<Map<String,String>> result = projectService.getProjectListByBuyer(buyerId);
		return new ResponseEntity<List<Map<String,String>>>(result, HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/projectListBySeller", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String,String>>> projectListBySeller(@Param("sellerId") long sellerId) {
		List<Map<String,String>> result = projectService.getProjectListBySeller(sellerId);
		return new ResponseEntity<List<Map<String,String>>>(result, HttpStatus.OK);
	}	

}
package org.jz.marketplace.controller;

import java.util.List;
import java.util.Map;

import org.jz.marketplace.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/projectList", method = RequestMethod.GET)
	public List<Map<String,String>> projectList() {
		List<Map<String,String>> result = projectService.getProjectList(null);
		return result;
	}

	@RequestMapping(value = "/projectListBySeller", method = RequestMethod.GET)
	public List<Map<String,String>> projectListBySeller(@Param("sellerId") long sellerId) {
		List<Map<String,String>> result = projectService.getProjectList(sellerId);
		return result;
	}	
	
	@RequestMapping(value = "/projectDetail", method = RequestMethod.GET)
	public Map<String,Object> getProjectDetail(@Param("projectId") long projectId) {
		Map<String,Object> result = projectService.getProjectDetail(projectId);
		return result;
	}

}
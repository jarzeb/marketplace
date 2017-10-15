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
	private ProjectService projectDao;

	@RequestMapping(value = "/projectList", method = RequestMethod.GET)
	public List<Map<String,String>> getProjectList() {
		List<Map<String,String>> result = projectDao.getProjectList();
		return result;
	}
	
	@RequestMapping(value = "/projectDetail", method = RequestMethod.GET)
	public Map<String,Object> getProjectDetail(@Param("projectId") long projectId) {
		Map<String,Object> result = projectDao.getProjectDetail(projectId);
		return result;
	}

}
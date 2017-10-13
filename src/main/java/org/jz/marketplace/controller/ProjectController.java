package org.jz.marketplace.controller;

import java.util.List;
import java.util.Map;

import org.jz.marketplace.dao.ProjectDAO;
import org.jz.marketplace.data.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectDAO projectDao;

	@RequestMapping("/projectList")
	public List<Map<String,String>> getProjectList() {
		List<Map<String,String>> result = projectDao.getProjectList();
		return result;
	}
	
	@RequestMapping("/projectDetail")
	public Map<String,Object> getProjectDetail(@Param("projectId") long projectId) {
		Map<String,Object> result = projectDao.getProjectDetail(projectId);
		return result;
	}

}
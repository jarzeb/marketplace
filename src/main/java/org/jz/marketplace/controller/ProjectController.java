package org.jz.marketplace.controller;

import java.util.Map;

import org.jz.marketplace.dao.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectDAO projectDao;

	@RequestMapping("/getProjectList")
	public Map<String,Object> getProjectList() {
		Map<String,Object> result = projectDao.getProjectList();
		
		return result;
	}


}
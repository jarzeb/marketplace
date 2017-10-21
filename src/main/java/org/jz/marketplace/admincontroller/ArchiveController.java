package org.jz.marketplace.admincontroller;

import java.util.Map;

import org.jz.marketplace.adminservice.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArchiveController {

	@Autowired
	private ArchiveService archiveService;
	
	@RequestMapping(value = "/archiveCompletedProjects", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String,String>> archiveCompletedProjects() {
		Map<String,String> result =  archiveService.archiveCompletedProjects();
		return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
	}
		
}
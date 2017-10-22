package org.jz.marketplace.admincontroller;

import java.util.Map;

import org.jz.marketplace.adminservice.SampleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleDataController {

	@Autowired
	private SampleDataService sampleDataService;

	@RequestMapping(value = "/loadSampleData", method = RequestMethod.POST)
	public ResponseEntity<Map<String,String>> loadSampleData() {
		Map<String,String> result = sampleDataService.loadSampleData();
		HttpStatus httpStatus = result.containsKey("error") ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
		return new ResponseEntity<Map<String,String>>(result, httpStatus);
	}
	
}

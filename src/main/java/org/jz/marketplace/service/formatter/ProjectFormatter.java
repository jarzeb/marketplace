package org.jz.marketplace.service.formatter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.jz.marketplace.data.Bid;
import org.jz.marketplace.data.Project;

public class ProjectFormatter {
	
	// Return Project data in a client-friendly format 
	public static Map<String,String> format(Project project, LocalDateTime currentDateTime) {
		Map<String,String> row = new HashMap<>();
		
		row.put("projectId", String.valueOf(project.getProjectId()));
		row.put("sellerName", project.getSeller().getUsername());
		row.put("description", project.getDescription());
		row.put("deadline", project.getDeadline().toString());
		row.put("billingType", project.getBillingType().name());
		row.put("startingAmount", project.getStartingAmount()==null ? "" : "$" + String.valueOf(project.getStartingAmount()));
		
		Bid lowestBid = project.getLowestBid();
		if(lowestBid != null) {
			row.put("lowestBid", "$" + String.valueOf(lowestBid.getAmount()));
			row.put("lowestBidUsername", lowestBid.getBuyer().getUsername());
		}
		
		boolean isActive = isProjectActive(project, currentDateTime);
		String status = "";
		if(isActive) {
			status = "Active";
		} else if(lowestBid == null) {
			status = "Ended";
		} else {
			status = "Won";
		}
		row.put("status", status);
		
		return row;
	}
	
	private static boolean isProjectActive(Project project, LocalDateTime currentDateTime) {
		return !currentDateTime.isAfter(project.getDeadline());
	}

}
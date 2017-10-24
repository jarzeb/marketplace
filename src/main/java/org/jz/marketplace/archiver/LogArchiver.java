package org.jz.marketplace.archiver;

import org.apache.log4j.Logger;
import org.jz.marketplace.model.Bid;
import org.jz.marketplace.model.Project;

public class LogArchiver implements Archiver {

	Logger log = Logger.getLogger(LogArchiver.class.getName());

	public void archive(Project project) {
		log.info(project.toString());
	}
	
	public void archive(Bid bid) {
		log.info(bid.toString());
	}
		
}

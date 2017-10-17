package org.jz.marketplace.archiver;

import org.apache.log4j.Logger;

public class LogArchiver implements Archiver {

	Logger log = Logger.getLogger(LogArchiver.class.getName());

	public void archive(String data) {
		log.info(data);
	}
		
}

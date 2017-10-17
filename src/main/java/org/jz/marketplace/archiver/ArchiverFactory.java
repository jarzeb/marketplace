package org.jz.marketplace.archiver;

public class ArchiverFactory {

	public Archiver getArchiver() {
		return new LogArchiver();
	}
}

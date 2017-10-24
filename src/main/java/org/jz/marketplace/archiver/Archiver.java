package org.jz.marketplace.archiver;

import org.jz.marketplace.model.Bid;
import org.jz.marketplace.model.Project;

public interface Archiver {

	public void archive(Project project);
	public void archive(Bid bid);
	
}

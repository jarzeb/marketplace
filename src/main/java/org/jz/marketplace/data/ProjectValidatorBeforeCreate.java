package org.jz.marketplace.data;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("beforeCreateProjectValidator")
public class ProjectValidatorBeforeCreate extends ProjectValidatorCommon {
	@Override
	public void validate(Object obj, Errors err) {
		Project project = (Project) obj;

	}	

}

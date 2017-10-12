package org.jz.marketplace.validator;

import org.jz.marketplace.data.Project;
import org.springframework.validation.Validator;

public abstract class ProjectValidatorCommon implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.equals(clazz);
	}
}

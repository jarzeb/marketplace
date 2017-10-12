package org.jz.marketplace.data;

import org.springframework.validation.Validator;

public abstract class BidValidatorCommon implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Bid.class.equals(clazz);
	}
}

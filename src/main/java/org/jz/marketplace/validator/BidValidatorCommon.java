package org.jz.marketplace.validator;

import org.jz.marketplace.data.Bid;
import org.springframework.validation.Validator;

public abstract class BidValidatorCommon implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Bid.class.equals(clazz);
	}
}

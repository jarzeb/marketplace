package org.jz.marketplace.data;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("beforeSaveBidValidator")
public class BidValidatorBeforeSave extends BidValidatorCommon {
	
	@Override
	public void validate(Object obj, Errors err) {		
		err.reject("cannot update an existing bid");
	}

}

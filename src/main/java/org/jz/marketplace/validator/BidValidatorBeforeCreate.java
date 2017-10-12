package org.jz.marketplace.validator;

import org.jz.marketplace.data.Bid;
import org.jz.marketplace.data.Project;
import org.jz.marketplace.data.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("beforeCreateBidValidator")
public class BidValidatorBeforeCreate extends BidValidatorCommon {
	@Override
	public void validate(Object obj, Errors err) {
		Bid bid = (Bid) obj;
		Project project = bid.getProject();
		User buyer = bid.getBuyer();

		if(bid.getBidDateTime().isAfter(project.getDeadline())) {
			err.rejectValue("bidDateTime", "project deadline has passed");
		} else if(buyer.equals(project.getSeller())) {
			err.rejectValue("buyer", "buyer cannot be same user as seller");
		} else if(!buyer.isBuyer()) {
			err.rejectValue("buyer", "user does not have buyer status");
		}
	}	

}

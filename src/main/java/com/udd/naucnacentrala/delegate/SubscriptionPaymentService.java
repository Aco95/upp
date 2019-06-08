package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class SubscriptionPaymentService implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Starting task SubscriptionPayment...");
        System.out.println("Calling the payment concentrator service...");
        System.out.println("Payment succesfull...");
        System.out.println("Ending task SubscriptionPayment...");
		
	}

}

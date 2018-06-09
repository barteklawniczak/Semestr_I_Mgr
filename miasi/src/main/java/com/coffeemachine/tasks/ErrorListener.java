package com.coffeemachine.tasks;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.coffeemachine.CoffeeController;

public class ErrorListener implements ExecutionListener {

	private static final long serialVersionUID = 3000520157219812568L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		CoffeeController.cause = execution.getVariable("cause").toString();
	}

}

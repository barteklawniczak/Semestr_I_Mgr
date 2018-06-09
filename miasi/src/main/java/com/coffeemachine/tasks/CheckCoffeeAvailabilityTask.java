package com.coffeemachine.tasks;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CheckCoffeeAvailabilityTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
				        
        if(Double.parseDouble(String.valueOf(execution.getVariable("coffee"))) < Double.parseDouble(String.valueOf(execution.getVariable("coffeeCoffee")))) {
        	execution.setVariable("coffeeAvailable", false);
        	execution.setVariable("cause", "kawa");
        } else if (Double.parseDouble(String.valueOf(execution.getVariable("water"))) < Double.parseDouble(String.valueOf(execution.getVariable("coffeeWater")))) {
        	execution.setVariable("coffeeAvailable", false);
        	execution.setVariable("cause", "woda");
        } else if (Double.parseDouble(String.valueOf(execution.getVariable("milk"))) < Double.parseDouble(String.valueOf(execution.getVariable("coffeeMilk")))) {
        	execution.setVariable("coffeeAvailable", false);
        	execution.setVariable("cause", "mleko");
        } else if (Double.parseDouble(String.valueOf(execution.getVariable("cups"))) <	1) {
        	execution.setVariable("coffeeAvailable", false);
        	execution.setVariable("cause", "kubki");
        } else {
        	execution.setVariable("coffeeAvailable", true);
        }
	}

}

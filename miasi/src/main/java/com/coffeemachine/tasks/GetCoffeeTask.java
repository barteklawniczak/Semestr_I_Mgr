package com.coffeemachine.tasks;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.coffeemachine.CoffeeController;

public class GetCoffeeTask implements TaskListener {

	private static final long serialVersionUID = -1986805147969531968L;

	@Override
	public void notify(DelegateTask task) {
		
		CoffeeController.taskId = task.getId();
		CoffeeController.executionId = task.getExecutionId();

	}
	
}

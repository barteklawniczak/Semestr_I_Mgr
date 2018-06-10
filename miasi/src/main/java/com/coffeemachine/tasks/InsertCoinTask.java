package com.coffeemachine.tasks;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.coffeemachine.CoffeeController;

public class InsertCoinTask implements TaskListener {

	private static final long serialVersionUID = -7907468854570732623L;

	@Override
	public void notify(DelegateTask task) {
		
		CoffeeController.taskId = task.getId();
		CoffeeController.executionId = task.getExecutionId();
		
	}

}

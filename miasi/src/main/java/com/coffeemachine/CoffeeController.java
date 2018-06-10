package com.coffeemachine;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.BpmnError;
import com.coffeemachine.models.Automat;
import com.coffeemachine.models.Coffee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CoffeeController implements Initializable {

	@FXML
	private Button coffee;
	@FXML
	private Button coffeeWithMilk;
	@FXML
	private Button cappuccino;
	@FXML
	private Button caffeLatte;
	@FXML
	private Button americano;
	@FXML
	private Button espresso;
	@FXML
	private Button oneZloty;
	@FXML
	private Button twoZlotych;
	@FXML
	private Button fiveZlotych;
	@FXML
	private Button tenGroszy;
	@FXML
	private Button twentyGroszy;
	@FXML
	private Button fiftyGroszy;
	@FXML
	private Button cancel;
	@FXML
	private Button takeCoffee;
	@FXML
	private Button restart;
	@FXML
	public TextField alert;
	@FXML
	private TextField currentAmount;
	@FXML
	private TextField neededAmount;

	private Automat automat;
	private RuntimeService runtimeService;
	private ProcessEngine processEngine;
	private RepositoryService repositoryService;
	private TaskService taskService;
	public static String taskId;
	public static String executionId;
	public static String cause;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		this.automat = new Automat(3, 1, 1, 3);
		this.currentAmount.setText("0");
		disableMoneyButtons();
		this.alert.setText("Wybierz kawę");

	}

	public void coffee() {
		this.startProcess(new Coffee("Kawa", 2.0, 0.4, 0, 0.25));
	}

	public void coffeeWithMilk() {
		this.startProcess(new Coffee("Kawa z mlekiem", 2.5, 0.3, 0.1, 0.2));
	}

	public void cappuccino() {
		this.startProcess(new Coffee("Cappuccino", 2.5, 0.3, 0.1, 0.1));
	}

	public void caffeLatte() {
		this.startProcess(new Coffee("Caffe Latte", 2.8, 0.2, 0.2, 0.1));
	}

	public void americano() {
		this.startProcess(new Coffee("Americano", 2.3, 0.5, 0, 0.2));
	}

	public void espresso() {
		this.startProcess(new Coffee("Espresso", 1.6, 0.2, 0, 0.25));
	}

	public void startProcess(Coffee coffee) {
		this.cancel.setVisible(true);
		this.automat.setCoffeeSelected(coffee);
		this.neededAmount.setText(String.valueOf(coffee.getPrice()));
		disableCoffeeButtons();
		ProcessEngineConfiguration processEngineConfig = ProcessEngineConfiguration
				.createStandaloneInMemProcessEngineConfiguration()
				.setJdbcUrl("jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000").setJdbcDriver("org.h2.Driver")
				.setJdbcUsername("sa").setJdbcPassword("").setDatabaseSchemaUpdate("create-drop");
		this.processEngine = processEngineConfig.buildProcessEngine();
		this.repositoryService = processEngine.getRepositoryService();
		this.repositoryService.createDeployment().addClasspathResource("diagrams/CoffeeMachine.bpmn").deploy();
		this.runtimeService = processEngine.getRuntimeService();
		Map<String, Object> variables = new HashMap<String, Object>();
		this.automat.addToHashMap(variables);
		variables.put("currentAmount", 0.0);
		variables.put("cancelled", false);
		try {
			runtimeService.startProcessInstanceByKey("myProcess", variables);
			taskService = processEngine.getTaskService();
			insertCoins();
		} catch (BpmnError e) {
			if (e.getErrorCode().equals("coffeeUnavailable")) {
				this.alert.setText("W automacie brakuje składników! Powód: " + CoffeeController.cause + "!");
			} else {
				this.alert.setText("Niespodziewany błąd!");
			}
			this.cancel.setVisible(false);
			this.restart.setVisible(true);
			processEngine.close();
			enableCoffeeButtons();
		}
	}

	public void insertCoins() {
		this.enableMoneyButtons();
		this.disableCoffeeButtons();
		this.alert.setText("Wrzuć monety");
	}

	public void disableCoffeeButtons() {
		this.coffee.setDisable(true);
		this.coffeeWithMilk.setDisable(true);
		this.cappuccino.setDisable(true);
		this.americano.setDisable(true);
		this.caffeLatte.setDisable(true);
		this.espresso.setDisable(true);
	}

	public void enableCoffeeButtons() {
		this.coffee.setDisable(false);
		this.coffeeWithMilk.setDisable(false);
		this.cappuccino.setDisable(false);
		this.americano.setDisable(false);
		this.caffeLatte.setDisable(false);
		this.espresso.setDisable(false);
		this.neededAmount.clear();
	}

	public void disableMoneyButtons() {
		this.oneZloty.setDisable(true);
		this.twoZlotych.setDisable(true);
		this.fiveZlotych.setDisable(true);
		this.tenGroszy.setDisable(true);
		this.twentyGroszy.setDisable(true);
		this.fiftyGroszy.setDisable(true);
	}

	public void enableMoneyButtons() {
		this.oneZloty.setDisable(false);
		this.twoZlotych.setDisable(false);
		this.fiveZlotych.setDisable(false);
		this.tenGroszy.setDisable(false);
		this.twentyGroszy.setDisable(false);
		this.fiftyGroszy.setDisable(false);
	}

	public void oneZloty() {
		updateAmount(1);
	}

	public void twoZlotych() {
		updateAmount(2);
	}

	public void fiveZlotych() {
		updateAmount(5);
	}

	public void tenGroszy() {
		updateAmount(0.1);
	}

	public void twentyGroszy() {
		updateAmount(0.2);
	}

	public void fiftyGroszy() {
		updateAmount(0.5);
	}

	public void cancel() {
		taskService.setVariable(taskId, "cancelled", true);
		double currentAmount = Double.parseDouble(taskService.getVariable(taskId, "currentAmount").toString());
		if (currentAmount == 0.0)
			this.alert.setText("Brak reszty");
		else {
			int difference = (int) ((currentAmount) * 10);
			this.alert.setText("Zakup kawy anulowany! Odbierz resztę: " + ((double) difference) / 10 + " zł");
		}
		disableMoneyButtons();
		this.cancel.setVisible(false);
		this.restart.setVisible(true);
		taskService.complete(taskId);
	}

	public void takeCoffee() {
		double currentAmount = Double.parseDouble(taskService.getVariable(taskId, "currentAmount").toString());
		double coffeePrice = Double.parseDouble(taskService.getVariable(taskId, "coffeePrice").toString());
		if (currentAmount == coffeePrice)
			this.alert.setText("Brak reszty");
		else {
			int difference = (int) ((currentAmount - coffeePrice) * 10);
			this.alert.setText("Odbierz resztę : " + ((double) difference) / 10 + " zł");
		}
		this.takeCoffee.setVisible(false);
		this.restart.setVisible(true);
		this.automat.decreaseResources();
		String message = "I've just made a coffee - " + taskService.getVariable(taskId, "coffeeName").toString() + "!";
		try {
			Automat.Send("migawkaapp", "migawkaapp12#$", "migawkaapp@gmail.com", "", "Coffee made!", message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		taskService.complete(taskId);
	}

	public void updateAmount(double current) {
		taskService.setVariable(taskId, "currentAmount",
				Double.parseDouble(taskService.getVariable(taskId, "currentAmount").toString()) + current);
		double currentAmount = Double.parseDouble(taskService.getVariable(taskId, "currentAmount").toString());
		int currentAmountDecimal = (int) (currentAmount * 10);
		this.currentAmount.setText(String.valueOf(((double) currentAmountDecimal) / 10));
		if (Double.parseDouble(taskService.getVariable(taskId, "currentAmount").toString()) >= Double
				.parseDouble(taskService.getVariable(taskId, "coffeePrice").toString())) {
			disableMoneyButtons();
			this.cancel.setVisible(false);
			this.takeCoffee.setVisible(true);
			this.alert.setText("Odbierz kawę (" + taskService.getVariable(taskId, "coffeeName").toString() + ")");
		}
		taskService.complete(taskId);
	}

	public void restart() {
		processEngine.close();
		this.restart.setVisible(false);
		this.enableCoffeeButtons();
		this.alert.setText("Wybierz kawę");
		this.currentAmount.setText("0");
		this.neededAmount.clear();
	}

}

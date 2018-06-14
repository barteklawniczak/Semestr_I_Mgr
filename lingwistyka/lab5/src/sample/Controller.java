package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField currentExpression;
    @FXML
    private TextField result;

    private static final String myRegex = "(\\((\\-)?(0|[1-9](\\d)*)(\\.(\\d)+)?((\\+|\\-|\\/|\\*|\\^)(0|[1-9](\\d)*)(\\.(\\d)+)?)*\\)|(\\-)?(0|[1-9](\\d)*)(\\.(\\d)+)?((\\+|\\-|\\/|\\*|\\^)(0|[1-9](\\d)*)(\\.(\\d)+)?)*)((\\+|\\-|\\/|\\*|\\^)(\\((\\-)?(0|[1-9](\\d)*)(\\.(\\d)+)?((\\+|\\-|\\/|\\*|\\^)(0|[1-9](\\d)*)(\\.(\\d)+)?)*\\)|(\\-)?(0|[1-9](\\d)*)(\\.(\\d)+)?((\\+|\\-|\\/|\\*|\\^)(0|[1-9](\\d)*)(\\.(\\d)+)?)*))*";

    public void checkExpression() {

        if(!currentExpression.getText().isEmpty()) {
            if(currentExpression.getText().matches(myRegex))
                this.result.setText("OK");
            else
                this.result.setText("WRONG");
        }

    }

}

package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {

    @FXML
    private TextField currentExpression;
    @FXML
    private TextField result;

    private int position;
    private String expression;

    public void checkExpression() throws IOException {

        this.position=0;
        this.expression = this.currentExpression.getText();
        try {
            parseS();
            this.result.setText("OK");
        } catch (Exception e) {
            this.result.setText(e.getMessage());
        }

    }

    public void parseS() throws Exception {
        try {
            parseW();
            parseSemicolon();
            parseZ();
        } catch(Exception e) {
            throw e;
        }
    }

    public void parseW() throws Exception {

        try {
            parseP();
            parseWprim();
        } catch(Exception e) {
            throw e;
        }

    }

    public void parseWprim() throws Exception {

        if((this.expression.charAt(this.position)+"").matches("[\\\\+\\-*:^]")) {
            try {
                parseO();
                parseW();
            } catch(Exception e) {
                throw e;
            }
        }

    }

    public void parseSemicolon() throws Exception {

        if(this.expression.charAt(this.position) == ';') {
            increasePosition();
        } else {
            throw new Exception("Expected semicolon but got " + this.expression.charAt(this.position) + " (position " + this.position + ")");
        }

    }

    public void parseZ() throws Exception {

        if(this.position+1 != this.expression.length()) {
            if ((this.expression.charAt(this.position+1) + "").matches("\\d|\\(")) {
                increasePosition();
                try {
                    parseS();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }

    public void parseP() throws Exception {

        if(this.expression.charAt(this.position) == '(') {
            increasePosition();
            try {
                parseW();
                parseClosingBracket();
            } catch (Exception e) {
                throw e;
            }
        } else {
            parseR();
        }
    }

    public void parseR() throws Exception {

        try {
            parseL();
            parseRprim();
        } catch (Exception e) {
            throw e;
        }
    }

    public void parseRprim() throws Exception {

        if (this.expression.charAt(this.position) == '.') {
            increasePosition();
            try {
                parseL();
            } catch (Exception e) {
                throw e;
            }
        }

    }
    public void parseL() throws Exception {

        try {
            parseC();
            parseLprim();
        } catch (Exception e) {
            throw e;
        }

    }

    public void parseLprim() throws Exception {

        if((this.expression.charAt(this.position)+"").matches("\\d")) {
            try {
                parseL();
            } catch (Exception e) {
                throw e;
            }
        }

    }

    public void parseC() throws Exception {

        if((this.expression.charAt(this.position)+"").matches("\\d")) {
            increasePosition();
        } else {
            throw new Exception("Expected digit but got " + this.expression.charAt(this.position) + " (position " + this.position + ")");
        }

    }

    public void parseO() throws Exception {

        if((this.expression.charAt(this.position)+"").matches("[\\\\+\\-*:^]")) {
            increasePosition();
        } else {
            throw new Exception("Expected operator but got " + this.expression.charAt(this.position) + " (position " + this.position + ")");
        }

    }

    public void parseClosingBracket() throws Exception {

        if(this.expression.charAt(this.position) == ')') {
            increasePosition();
        } else {
            throw new Exception("Expected closing bracket but got " + this.expression.charAt(this.position) + " (position " + this.position + ")");
        }

    }

    public void increasePosition() throws Exception {
        this.position++;
        if(this.position == this.expression.length()) {
            throw new Exception("Unexpected end of expression (position " + this.position + ")");
        }
    }

}

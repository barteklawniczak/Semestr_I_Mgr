package sample.models;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class TableViewObject {

    private SimpleStringProperty stateSymbol = new SimpleStringProperty();
    private SimpleStringProperty currentChar = new SimpleStringProperty();
    private SimpleStringProperty outSymbol = new SimpleStringProperty();

    public String getStateSymbol() {
        return stateSymbol.get();
    }

    public SimpleStringProperty stateSymbolProperty() {
        return stateSymbol;
    }

    public void setStateSymbol(String stateSymbol) {
        this.stateSymbol.set(stateSymbol);
    }

    public String getCurrentChar() { return currentChar.get(); }

    public SimpleStringProperty currentCharProperty() { return currentChar; }

    public void setCurrentChar(String currentChar) { this.currentChar.set(currentChar); }

    public String getOutSymbol() {
        return outSymbol.get();
    }

    public SimpleStringProperty outSymbolProperty() {
        return outSymbol;
    }

    public void setOutSymbol(String outSymbol) {
        this.outSymbol.set(outSymbol);
    }

    public TableViewObject(Integer stateId, String currentChar, State outState) {
        this.stateSymbol.set("Q" + stateId);
        this.currentChar.set(currentChar);
        this.outSymbol.set("{" + outState.getCharToWrite() + ", Q" + outState.getId() + ", " + outState.getDecision() + "}");
    }
}

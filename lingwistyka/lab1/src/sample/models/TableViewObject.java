package sample.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableViewObject {

    private SimpleStringProperty stateSymbol = new SimpleStringProperty();
    private SimpleIntegerProperty amount = new SimpleIntegerProperty();
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

    public int getAmount() {
        return amount.get();
    }

    public SimpleIntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public String getOutSymbol() {
        return outSymbol.get();
    }

    public SimpleStringProperty outSymbolProperty() {
        return outSymbol;
    }

    public void setOutSymbol(String outSymbol) {
        this.outSymbol.set(outSymbol);
    }

    public TableViewObject(Integer stateId, int amount, Integer outStateId) {
        this.stateSymbol.set("Q" + stateId);
        this.amount.set(amount);
        this.outSymbol.set("Q" + outStateId);
    }
}

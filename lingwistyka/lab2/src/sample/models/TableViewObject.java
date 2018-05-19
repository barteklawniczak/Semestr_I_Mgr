package sample.models;

import javafx.beans.property.SimpleIntegerProperty;
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

    public TableViewObject(List<State> statesIn, char currentChar, List<State> outStates) {
        String out = "{";
        for(int i=0; i<statesIn.size(); i++) {
            out = out + "Q" + statesIn.get(i).getId();
            if(i!=statesIn.size()-1)
                out = out + ",";
        }
        this.stateSymbol.set(out+"}");

        this.currentChar.set("" + currentChar);

        out = "{";
        for(int i=0; i<outStates.size(); i++) {
            out = out + "Q" + outStates.get(i).getId();
            if(i!=outStates.size()-1)
                out = out + ",";
        }
        this.outSymbol.set(out+"}");
    }
}

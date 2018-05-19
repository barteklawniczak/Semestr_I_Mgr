package sample.models;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private int[][] automatTable = {{1,2,5,0,0}, {2,3,6,1,1}, {3,4,11,2,2}, {4,5,12,3,3}, {5,6,13,4,4}, {6,11,14,7,9}, {11,12,15,8,10}, {6,11,14,7,7},{11,12,15,8,8}};
    private List<State> stateList;
    private int amount;
    private int currentState;

    public int[][] getAutomatTable() {
        return automatTable;
    }

    public void setAutomatTable(int[][] automatTable) {
        this.automatTable = automatTable;
    }

    public List<State> getStateList() {
        return stateList;
    }

    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }


    public State getCurrentStateObject() {
        return this.stateList.get(this.getCurrentState());
    }

    public State getNextState(int thrown) {
        if(thrown == 5)
            thrown = 3;
        else if(thrown == 3)
            thrown = 4;
        else if(thrown == 6 || thrown == 7)
            thrown = 5;
        return this.stateList.get(this.automatTable[this.getCurrentState()][thrown-1]);
    }

    public Table() {
        this.amount = 0;
        this.currentState = 0;
        this.stateList = new ArrayList<State>();
        this.stateList.add(new State(0));
        this.stateList.add(new State(1));
        this.stateList.add(new State(2));
        this.stateList.add(new State(3));
        this.stateList.add(new State(4));
        this.stateList.add(new State(5,"Chcesz herbatę czy dorzuczasz monety?", 0));
        this.stateList.add(new State(6, "Chcesz herbatę czy dorzuczasz monety?", 1));
        this.stateList.add(new State(7));
        this.stateList.add(new State(8));
        this.stateList.add(new State(9,"Otrzymujesz herbatę", 0));
        this.stateList.add(new State(10,"Otrzymujesz herbatę", 1));
        this.stateList.add(new State(11, "Otrzymujesz kawę", 0));
        this.stateList.add(new State(12, "Otrzymujesz kawę", 1));
        this.stateList.add(new State(13, "Otrzymujesz kawę", 2));
        this.stateList.add(new State(14, "Otrzymujesz kawę", 3));
        this.stateList.add(new State(15, "Otrzymujesz kawę", 4));
    }
}

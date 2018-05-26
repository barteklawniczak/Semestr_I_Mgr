package sample.models;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private State[][] automatTable = new State[5][3];
    private int currentState;

    public State[][] getAutomatTable() { return automatTable; }

    public void setAutomatTable(State[][] automatTable) { this.automatTable = automatTable; }

    public int getCurrentState() { return currentState; }

    public void setCurrentState(int currentState) { this.currentState = currentState; }

    public State getNextState(char currentChar) {
        int currentLoaded;
        if(currentChar == '#')
            currentLoaded = 2;
        else
            currentLoaded = Character.getNumericValue(currentChar);

        return this.automatTable[this.currentState][currentLoaded];
    }

    public Table() {
        this.automatTable[0][0] = new State(1, "L",'1');
        this.automatTable[0][1] = new State(2, "L",'0');
        this.automatTable[0][2] = new State(1, "L",'1');
        this.automatTable[1][0] = new State(3, "L",'1');
        this.automatTable[1][1] = new State(4, "L",'0');
        this.automatTable[1][2] = new State(3, "-",'1');
        this.automatTable[2][0] = new State(4, "L",'0');
        this.automatTable[2][1] = new State(4, "L",'1');
        this.automatTable[2][2] = new State(4, "L",'0');
        this.automatTable[3][0] = new State(3, "L",'0');
        this.automatTable[3][1] = new State(3, "L",'1');
        this.automatTable[3][2] = new State(3, "-",'-');
        this.automatTable[4][0] = new State(3, "L",'1');
        this.automatTable[4][1] = new State(4, "L",'0');
        this.automatTable[4][2] = new State(3, "-",'1');
        this.currentState = 0;
    }
}

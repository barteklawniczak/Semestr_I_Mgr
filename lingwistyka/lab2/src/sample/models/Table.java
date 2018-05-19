package sample.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Table {

    private ArrayList[][] automatTable = new ArrayList[13][10];
    private List<State> stateList;
    private List<State> currentStates;

    public List[][] getAutomatTable() { return automatTable; }

    public void setAutomatTable(ArrayList[][] automatTable) { this.automatTable = automatTable; }

    public List<State> getStateList() {
        return stateList;
    }

    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }

    public List<State> getCurrentStates() { return currentStates; }

    public void setCurrentStates(List<State> currentStates) { this.currentStates = currentStates; }

    public ArrayList<State> getNextStates(char currentChar) {

        ArrayList<State> nextStates = new ArrayList<>();

        if (currentChar == 'a')
            currentChar = '5';
        else if (currentChar == 'b')
            currentChar = '6';
        else if (currentChar == 'c')
            currentChar = '7';
        else if (currentChar == 'e')
            currentChar = '8';
        else if (currentChar == 'f')
            currentChar = '9';

        for(State state : this.currentStates) {
            if(this.automatTable[state.getId()][currentChar - '0']!= null)
                nextStates.addAll(this.automatTable[state.getId()][currentChar - '0']);
        }
        return nextStates;
    }

    public Table() {
        this.stateList = new ArrayList<State>();
        this.stateList.add(new State(0, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(1, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(2, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(3, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(4, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(5, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(6, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(7, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(8, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(9, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(10, "W badanym ciągu nie występuje powtórzenie"));
        this.stateList.add(new State(11, "W badanym ciągu występuje powtórzenie cyfer!"));
        this.stateList.add(new State(12, "W badanym ciągu występuje powtórzenie liter!"));
        this.currentStates = new ArrayList<State>() {{add(stateList.get(0));}};
        this.automatTable[0][0] = new ArrayList<State>() {{add(stateList.get(0)); add((stateList.get(1)));}};
        this.automatTable[0][1] = new ArrayList<State>() {{add(stateList.get(0)); add((stateList.get(2)));}};
        this.automatTable[0][2] = new ArrayList<State>() {{add(stateList.get(0)); add((stateList.get(3)));}};
        this.automatTable[0][3] = new ArrayList<State>() {{add(stateList.get(0)); add((stateList.get(4)));}};
        this.automatTable[0][4] = new ArrayList<State>() {{add(stateList.get(0)); add((stateList.get(5)));}};
        this.automatTable[0][5] = new ArrayList<State>() {{add(stateList.get(0)); add((stateList.get(6)));}};
        this.automatTable[0][6] = new ArrayList<State>() {{add(stateList.get(0)); add((stateList.get(7)));}};
        this.automatTable[0][7] = new ArrayList<State>() {{add(stateList.get(0)); add((stateList.get(8)));}};
        this.automatTable[0][8] = new ArrayList<State>() {{add(stateList.get(0)); add((stateList.get(9)));}};
        this.automatTable[0][9] = new ArrayList<State>() {{add(stateList.get(0)); add((stateList.get(10)));}};
        this.automatTable[1][0] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[2][1] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[3][2] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[4][3] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[5][4] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[6][5] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[7][6] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[8][7] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[9][8] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[10][9] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[11][0] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[11][1] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[11][2] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[11][3] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[11][4] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[11][5] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[11][6] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[11][7] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[11][8] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[11][9] = new ArrayList<State>() {{add(stateList.get(11));}};
        this.automatTable[12][0] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[12][1] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[12][2] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[12][3] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[12][4] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[12][5] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[12][6] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[12][7] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[12][8] = new ArrayList<State>() {{add(stateList.get(12));}};
        this.automatTable[12][9] = new ArrayList<State>() {{add(stateList.get(12));}};
    }
}

package sample;

import java.util.ArrayList;
import java.util.List;

public class State {

    private String name;
    private String regex;
    private List<State> possibleStates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public List<State> getPossibleStates() {
        return possibleStates;
    }

    public void setPossibleStates(List<State> possibleStates) {
        this.possibleStates = possibleStates;
    }

    public State(String name, String regex) {
        this.name=name;
        this.regex=regex;
        this.possibleStates=new ArrayList<>();
    }

}

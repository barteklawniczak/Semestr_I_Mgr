package sample.models;

public class State {

    private int id;
    private String decision;
    private char charToWrite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public char getCharToWrite() {
        return charToWrite;
    }

    public void setCharToWrite(char charToWrite) {
        this.charToWrite = charToWrite;
    }

    public State(int id, String decision, char charToWrite) {
        this.id = id;
        this.decision = decision;
        this.charToWrite = charToWrite;
    }
}

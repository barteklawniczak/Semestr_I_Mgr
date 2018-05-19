package sample.models;

public class State {

    private int id;
    private String message;
    private int rest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public State(int id, String message, int rest) {
        this.id = id;
        this.message = message;
        this.rest = rest;
    }

    public State(int id) {
        this.id = id;
    }
}

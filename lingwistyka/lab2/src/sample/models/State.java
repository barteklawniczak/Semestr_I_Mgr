package sample.models;

public class State {

    private int id;
    private String message;

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

    public State(int id, String message) {
        this.id = id;
        this.message = message;
    }
}

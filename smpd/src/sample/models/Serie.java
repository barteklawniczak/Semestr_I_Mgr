package sample.models;

public class Serie {

    private String name;
    private float[] values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    public Serie(String name) {
        this.name = name;
    }
}

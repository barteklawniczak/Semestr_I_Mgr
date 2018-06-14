package sample.models;

public class ResultDistance {

    private String name;
    private double distance;

    public ResultDistance(String name, double distance) {
        this.name = name;
        this.distance=distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}

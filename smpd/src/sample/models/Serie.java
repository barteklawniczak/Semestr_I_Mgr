package sample.models;

public class Serie {

    private float[] values;
    private float average;
    private double standardDeviation;

    public float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public Serie() {
        this.average = 0;
        this.standardDeviation = 0;
    }
}

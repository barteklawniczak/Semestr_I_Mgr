package sample.models;

import java.util.Comparator;

public class ResultComparator implements Comparator<ResultDistance> {
    @Override
    public int compare(ResultDistance resultDistance1, ResultDistance resultDistance2) {
        return Double.compare(resultDistance1.getDistance(), resultDistance2.getDistance());
    }
}

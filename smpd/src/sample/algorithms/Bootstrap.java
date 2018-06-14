package sample.algorithms;

import sample.models.Serie;

import java.util.*;

public class Bootstrap {

    private List<Serie> testSamples;
    private List<Serie> trainSamples;

    public Bootstrap(List<Serie> containerSeries, int n) {

        this.testSamples = new ArrayList<>(containerSeries);
        this.trainSamples = new ArrayList<>();
        int amount = testSamples.size()/n;

        for(int i=0; i<n; i++) {
            int[] seriesIds = new Random().ints(0, testSamples.size()).distinct().limit(amount).toArray();
            for(int j=0; j<seriesIds.length; j++) {
                this.trainSamples.add(testSamples.get(seriesIds[j]));
            }
        }
        testSamples.removeAll(this.trainSamples);
    }

    public List<Double> executeBootstrap(int kRepeats, int n) {

        List<Double> results = Arrays.asList(new Double[4]);
        Collections.fill(results, 0.0);

        NearestNeighbourAndMean nearestNeighbourAndMean = new NearestNeighbourAndMean(testSamples, trainSamples);
        results.set(0, results.get(0)+nearestNeighbourAndMean.nearestNeighbour(kRepeats));
        results.set(1, results.get(1)+nearestNeighbourAndMean.nearestNeighbour(1));
        results.set(2, results.get(2)+nearestNeighbourAndMean.nearestMean());
        results.set(3, results.get(3)+nearestNeighbourAndMean.kMeans());

        return results;
    }
}

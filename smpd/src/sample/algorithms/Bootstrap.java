package sample.algorithms;

import sample.models.Serie;

import java.util.*;

public class Bootstrap {

    private List<Serie> testSamples;
    private List<Serie> trainSamples;

    public Bootstrap() {
    }

    public List<Double> executeBootstrap(List<Serie> series, int kRepeats, int n, int trainingPartPercent) {

        List<Double> results = Arrays.asList(new Double[4]);
        Collections.fill(results, 0.0);

        for(int p=0; p<n; p++) {

            int[] trainSamplesArray = new Random().ints(0, series.size()).distinct().limit(series.size()*trainingPartPercent/100).toArray();
            this.testSamples = new ArrayList<>(series);
            this.trainSamples = new ArrayList<>();
            for(int i=0; i<trainSamplesArray.length; i++) {
                this.trainSamples.add(testSamples.get(trainSamplesArray[i]));
            }
            testSamples.removeAll(this.trainSamples);

            NearestNeighbourAndMean nearestNeighbourAndMean = new NearestNeighbourAndMean(testSamples, trainSamples);
            results.set(0, results.get(0) + nearestNeighbourAndMean.nearestNeighbour(kRepeats));
            results.set(1, results.get(1) + nearestNeighbourAndMean.nearestNeighbour(1));
            results.set(2, results.get(2) + nearestNeighbourAndMean.nearestMean());
            results.set(3, results.get(3) + nearestNeighbourAndMean.kMeans(kRepeats));
        }

        for(int i=0; i<results.size(); i++){
            results.set(i, results.get(i)/n);
        }

        return results;
    }
}

package sample.algorithms;

import sample.models.Serie;

import java.util.*;

public class CrossValidation {

    private List<Serie> testSamples;
    private List<Serie> trainSamples;
    private List<List<Serie>> dividedSeries;


    public CrossValidation(List<Serie> containerSeries, int k) {

        List<Serie> series = new ArrayList<>(containerSeries);
        this.dividedSeries = new ArrayList<>();
        int amount = series.size()/k;
        for(int i=0; i<k-1; i++) {
            int[] seriesIds = new Random().ints(0, series.size()).distinct().limit(amount).toArray();
            List<Serie> currentSerie = new ArrayList<>();
            for(int j=0; j<seriesIds.length; j++) {
                currentSerie.add(series.get(seriesIds[j]));
            }
            series.removeAll(currentSerie);
            this.dividedSeries.add(currentSerie);
        }
        this.dividedSeries.add(series);
    }

    public List<Double> executeCrossValidation(int kRepeats) {

        List<Double> results = Arrays.asList(new Double[4]);
        Collections.fill(results, 0.0);
        for(int i=0; i<this.dividedSeries.size(); i++) {
            this.testSamples=new ArrayList<>(this.dividedSeries.get(i));
            this.trainSamples=new ArrayList<>();
            for(int j=0; j<this.dividedSeries.size(); j++) {
                if(j!=i)
                    this.trainSamples.addAll(this.dividedSeries.get(j));
            }

            NearestNeighbourAndMean nearestNeighbourAndMean = new NearestNeighbourAndMean(testSamples, trainSamples);
            results.set(0, results.get(0)+nearestNeighbourAndMean.nearestNeighbour(kRepeats));
            results.set(1, results.get(1)+nearestNeighbourAndMean.nearestNeighbour(1));
            results.set(2, results.get(2)+nearestNeighbourAndMean.nearestMean());
            results.set(3, results.get(3)+nearestNeighbourAndMean.kMeans(kRepeats));
        }

        for(int i=0; i<results.size(); i++) {
            results.set(i, results.get(i)/this.dividedSeries.size());
        }

        return results;
    }

}

package sample.algorithms;

import sample.models.ResultComparator;
import sample.models.ResultDistance;
import sample.models.Serie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NearestNeighbourAndMean {

    private List<Serie> testSamples;
    private List<Serie> trainSamples;

    public NearestNeighbourAndMean(List<Serie> testSamples, List<Serie> trainSamples) {
        this.testSamples = testSamples;
        this.trainSamples = trainSamples;
    }

    public double nearestNeighbour(int kRepeats) {
        List<ResultDistance> results = new ArrayList<ResultDistance>();
        int goodSeries=0;
        double distance = 0.0;
        for(int i=0; i<trainSamples.size(); i++) {
            Serie trainSerie = trainSamples.get(i);
            for (int j = 0; j < testSamples.size(); j++) {
                Serie testSerie = testSamples.get(j);
                for (int k = 0; k < testSerie.getValues().length; k++) {
                    distance += Math.pow((testSerie.getValues()[k] - trainSerie.getValues()[k]), 2);
                }
                distance=Math.sqrt(distance);
                results.add(new ResultDistance(testSerie.getName(), distance));
                distance=0.0;
            }
            if(trainSerie.getName().equals(decideWhichSerie(results, kRepeats)))
                goodSeries++;
        }

        return (double)goodSeries/(double)trainSamples.size();
    }

    public double nearestMean() {

        List<Serie> acerTestSeries = new ArrayList<>();
        List<Serie> quercusTestSeries = new ArrayList<>();

        for(Serie serie : this.testSamples) {
            if(serie.getName().equals("Acer"))
                acerTestSeries.add(serie);
            else
                quercusTestSeries.add(serie);
        }

        List<Double> acerMeans = Arrays.asList(new Double[acerTestSeries.get(0).getValues().length]);
        List<Double> quercusMeans = Arrays.asList(new Double[quercusTestSeries.get(0).getValues().length]);
        Collections.fill(acerMeans, 0.0);
        Collections.fill(quercusMeans, 0.0);

        for(Serie serie : acerTestSeries) {
            for(int i=0; i<serie.getValues().length; i++) {
                acerMeans.set(i, acerMeans.get(i)+serie.getValues()[i]);
            }
        }
        for(Serie serie : quercusTestSeries) {
            for(int i=0; i<serie.getValues().length; i++) {
                quercusMeans.set(i, quercusMeans.get(i)+serie.getValues()[i]);
            }
        }
        for(int i=0; i<acerMeans.size(); i++) {
            acerMeans.set(i, acerMeans.get(i)/acerTestSeries.size());
            quercusMeans.set(i, quercusMeans.get(i)/quercusTestSeries.size());
        }

        double distanceToAcer=0.0, distanceToQuercus=0.0;
        int goodSeries=0;
        for(Serie serie : this.trainSamples) {
            for(int i=0; i<acerMeans.size(); i++) {
                distanceToAcer+=Math.pow((acerMeans.get(i)-serie.getValues()[i]),2);
                distanceToQuercus+=Math.pow((quercusMeans.get(i)-serie.getValues()[i]),2);
            }
            distanceToAcer = Math.sqrt(distanceToAcer);
            distanceToQuercus = Math.sqrt(distanceToQuercus);
            if(distanceToAcer<distanceToQuercus && serie.getName().equals("Acer"))
                goodSeries++;
            else if(distanceToQuercus<distanceToAcer && serie.getName().equals("Quercus"))
                goodSeries++;
            distanceToAcer=0.0; distanceToQuercus=0.0;
        }
        return (double)goodSeries/(double)trainSamples.size();
    }

    public String decideWhichSerie(List<ResultDistance> results, int kRepeats) {
        int acerSeriesCounter=0, quercusSeriesCounter=0;
        results.sort(new ResultComparator());
        for(int j=0; j < kRepeats; j++) {
            if(results.get(j).getName().equals("Acer"))
                acerSeriesCounter++;
            else
                quercusSeriesCounter++;
        }
        results.clear();
        if(acerSeriesCounter>quercusSeriesCounter)
            return "Acer";
        else if(acerSeriesCounter < quercusSeriesCounter)
            return "Quercus";
        else
            return "";
    }

    public double kMeans() {

        List<Serie> acerTestSeries = new ArrayList<>();
        List<Serie> quercusTestSeries = new ArrayList<>();

        for(Serie serie : this.testSamples) {
            if(serie.getName().equals("Acer"))
                acerTestSeries.add(serie);
            else
                quercusTestSeries.add(serie);
        }

        List<Double> acerMeans = Arrays.asList(new Double[acerTestSeries.get(0).getValues().length]);
        List<Double> quercusMeans = Arrays.asList(new Double[quercusTestSeries.get(0).getValues().length]);
        Collections.fill(acerMeans, 0.0);
        Collections.fill(quercusMeans, 0.0);

        for(Serie serie : acerTestSeries) {
            for(int i=0; i<serie.getValues().length; i++) {
                acerMeans.set(i, acerMeans.get(i)+serie.getValues()[i]);
            }
        }
        for(Serie serie : quercusTestSeries) {
            for(int i=0; i<serie.getValues().length; i++) {
                quercusMeans.set(i, quercusMeans.get(i)+serie.getValues()[i]);
            }
        }
        for(int i=0; i<acerMeans.size(); i++) {
            acerMeans.set(i, acerMeans.get(i)/acerTestSeries.size());
            quercusMeans.set(i, quercusMeans.get(i)/quercusTestSeries.size());
        }

        List<String> currentMembership = new ArrayList<>();
        double distanceToAcer=0.0, distanceToQuercus=0.0;
        for(Serie serie : this.trainSamples) {
            for(int i=0; i<acerMeans.size(); i++) {
                distanceToAcer+=Math.pow((acerMeans.get(i)-serie.getValues()[i]),2);
                distanceToQuercus+=Math.pow((quercusMeans.get(i)-serie.getValues()[i]),2);
            }
            distanceToAcer = Math.sqrt(distanceToAcer);
            distanceToQuercus = Math.sqrt(distanceToQuercus);
            if(distanceToAcer<distanceToQuercus)
                currentMembership.add("Acer");
            else
                currentMembership.add("Quercus");
            distanceToAcer=0.0; distanceToQuercus=0.0;
        }

        boolean membershipChanged;
        do {
            membershipChanged = false;
            Collections.fill(acerMeans, 0.0);
            Collections.fill(quercusMeans, 0.0);
            for(int i=0; i<currentMembership.size(); i++) {
                if(currentMembership.get(i).equals("Acer")) {
                    for(int j=0; i<this.trainSamples.get(i).getValues()[j]; j++) {
                        acerMeans.set(j, acerMeans.get(j)+this.trainSamples.get(i).getValues()[j]);
                    }
                } else {
                    for(int j=0; i<this.trainSamples.get(i).getValues()[j]; j++) {
                        quercusMeans.set(j, quercusMeans.get(j)+this.trainSamples.get(i).getValues()[j]);
                    }
                }
            }

            distanceToAcer=0.0; distanceToQuercus=0.0;
            for(int i=0; i<this.trainSamples.size(); i++) {
                for(int j=0; j<acerMeans.size(); j++) {
                    distanceToAcer+=Math.pow((acerMeans.get(j)-this.trainSamples.get(i).getValues()[j]),2);
                    distanceToQuercus+=Math.pow((quercusMeans.get(j)-this.trainSamples.get(i).getValues()[j]),2);
                }
                distanceToAcer = Math.sqrt(distanceToAcer);
                distanceToQuercus = Math.sqrt(distanceToQuercus);
                if(distanceToAcer<distanceToQuercus) {
                    if(currentMembership.get(i).equals("Quercus")) {
                        membershipChanged=true;
                        currentMembership.set(i, "Acer");
                    }
                }
                else {
                    if(currentMembership.get(i).equals("Acer")) {
                        membershipChanged=true;
                        currentMembership.set(i, "Quercus");
                    }
                }
                distanceToAcer=0.0; distanceToQuercus=0.0;
            }

        } while (membershipChanged);

        int goodSeries=0;
        for(int i=0; i<currentMembership.size(); i++) {
            if(this.trainSamples.get(i).getName().equals(currentMembership.get(i)))
                goodSeries++;
        }

        return (double)goodSeries/(double)trainSamples.size();
    }

}

package sample.algorithms;

import sample.models.ResultComparator;
import sample.models.ResultDistance;
import sample.models.Serie;

import java.util.*;

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

    public double kMeans(int k) {

        List<Serie> series = new ArrayList<>();
        series.addAll(this.testSamples);
        series.addAll(this.trainSamples);

        int[] seriesIds = new Random().ints(0, testSamples.size()).distinct().limit(k).toArray();

        List<Serie> choosenSeries = new ArrayList<>();
        List<List<Double>> means = new ArrayList<>();

        for(int i=0; i<seriesIds.length; i++) {
            choosenSeries.add(series.get(seriesIds[i]));
            means.add(new ArrayList<>());
            for(int j=0; j<series.get(seriesIds[i]).getValues().length; j++) {
                means.get(i).add(series.get(seriesIds[i]).getValues()[j]);
            }
        }

        List<Double> distances = Arrays.asList(new Double[seriesIds.length]);
        Collections.fill(distances, 0.0);

        List<String> currentMembership = new ArrayList<>();
        for(Serie serie : series) {
            for(int i=0; i<means.size(); i++) {
                for(int j=0; j<serie.getValues().length; j++) {
                    for(int l=0; l<distances.size(); l++) {
                        distances.set(l, distances.get(l)+Math.pow(means.get(l).get(j) - serie.getValues()[j], 2));
                    }
                }
            }
            int id=-1; double distance=Double.MAX_VALUE;
            for(int i=0; i<distances.size(); i++) {
                distances.set(i, Math.sqrt(distances.get(i)));
                if(distances.get(i) < distance) {
                    id=i;
                    distance=distances.get(i);
                }
            }
            currentMembership.add(String.valueOf(id));
            Collections.fill(distances, 0.0);
        }

        int[] counters = new int[k];

        boolean membershipChanged;
        do {
            membershipChanged = false;
            Arrays.fill(counters, 0);
            for(int i=0; i<means.size(); i++) {
                Collections.fill(means.get(i), 0.0);
            }

            for(int i=0; i<currentMembership.size(); i++) {
                int membership = Integer.parseInt(currentMembership.get(i));
                counters[membership]++;
                for(int j=0; j<means.get(membership).size(); j++) {
                    means.get(membership).set(j, means.get(membership).get(j) + series.get(i).getValues()[j]);
                }
            }

            for(int i=0; i<means.size(); i++) {
                for (int j=0; j<means.get(0).size(); j++) {
                    means.get(i).set(j, means.get(i).get(j)/counters[i]);
                }
            }

            Serie serie;
            for(int p=0; p<series.size(); p++) {
                serie = series.get(p);
                for(int i=0; i<means.size(); i++) {
                    for(int j=0; j<serie.getValues().length; j++) {
                        for(int l=0; l<distances.size(); l++) {
                            distances.set(l, distances.get(l)+Math.pow(means.get(l).get(j) - serie.getValues()[j], 2));
                        }
                    }
                }
                int id=-1; double distance=Double.MAX_VALUE;
                for(int i=0; i<distances.size(); i++) {
                    distances.set(i, Math.sqrt(distances.get(i)));
                    if(distances.get(i) < distance) {
                        id=i;
                        distance=distances.get(i);
                    }
                }
                if(!currentMembership.get(p).equals(String.valueOf(id))) {
                    membershipChanged=true;
                }
                currentMembership.set(p, String.valueOf(id));
                Collections.fill(distances, 0.0);
            }

        } while (membershipChanged);

        int goodSeries=0;
        for(int i=0; i<currentMembership.size(); i++) {
            if(series.get(i).getName().equals(choosenSeries.get(Integer.parseInt(currentMembership.get(i))).getName()))
                goodSeries++;
        }

        return (double)goodSeries/(double)series.size();
    }

}

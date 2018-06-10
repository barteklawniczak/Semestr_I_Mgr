package sample.algorithms;

import org.apache.commons.math3.util.Combinations;
import org.ejml.data.SimpleMatrix;
import sample.models.Container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Fisher {

    private Container container;
    private List<Integer> features;
    private double fisherBestResult;

    public Fisher() {
        this.features = new ArrayList<>();
        this.fisherBestResult = -1;
    }

    public List<Integer> decide(Container container, int n) {

        this.container = container;

        if(n==1) {
            fisherForOneFeature();
        } else {
            fisherForMultipleFeature(n);
        }

        return this.features;
    }

    public void fisherForOneFeature() {

        for(int i=0; i<container.getQuercus().getStandardDeviations().length; i++) {

            double currentFisher = (Math.abs(container.getQuercus().getAverages()[i] - container.getAcer().getAverages()[i]))/
                    (container.getQuercus().getStandardDeviations()[i] + container.getAcer().getStandardDeviations()[i]);
            if(this.fisherBestResult < currentFisher) {
                this.fisherBestResult = currentFisher;
                this.features.clear();
                this.features.add(i);
            }
        }

    }

    public void fisherForMultipleFeature(int n) {

        double[][] quercusValues= new double[container.getQuercus().getValues().length][container.getQuercus().getValues()[0].length];
        double[][] acerValues = new double[container.getAcer().getValues().length][container.getAcer().getValues()[0].length];

        for(int i=0; i<quercusValues.length; i++) {
            for (int j = 0; j < quercusValues[0].length; j++) {
                quercusValues[i][j] = container.getQuercus().getValues()[i][j] - container.getQuercus().getAverages()[i];
            }
        }

        for(int i=0; i<acerValues.length; i++) {
            for (int j = 0; j < acerValues[0].length; j++) {
                acerValues[i][j] = container.getAcer().getValues()[i][j] - container.getAcer().getAverages()[i];
            }
        }

        Combinations combinations = new Combinations(quercusValues.length,n);

        double[][] quercusCurrentFeatures = new double[n][quercusValues[0].length];
        double[][] acerCurrentFeatures = new double[n][acerValues[0].length];

        for(Iterator<int[]> iter = combinations.iterator(); iter.hasNext();) {
            int[] currentFeaturesIndexes = iter.next();
            for(int i=0; i<n; i++) {
                quercusCurrentFeatures[i] = quercusValues[currentFeaturesIndexes[i]];
                acerCurrentFeatures[i] = acerValues[currentFeaturesIndexes[i]];
            }

            SimpleMatrix quercusCurrentFeaturesMatrix = new SimpleMatrix(quercusCurrentFeatures);
            SimpleMatrix acerCurrentFeaturesMatrix = new SimpleMatrix(acerCurrentFeatures);

            SimpleMatrix quercusCovarianceMatrix = quercusCurrentFeaturesMatrix.mult(quercusCurrentFeaturesMatrix.transpose())
                    .scale(Math.pow(quercusCurrentFeatures[0].length, -1));
            SimpleMatrix acerCovarianceMatrix = acerCurrentFeaturesMatrix.mult(acerCurrentFeaturesMatrix.transpose())
                    .scale(Math.pow(acerCurrentFeatures[0].length, -1));

            double determinant = quercusCovarianceMatrix.plus(acerCovarianceMatrix).determinant();

            double denominatorValue=0;
            for(int i=0; i<n; i++) {
                denominatorValue += Math.pow((container.getQuercus().getAverages()[currentFeaturesIndexes[i]]-
                        container.getAcer().getAverages()[currentFeaturesIndexes[i]]),2);
            }
            denominatorValue=Math.sqrt(denominatorValue);

            double fisherValue = denominatorValue/determinant;
            if(fisherValue>this.fisherBestResult) {
                this.fisherBestResult = fisherValue;
                this.features.clear();
                this.features = convertIntArrayToList(currentFeaturesIndexes);
            }

            //Uncomment line below to see what indexes are currently examinated
            //System.out.println(convertIntArrayToList(currentFeaturesIndexes));
        }

    }

    private static List<Integer> convertIntArrayToList(int[] input) {

        List<Integer> list = new ArrayList<>();
        for (int i : input) {
            list.add(i);
        }
        return list;

    }
}

package sample.algorithms;

import org.ejml.data.SimpleMatrix;
import sample.models.Container;

import java.util.ArrayList;
import java.util.List;

public class SFS {

    private Container container;
    private List<Integer> features;
    private double fisherBestResult;
    private int currentBestFeatureIndex;

    public SFS() {
        this.features = new ArrayList<Integer>() {{add(-1);}};
        this.fisherBestResult = -1;
    }

    public List<Integer> performSFS(Container container, int n) {

        this.container = container;
        fisherForOneFeature();
        fisherWithChoosenFeatures(n);
        return this.features;
    }

    public void fisherForOneFeature() {

        for(int i=0; i<container.getQuercus().getStandardDeviations().length; i++) {

            double currentFisher = (Math.abs(container.getQuercus().getAverages()[i] - container.getAcer().getAverages()[i]))/
                    (container.getQuercus().getStandardDeviations()[i] + container.getAcer().getStandardDeviations()[i]);
            if(this.fisherBestResult < currentFisher) {
                this.fisherBestResult = currentFisher;
                this.features.set(0, i);
            }
        }
    }

    public void fisherWithChoosenFeatures(int n) {

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

        for(int i=1; i<n; i++) {
            List<Integer> choosenFeatures = new ArrayList<>(this.features);

            for(int j=0; j<quercusValues.length; j++) {
                if(!choosenFeatures.contains(j)) {

                    double[][] quercusCurrentFeatures = new double[this.features.size()+1][quercusValues[0].length];
                    double[][] acerCurrentFeatures = new double[this.features.size()+1][acerValues[0].length];

                    for(int k=0; k<this.features.size(); k++) {
                        quercusCurrentFeatures[k] = quercusValues[this.features.get(k)];
                        acerCurrentFeatures[k] = acerValues[this.features.get(k)];
                    }
                    quercusCurrentFeatures[this.features.size()] = quercusValues[j];
                    acerCurrentFeatures[this.features.size()] = acerValues[j];

                    SimpleMatrix quercusCurrentFeaturesMatrix = new SimpleMatrix(quercusCurrentFeatures);
                    SimpleMatrix acerCurrentFeaturesMatrix = new SimpleMatrix(acerCurrentFeatures);

                    SimpleMatrix quercusCovarianceMatrix = quercusCurrentFeaturesMatrix.mult(quercusCurrentFeaturesMatrix.transpose())
                            .scale(Math.pow(quercusCurrentFeatures[0].length, -1));
                    SimpleMatrix acerCovarianceMatrix = acerCurrentFeaturesMatrix.mult(acerCurrentFeaturesMatrix.transpose())
                            .scale(Math.pow(acerCurrentFeatures[0].length, -1));

                    double determinant = quercusCovarianceMatrix.plus(acerCovarianceMatrix).determinant();

                    double denominatorValue=0;
                    for(int k=0; k<choosenFeatures.size(); k++) {
                        denominatorValue += Math.pow((container.getQuercus().getAverages()[choosenFeatures.get(k)]-
                                container.getAcer().getAverages()[choosenFeatures.get(k)]),2);
                    }
                    denominatorValue=Math.sqrt(denominatorValue);

                    double fisherValue = denominatorValue/determinant;
                    if(fisherValue>this.fisherBestResult) {
                        this.fisherBestResult = fisherValue;
                        this.currentBestFeatureIndex = j;
                    }

                    //Uncomment line below to see what indexes are currently examinated
                    //System.out.println(""+ choosenFeatures + " " + j);
                }
            }

            this.features.add(this.currentBestFeatureIndex);
            this.fisherBestResult=-1;

        }

    }
}

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import sample.algorithms.*;
import sample.models.Container;
import sample.models.ObjectClass;
import sample.models.Serie;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    private Container container;
    private NearestNeighbourAndMean nearestNeighbourAndMean;

    @FXML
    private Button loadFile;
    @FXML
    private Button computeFisher;
    @FXML
    private Button computeSFS;
    @FXML
    private Button train;
    @FXML
    private Button execute;
    @FXML
    private Button crossValidation;
    @FXML
    private Button iteration;
    @FXML
    private ComboBox nList;
    @FXML
    private ComboBox kList;
    @FXML
    private TextArea textAreaSelection;
    @FXML
    private TextArea textAreaSFS;
    @FXML
    private TextArea textAreakMeans;
    @FXML
    private TextArea textAreakNN;
    @FXML
    private TextArea textAreaNM;
    @FXML
    private TextArea textAreaNN;
    @FXML
    private TextField trainingPart;
    @FXML
    private TextField intervals;

    private final ObservableList<Integer> nItems = FXCollections.observableArrayList();
    private final ObservableList<Integer> kItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.container = new Container();
        this.nList.setItems(nItems);
        this.kList.setItems(kItems);

        this.textAreaSelection.setWrapText(true);
        this.textAreaSFS.setWrapText(true);
        this.textAreakNN.setWrapText(true);
        this.textAreakMeans.setWrapText(true);
        this.textAreaNN.setWrapText(true);
        this.textAreaNM.setWrapText(true);

    }

    public void loadFile() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        fileChooser.setTitle("Open your file");
        File file = fileChooser.showOpenDialog(loadFile.getScene().getWindow());
        if (file != null) {
            Scanner scanner = new Scanner(file).useDelimiter("[\\r\\n]+");
            String[] loadedValues = scanner.next().split(",");
            for (int i = 0; i < Integer.parseInt(loadedValues[0]); i++) {
                nItems.add(i+1);
                kItems.add(i+1);
            }
            nList.getSelectionModel().selectFirst();
            kList.getSelectionModel().selectFirst();
            int acerCounter=0, quercusCounter=0;
            while (scanner.hasNext()) {
                loadedValues = scanner.next().split(",");
                Serie serie = new Serie(loadedValues[0]);
                double[] values = new double[nItems.size()];
                for (int i = 0; i < nItems.size(); i++) {
                    values[i] = Float.parseFloat(loadedValues[i + 1]);
                }
                serie.setValues(values);
                this.container.getSerieList().add(serie);
                if(serie.getName().contains("Acer"))
                    acerCounter++;
                else
                    quercusCounter++;
            }

            scanner.close();
            loadFile.setVisible(false);
            computeFisher.setVisible(true);
            computeSFS.setVisible(true);
            train.setVisible(true);
            crossValidation.setVisible(true);
            iteration.setVisible(true);
            attachToProperClass(acerCounter, quercusCounter);
        }
    }

    public void attachToProperClass(int acerCounter, int quercusCounter) {

        this.container.setAcer(new ObjectClass("Acer", nItems.size(), acerCounter));
        this.container.setQuercus(new ObjectClass("Quercus", nItems.size(), quercusCounter));

        acerCounter=0; quercusCounter=0;

        for(Serie serie : this.container.getSerieList()) {

            if(serie.getName().contains("Acer")) {
                serie.setName("Acer");
                insertValuesToProperTable(this.container.getAcer(), serie, acerCounter);
                acerCounter++;
            } else {
                serie.setName("Quercus");
                insertValuesToProperTable(this.container.getQuercus(), serie, quercusCounter);
                quercusCounter++;
            }
        }

        computeCommonValues(this.container.getAcer());
        computeCommonValues(this.container.getQuercus());
    }

    public void insertValuesToProperTable(ObjectClass objectClass, Serie serie, int counter) {
        for(int i=0; i<serie.getValues().length; i++) {
            objectClass.getValues()[i][counter] = serie.getValues()[i];
        }
    }

    public void computeCommonValues(ObjectClass objectClass) {

        double sum = 0;

        for(int i=0; i<objectClass.getValues().length; i++) {

            for(int j=0; j<objectClass.getValues()[i].length; j++) {
                sum += objectClass.getValues()[i][j];
            }

            objectClass.getAverages()[i] = (sum/(objectClass.getValues()[i].length));
            sum=0;

            for(int j=0; j<objectClass.getValues()[i].length; j++) {
                sum += Math.pow((objectClass.getValues()[i][j] - objectClass.getAverages()[i]),2);
            }

            objectClass.getStandardDeviations()[i] = Math.sqrt(sum/objectClass.getValues()[i].length);
            sum=0;
        }
    }

    public void computeFisher() {
        List<Integer> features = new Fisher().decide(this.container, Integer.parseInt(this.nList.getValue().toString()));
        this.textAreaSelection.appendText("(n=" + this.nList.getValue() + "): " + features + "\n");
    }

    public void computeSFS() {
        List<Integer> features = new SFS().performSFS(this.container, Integer.parseInt(this.nList.getValue().toString()));
        this.textAreaSFS.appendText("(n=" + this.nList.getValue() + "): " + features + "\n");
    }

    public void train() {
        if(!trainingPart.getText().isEmpty()) {
            int trainingPartPercent = Integer.parseInt(trainingPart.getText());
            if(trainingPartPercent>0 && trainingPartPercent<100) {

                int samplesAmount = container.getQuercus().getValues()[0].length+container.getAcer().getValues()[0].length;
                int[] trainSamples = new Random().ints(0, samplesAmount).distinct().limit(samplesAmount*trainingPartPercent/100).toArray();
                List<Serie> testSamplesList = new ArrayList<>(this.container.getSerieList());
                List<Serie> trainSamplesList = new ArrayList<>();
                for(int i=0; i<trainSamples.length; i++) {
                    trainSamplesList.add(testSamplesList.get(trainSamples[i]));
                }
                testSamplesList.removeAll(trainSamplesList);

               this.nearestNeighbourAndMean = new NearestNeighbourAndMean(testSamplesList, trainSamplesList);
               this.execute.setVisible(true);
            }
        }
    }

    public void execute() {
        String k = this.kList.getValue().toString();
        String kNNPercent = String.format("%.2f", this.nearestNeighbourAndMean.nearestNeighbour(Integer.parseInt(k))*100);
        String oneNNPercent = String.format("%.2f", this.nearestNeighbourAndMean.nearestNeighbour(1)*100);
        String oneNMPercent = String.format("%.2f", this.nearestNeighbourAndMean.nearestMean()*100);
        String kMeans = String.format("%.2f", this.nearestNeighbourAndMean.kMeans(Integer.parseInt(k))*100);
        this.textAreakNN.appendText("(k=" + k + ", train: " + this.trainingPart.getText() + "%): efficiency: " + kNNPercent + "%\n");
        this.textAreaNN.appendText("(k=1, train: " + this.trainingPart.getText() + "%): efficiency: " + oneNNPercent + "%\n");
        this.textAreaNM.appendText("(train: " + this.trainingPart.getText() + "%): efficiency: " + oneNMPercent + "%\n");
        this.textAreakMeans.appendText("(k=" + k + "): efficiency: " + kMeans + "%\n");

    }

    public void crossValidation() {
        int k = Integer.parseInt(this.kList.getValue().toString());
        int i = Integer.parseInt(this.intervals.getText());
        if(i>1) {
            CrossValidation crossValidation = new CrossValidation(this.container.getSerieList(), i);
            List<Double> results = crossValidation.executeCrossValidation(k);
            String kNNPercent = String.format("%.2f", results.get(0)*100);
            String oneNNPercent = String.format("%.2f", results.get(1)*100);
            String oneNMPercent = String.format("%.2f", results.get(2)*100);
            String kMeans = String.format("%.2f", results.get(3)*100);
            this.textAreakNN.appendText("(k=" + k + ", i=" + i + "): quality: " + kNNPercent + "%\n");
            this.textAreaNN.appendText("(k=1, i=" + i + "), quality: " + oneNNPercent + "%\n");
            this.textAreaNM.appendText("(i=" + i + "): quality: " + oneNMPercent + "%\n");
            this.textAreakMeans.appendText("(k=" + k + ", i=" + i + "): quality: " + kMeans + "%\n");

        }
    }

    public void iteration() {
        int k = Integer.parseInt(this.kList.getValue().toString());
        int i = Integer.parseInt(this.intervals.getText());
        int trainingPartPercent = Integer.parseInt(trainingPart.getText());
        if(i>1 && (trainingPartPercent >0 && trainingPartPercent<100)) {
            Bootstrap bootstrap = new Bootstrap();
            List<Double> results = bootstrap.executeBootstrap(this.container.getSerieList(), k, i, trainingPartPercent);
            String kNNPercent = String.format("%.2f", results.get(0)*100);
            String oneNNPercent = String.format("%.2f", results.get(1)*100);
            String oneNMPercent = String.format("%.2f", results.get(2)*100);
            String kMeans = String.format("%.2f", results.get(3)*100);
            this.textAreakNN.appendText("(k=" + k + ", i=" + i + "): quality: " + kNNPercent + "%\n");
            this.textAreaNN.appendText("(k=1, i=" + i + "), quality: " + oneNNPercent + "%\n");
            this.textAreaNM.appendText("(i=" + i + "): quality: " + oneNMPercent + "%\n");
            this.textAreakMeans.appendText("(k=" + k + ", i=" + i + "): quality: " + kMeans + "%\n");

        }
    }

}

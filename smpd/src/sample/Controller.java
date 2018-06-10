package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import sample.algorithms.Fisher;
import sample.models.Container;
import sample.models.ObjectClass;
import sample.models.Serie;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

    private Container container;

    @FXML
    private Button loadFile;
    @FXML
    private Button computeFisher;
    @FXML
    private ComboBox nList;
    @FXML
    private TextArea textArea;

    private final ObservableList<Integer> nItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.container = new Container();
        this.nList.setItems(nItems);
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
                nItems.add(i + 1);
            }

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
            attachToProperClass(acerCounter, quercusCounter);
        }
    }

    public void attachToProperClass(int acerCounter, int quercusCounter) {

        this.container.setAcer(new ObjectClass("Acer", nItems.size(), acerCounter));
        this.container.setQuercus(new ObjectClass("Quercus", nItems.size(), quercusCounter));

        acerCounter=0; quercusCounter=0;

        for(Serie serie : this.container.getSerieList()) {

            if(serie.getName().contains("Acer")) {
                insertValuesToProperTable(this.container.getAcer(), serie, acerCounter);
                acerCounter++;
            } else {
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
        this.textArea.setWrapText(true);
        this.textArea.appendText("Najlepsze cechy dla n=" + this.nList.getValue() + ": " + features + "\n");
    }
}

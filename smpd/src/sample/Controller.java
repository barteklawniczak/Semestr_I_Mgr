package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import sample.models.Container;
import sample.models.ObjectClass;
import sample.models.Serie;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

    private Container container;

    @FXML
    private Button loadFile;

    @FXML
    private Button fisher;

    @FXML
    private ComboBox acer;

    @FXML
    private ComboBox quercus;

    @FXML
    private ComboBox nList;

    @FXML
    private TextArea textArea;

    private final ObservableList<Integer> nItems = FXCollections.observableArrayList();
    private final ObservableList<String> acerNames = FXCollections.observableArrayList();
    private final ObservableList<String> quercusNames = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.container = new Container();
        this.nList.setItems(nItems);
        this.acer.setItems(acerNames);
        this.quercus.setItems(quercusNames);
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

            int counter=0;

            while (scanner.hasNext()) {
                loadedValues = scanner.next().split(",");
                if(counter==16)
                    counter=0;
                if(counter==0) {
                    ObjectClass objectClass = new ObjectClass(loadedValues[0]);
                    this.container.getObjectClasses().add(objectClass);
                    if(loadedValues[0].contains("Acer"))
                        this.acerNames.add(loadedValues[0]);
                    else
                        this.quercusNames.add(loadedValues[0]);
                }
                Serie serie = new Serie();
                float[] values = new float[nItems.size()];
                for (int i = 0; i < nItems.size(); i++) {
                    values[i] = Float.parseFloat(loadedValues[i + 1]);
                }
                serie.setValues(values);
                this.container.getObjectClasses().get(this.container.getObjectClasses().size()-1).getSerieList().add(serie);
                counter++;
            }

            scanner.close();
            loadFile.setVisible(false);
            fisher.setVisible(true);
            computeCommonValues();
        }
    }

    public void computeCommonValues() {

        float sum = 0;

        for(ObjectClass objectClass : container.getObjectClasses()) {

            for(Serie serie : objectClass.getSerieList()) {

                for (int i = 0; i < serie.getValues().length; i++) {
                    sum += serie.getValues()[i];
                }

                serie.setAverage(sum / (serie.getValues().length));
                sum = 0;

                for (int i = 0; i < serie.getValues().length; i++) {
                    sum += Math.pow((serie.getValues()[i] - serie.getAverage()), 2);
                }

                serie.setStandardDeviation(Math.sqrt(sum / serie.getValues().length));
            }
        }
    }

    public void computeFisher() {

    }
}

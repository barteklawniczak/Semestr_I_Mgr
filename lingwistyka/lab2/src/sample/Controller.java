package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import sample.models.State;
import sample.models.Table;
import sample.models.TableViewObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    private Table table;
    private List<String> series;
    private int serieCounter;
    private char[] currentSerie;
    private int charCounter;

    @FXML
    private TextField currentSerieText;

    @FXML
    private Button loadFile;

    @FXML
    private Button nextSerie;

    @FXML
    private TextField message;

    @FXML
    private TableView<TableViewObject> tableView;

    private final ObservableList<TableViewObject> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.table = new Table();
        this.series = new ArrayList<String>();
        tableView.setEditable(true);
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("stateSymbol"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("currentChar"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("outSymbol"));
        data.add(new TableViewObject(Arrays.asList(this.table.getStateList().get(0)), '-', Arrays.asList(this.table.getStateList().get(0))));
        tableView.setItems(data);
    }

    public void loadFile() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        fileChooser.setTitle("Open your file");
        File file = fileChooser.showOpenDialog(loadFile.getScene().getWindow());
        if(file != null) {
            Scanner scanner = new Scanner(file).useDelimiter("\\s*#");
            while (scanner.hasNext()) {
                this.series.add(scanner.next());
            }
            scanner.close();
            loadFile.setVisible(false);
            serieCounter = 0;
            start();
        }
    }

    public void start() {
        this.data.clear();
        this.table.setCurrentStates(new ArrayList<State>() {{add(table.getStateList().get(0));}});
        this.currentSerie = this.series.get(this.serieCounter).toCharArray();
        this.currentSerieText.setText(this.series.get(this.serieCounter));
        this.serieCounter++;
        this.automatStart();
    }

    public void automatStart() {

        for(int i=0; i<this.currentSerie.length; i++) {

            ArrayList<State> nextStates = this.table.getNextStates(this.currentSerie[i]);
            data.add(new TableViewObject(this.table.getCurrentStates(), this.currentSerie[i], nextStates));
            this.table.setCurrentStates(nextStates);
        }

        message.setText(this.table.getCurrentStates().get(0).getMessage());
        for(State state : this.table.getCurrentStates()) {
            if(state.getId() == 11 || state.getId() == 12)
                message.setText(state.getMessage());
        }

        if(serieCounter != this.series.size())
            nextSerie.setVisible(true);
        else
            nextSerie.setVisible(false);
    }

    public void next() {
        start();
    }
}

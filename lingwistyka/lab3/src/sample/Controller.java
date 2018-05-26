package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import sample.models.State;
import sample.models.Table;
import sample.models.TableViewObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {

    private Table table;
    private List<TextField> textFieldList;
    private int currentCharPositionInString;

    @FXML
    private TableView<TableViewObject> tableView;

    @FXML
    private TextField currentSerieText;
    @FXML
    private TextField resultSerie;
    @FXML
    private TextField current;

    @FXML
    private Button approve;
    @FXML
    private Button auto;
    @FXML
    private Button next;

    @FXML
    private TextField field1;
    @FXML
    private TextField field2;
    @FXML
    private TextField field3;
    @FXML
    private TextField field4;
    @FXML
    private TextField field5;
    @FXML
    private TextField field6;
    @FXML
    private TextField field7;
    @FXML
    private TextField field8;
    @FXML
    private TextField field9;

    private final ObservableList<TableViewObject> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.auto.setVisible(false);
        this.next.setVisible(false);
        this.table = new Table();
        data.add(new TableViewObject(this.table.getCurrentState(), "-", new State(0, "-", '-')));
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("stateSymbol"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("currentChar"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("outSymbol"));
        tableView.setItems(data);
        textFieldList=new ArrayList<>();
        textFieldList.add(field1);
        textFieldList.add(field2);
        textFieldList.add(field3);
        textFieldList.add(field4);
        textFieldList.add(field5);
        textFieldList.add(field6);
        textFieldList.add(field7);
        textFieldList.add(field8);
        textFieldList.add(field9);
        this.current.setText("Q" + this.table.getCurrentState());
    }

    public void approveSerie() {

        this.auto.setVisible(true);
        this.next.setVisible(true);
        this.approve.setVisible(false);
        this.currentSerieText.setEditable(false);
        this.currentSerieText.setText("#" + this.currentSerieText.getText());
        char[] serie = this.currentSerieText.getText().toCharArray();
        int counter=1;
        for(int i=4; i>=0; i--) {
            if(serie.length - counter >= 0)
                this.textFieldList.get(i).setText("" + serie[serie.length-counter++]);
        }
        this.currentCharPositionInString = serie.length-1;

    }

    public void autoResolve() throws InterruptedException {

        this.auto.setVisible(false);
        this.next.setVisible(false);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3),
                        event -> {
                    nextStep();
                })
        );
        timeline.setCycleCount(this.currentCharPositionInString+1);
        timeline.play();

    }

    public void nextStep() {
        String currentChar = this.field5.getText();
        State nextState = this.table.getNextState(currentChar.charAt(0));
        data.add(new TableViewObject(this.table.getCurrentState(), currentChar, nextState));
        if(nextState.getCharToWrite() != '-')
            this.resultSerie.setText(nextState.getCharToWrite() + this.resultSerie.getText());
        this.moveTape(nextState.getDecision());
        this.current.setText("Q" + nextState.getId());
        if(!currentChar.equals("#"))
            this.table.setCurrentState(nextState.getId());
        else {
            this.next.setVisible(false);
            this.auto.setVisible(false);
        }
    }

    public void moveTape(String decision) {

        if(decision.equals("L")) {

            for(int i=8; i>=1; i--) {
                this.textFieldList.get(i).setText(this.textFieldList.get(i-1).getText());
            }

            if(this.currentCharPositionInString>4) {
                this.textFieldList.get(0).setText("" + this.currentSerieText.getText().toCharArray()[this.currentCharPositionInString - 5]);
                this.currentCharPositionInString--;
            } else {
                this.textFieldList.get(0).setText("");
            }

        }
        //in my case tape can't stay in place or go right so I'm not implementing it
    }
}

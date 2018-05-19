package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.models.State;
import sample.models.Table;
import sample.models.TableViewObject;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField amount;

    @FXML
    private TextField message;

    @FXML
    private Button buttonYes;

    @FXML
    private Button buttonNo;

    @FXML
    private Button oneZloty;

    @FXML
    private Button twoZlotych;

    @FXML
    private Button fiveZlotych;

    @FXML
    private TableView<TableViewObject> tableView;

    private Table table;

    private final ObservableList<TableViewObject> data =
            FXCollections.observableArrayList(new TableViewObject(0, 0, 0));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.table = new Table();
        amount.setText("Kwota: " + this.table.getAmount());
        tableView.setEditable(true);
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("stateSymbol"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("amount"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("outSymbol"));
        tableView.setItems(data);
    }

    public void oneZloty() {
        changeState(1);
    }

    public void twoZlotych() {
        changeState(2);
    }

    public void fiveZlotych() {
        changeState(5);
    }

    public void changeState(int thrown) {
        if(thrown != 3 && thrown != 4 && thrown != 6 && thrown != 7)
            this.table.setAmount(this.table.getAmount() + thrown);
        amount.setText("Kwota: " + this.table.getAmount() + " zł");
        State nextState = this.table.getNextState(thrown);
        if(thrown == 3 || thrown == 4 || thrown == 6 || thrown == 7)
            thrown=0;
        data.add(new TableViewObject(this.table.getCurrentState(), thrown, nextState.getId()));
        this.table.setCurrentState(nextState.getId());
        if(nextState.getId() > 4)
            makeAction(nextState);
    }

    public void makeAction(State state) {
        this.message.setText(state.getMessage());
        this.oneZloty.setDisable(true);
        this.twoZlotych.setDisable(true);
        this.fiveZlotych.setDisable(true);
        this.message.setVisible(true);
        if(state.getId() == 5 || state.getId() == 6) {
            this.buttonNo.setVisible(true);
            this.buttonYes.setVisible(true);
        }
        else {
            this.result();
        }
    }

    public void agreed() {
        this.buttonNo.setVisible(false);
        this.buttonYes.setVisible(false);
        if(this.table.getCurrentState()==5)
            changeState(3);
        else
            changeState(4);
        this.oneZloty.setDisable(false);
        this.twoZlotych.setDisable(false);
        this.fiveZlotych.setDisable(false);
        this.buttonNo.setVisible(false);
        this.buttonYes.setVisible(false);
        this.message.setVisible(false);
        result();
    }

    public void decline() {
        this.buttonNo.setVisible(false);
        this.buttonYes.setVisible(false);
        if(this.table.getCurrentState()==5)
            changeState(6);
        else
            changeState(7);
        this.oneZloty.setDisable(true);
        this.twoZlotych.setDisable(true);
        this.fiveZlotych.setDisable(true);
        this.message.setVisible(true);
        result();
    }

    public void result() {
        State state = this.table.getStateList().get(this.table.getCurrentState());
        if(state.getRest() == 0)
            this.message.setText(state.getMessage());
        else
            this.message.setText(state.getMessage() + " oraz " + state.getRest() + " zł reszty");
    }
}

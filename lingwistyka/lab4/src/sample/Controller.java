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
import sample.models.TableViewObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private List<TextField> textFieldList;
    private int currentPositionInString;
    private List<String> elementsInSerieList;
    private List<String> heap;
    private String resultSerieText = "";
    private String previous;
    @FXML
    private TableView<TableViewObject> tableView;

    @FXML
    private TextField currentSerieText;
    @FXML
    private TextField resultSerie;

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

    private final ObservableList<TableViewObject> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.previous="";
        this.auto.setVisible(false);
        this.next.setVisible(false);
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("inSymbol"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("heap"));
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
        this.elementsInSerieList = new ArrayList<>();
        this.heap = new ArrayList<>();
        this.resultSerie.setText(this.resultSerieText);
    }

    public void approveSerie() {

        this.auto.setVisible(true);
        this.next.setVisible(true);
        this.approve.setVisible(false);
        this.currentSerieText.setEditable(false);
        this.currentSerieText.setText(this.currentSerieText.getText() + "#");
        String currentSerie = this.currentSerieText.getText();
        String[] elements = currentSerie.split("((?<=[\\\\+])|(?=[\\\\+]))|((?<=[\\-])|(?=[\\-]))|((?<=[\\s+])|(?=[\\s+]))|((?<=[*])|(?=[*]))" +
                "|((?<=[/])|(?=[/]))|((?<=[\\\\^])|(?=[\\\\^]))|((?<=[#])|(?=[#]))|((?<=[)])|(?=[)]))|((?<=[(])|(?=[(]))");

        for(int i=0; i<elements.length; i++) {
            if(elements[i].equals("-")) {
                if(i==0 || elements[i-1].matches("[\\\\+\\-*/^]")) {
                    elements[i+1] = "-" + elements[i+1];
                } else {
                    this.elementsInSerieList.add(0, elements[i]);
                }
            }
            if(!elements[i].equals("-")) {
                this.elementsInSerieList.add(0, elements[i]);
            }
        }

        int counter=1;
        for(int i=3; i>=0; i--) {
            if(elementsInSerieList.size() - counter >= 0)
                this.textFieldList.get(i).setText("" + elementsInSerieList.get(elementsInSerieList.size()-counter++));
        }
        this.currentPositionInString = elementsInSerieList.size()-1;

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
        timeline.setCycleCount(this.currentPositionInString +1);
        timeline.play();

    }

    public void nextStep() {
        String current = this.field4.getText();
        String outSymbols = this.makeDecision(current);
        data.add(new TableViewObject(current, this.heap, outSymbols));
        this.previous = current;
        if(!current.equals("#"))
            this.moveTape("L");
    }

    public void moveTape(String decision) {

        if(decision.equals("L")) {

            for(int i=6; i>=1; i--) {
                this.textFieldList.get(i).setText(this.textFieldList.get(i-1).getText());
            }

            if(this.currentPositionInString >3) {
                this.textFieldList.get(0).setText(this.elementsInSerieList.get(this.currentPositionInString - 4));
                this.currentPositionInString--;
            } else {
                this.textFieldList.get(0).setText("");
            }

        }
        //in my case tape can't stay in place or go right so I'm not implementing it
    }

    public String makeDecision(String current) {

        if(current.matches("-?\\d+")) {
            if(this.previous.equals(")")) {
                addToHeap("*");
            }
            this.resultSerie.setText(this.resultSerie.getText() + current);
            return current;
        }
        if(current.equals("(")) {
            if(this.previous.matches("-?\\d+")) {
                addToHeap("*");
            }
            addToHeap(current);
        }
        if(current.equals(")"))
            return this.returnHeapValuesToBracket();
        if(current.matches("[\\\\+\\-*/^]"))
            this.checkPriorities(current);
        if(current.equals("#")) {
            this.next.setVisible(false);
            this.auto.setVisible(false);
            return this.returnHeapValuesToBracket();
        }
        return "";
    }

    public void addToHeap(String element) {
        this.heap.add(0, element);
    }

    public String returnHeapValuesToBracket() {

        String returnString = "";

        while (!this.heap.isEmpty()) {
            if (this.heap.get(0).equals("(")) {
                this.heap.remove(0);
                break;
            } else {
                this.resultSerie.setText(this.resultSerie.getText() + this.heap.get(0));
                returnString = returnString + this.heap.get(0) + " ";
                this.heap.remove(0);
            }
        }

        return returnString;
    }

    public String checkPriorities(String element) {

        String returnString = "";

        if(element.equals("^")) {
            if(!this.heap.isEmpty() && this.heap.get(0).equals("^")) {
                returnString = this.returnHeapValuesMatchingRegex("[\\\\^]");
            }
            this.addToHeap(element);
        }

        if(element.equals("*") || element.equals("/")) {
            if(!this.heap.isEmpty() && this.heap.get(0).matches("[*/^]")) {
                returnString = this.returnHeapValuesMatchingRegex("[*/^]");
            }
            this.addToHeap(element);
        }

        if(element.equals("+") || element.equals("-")) {
            if(!this.heap.isEmpty() && !this.heap.get(0).equals("(")) {
                returnString = this.returnHeapValuesMatchingRegex("[\\\\+\\-*/^]");
            }
            this.addToHeap(element);
        }

        return returnString;
    }

    public String returnHeapValuesMatchingRegex(String regex) {

        String returnString = "";

        while(!this.heap.isEmpty()) {
            if(this.heap.get(0).matches(regex)) {
                this.resultSerie.setText(this.resultSerie.getText() + this.heap.get(0));
                returnString = returnString + this.heap.get(0) + " ";
                this.heap.remove(0);
            } else {
                break;
            }
        }

        return returnString;
    }
}

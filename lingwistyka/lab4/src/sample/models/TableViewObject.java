package sample.models;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class TableViewObject {

    private SimpleStringProperty inSymbol = new SimpleStringProperty();
    private SimpleStringProperty heap = new SimpleStringProperty();
    private SimpleStringProperty outSymbol = new SimpleStringProperty();

    public String getInSymbol() {
        return inSymbol.get();
    }

    public SimpleStringProperty inSymbolProperty() {
        return inSymbol;
    }

    public void setInSymbol(String inSymbol) {
        this.inSymbol.set(inSymbol);
    }

    public String getHeap() {
        return heap.get();
    }

    public SimpleStringProperty heapProperty() {
        return heap;
    }

    public void setHeap(String heap) {
        this.heap.set(heap);
    }

    public String getOutSymbol() {
        return outSymbol.get();
    }

    public SimpleStringProperty outSymbolProperty() {
        return outSymbol;
    }

    public void setOutSymbol(String outSymbol) {
        this.outSymbol.set(outSymbol);
    }

    public TableViewObject(String inSymbol, List<String> heap, String outSymbol) {
        this.inSymbol.set(inSymbol);
        this.outSymbol.set(outSymbol);
        String heapTmp = "";
        if(!heap.isEmpty()) {
            for(String heapElement : heap) {
                heapTmp += heapElement + " ";
            }
        }
        this.heap.set(heapTmp);
    }
}

//package com.wave.trace.wave_trace.elements;
//
//import javafx.scene.control.Label;
//import javafx.scene.layout.GridPane;
//
//public class LabelCell{
//    private boolean isBarrier;
//    private boolean isA;
//    private boolean isB;
//    private int index = 0;
//    private LabelCell prevLabelCell;
//    private Label lb;
//    private Label lbIndex;
//    private int i;
//    private int j;
//
//    private int front = 0;
//
//
//
//    public LabelCell(int i, int j) {
//        this.i = i;
//        this.j = j;
//    }
//
//
//
//
//
//    public boolean isBarrier() {
//        return isBarrier;
//    }
//    public void mark(){
//        grid.setStyle("-fx-background-color: rgb(0,255,127,25)");
//    }
//    public void setBarrier(){
//        unSet();
//        isBarrier = true;
//
//        grid.setStyle("-fx-background-color: black");
//    }
//    public void unSetBarrier(){
//        grid.setStyle("-fx-background-color: none");
//    }
//
//    public void unSet(){
//        isA=false;
//        unSetA();
//        isB=false;
//        unSetB();
//        isBarrier = false;
//        unSetBarrier();
//    }
//    public void setA() {
//        if(isB){
//            isB=false;
//        }
//        isA = true;
//        lb.setText("A");
//    }
//    public void unSetA() {
//        isA = false;
//        lb.setText(" ");
//    }
//    public void unSetB() {
//        isB = false;
//        lb.setText(" ");
//    }
//    public void setB() {
//        if(isA){
//            isA=false;
//        }
//        isB = true;
//        lb.setText("B");
//    }
//
//    public boolean isA() {
//        return isA;
//    }
//
//    public boolean isB() {
//        return isB;
//    }
//
//
//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//        lbIndex.setText(""+index);
//    }
//
//    public LabelCell getPrevCell() {
//        return prevLabelCell;
//    }
//
//    public void setPrevCell(LabelCell prevGridCell) {
//        this.prevLabelCell = prevLabelCell;
//    }
//
//    public int getI() {
//        return i;
//    }
//
//    public void setI(int i) {
//        this.i = i;
//    }
//
//    public int getJ() {
//        return j;
//    }
//
//    public void setJ(int j) {
//        this.j = j;
//    }
//
//    public int getFront() {
//        return front;
//    }
//
//    public void setFront(int front) {
//        this.front = front;
//        lb.setText(""+front);
//    }
//}

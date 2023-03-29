package com.wave.trace.wave_trace.elements;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GridCell {

    private boolean isUsed = false;

    private int lengthFromA = 0;
    private double innerCellSize;
    private boolean isBarrier;
    private boolean isA;
    private boolean isB;
    private int index = 0;
    private GridCell prevGridCell;
    private Label lb;
    private Label lbIndex;
    private int i;
    private int j;
    private int front = 0;
    GridPane grid;
    private Direction direction = null;



    private void createGrid(double innerCS){
        innerCellSize = innerCS;
        grid = new GridPane();
       // grid.setGridLinesVisible(true);
        for (int k = 0; k < 3; k++) {
            grid.getRowConstraints().add(new RowConstraints(innerCellSize));
            for (int l = 0; l < 3; l++) {
                if(k<1){
                    grid.getColumnConstraints().add(new ColumnConstraints(innerCellSize));
                }
                if(k==0 && l==2){
                    lbIndex = new Label(" ");
                    lbIndex.setStyle("-fx-font-size: 10 ;");
                    grid.add(lbIndex,l,k);
                }
                if(k == 1 && l == 1){
                    lb = new Label("");
                    lb.setStyle("-fx-font-size: 12 ;");
                    GridPane.setHalignment(lb,HPos.CENTER);
                    grid.add(lb,1,1);
                }else{
                    grid.add(new Label(" "),l,k);
                }
            }

        }
    }
    public GridCell(int i, int j, double innerCellSize) {
        this.i = i;
        this.j = j;
        createGrid(innerCellSize);

    }

    public int getLengthFromA() {
        return lengthFromA;
    }

    public void setLengthFromA(int lengthFromA) {
        this.lengthFromA = lengthFromA;
    }

    public void setDirection(Direction dir)  {
        ImageView iv = null;
        try {
            iv = new ImageView(new Image(new FileInputStream("D:\\АКИТП\\WaveTrace\\wave_trace\\src\\main\\resources\\com\\wave\\trace\\wave_trace\\arrow.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        iv.setFitHeight(innerCellSize);
        iv.setFitWidth(innerCellSize);
        direction = dir;
        switch (dir){
            case UP ->{
                grid.add(iv,1,0);
            }
            case LEFT -> {
                iv.setRotate(270);
                grid.add(iv,0,1);
            }
            case RIGHT -> {
                iv.setRotate(90);
                grid.add(iv,2,1);
            }
            case BOTTOM -> {
                iv.setRotate(180);
                grid.add(iv,1,2);
            }
        }
    }


    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean isBarrier() {
        return isBarrier;
    }
    public void mark(){
        grid.setStyle("-fx-background-color: rgb(0,255,127,25)");
    }
    public void setBarrier(){
        unSet();
        isBarrier = true;

        grid.setStyle("-fx-background-color: black");
    }
    public void unSetBarrier(){
        grid.setStyle("-fx-background-color: none");
    }

    public void unSet(){
        isA=false;
        unSetA();
        isB=false;
        unSetB();
        isBarrier = false;
        unSetBarrier();
    }
    public void setA() {
        if(isB){
            isB=false;
        }
        isA = true;
        lb.setText("A");
    }
    public void unSetA() {
        isA = false;
        lb.setText(" ");
    }
    public void unSetB() {
        isB = false;
        lb.setText(" ");
    }
    public void setB() {
        if(isA){
            isA=false;
        }
        isB = true;
        lb.setText("B");
    }

    public boolean isA() {
        return isA;
    }

    public boolean isB() {
        return isB;
    }

    public GridPane getGrid() {
        return grid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        lbIndex.setText(""+index);
    }

    public GridCell getPrevCell() {
        return prevGridCell;
    }

    public void setPrevCell(GridCell prevGridCell) {
        this.prevGridCell = prevGridCell;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
        lb.setText(""+front);
    }
}

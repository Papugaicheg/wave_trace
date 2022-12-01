package com.wave.trace.wave_trace;

import com.wave.trace.wave_trace.elements.Direction;
import com.wave.trace.wave_trace.elements.GridCell;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;
import java.util.Map;

public class FieldDrawer {
    private static GridCell aLetter;
    private static GridCell bLetter;

    private static Map<Integer, GridCell> cellMap = new HashMap<>();
    private static Integer lastCellIndex = 0;
    private static Integer firstFrontIndex = 1;
    private static Integer frontNumber = 1;
    private static int w;
    private static int h;


    private static GridCell[][] cellList;
    private static boolean check = true;

    private static void reverse(int i, int j) {

        while (!cellList[i][j].isA()) {
            cellList[i][j].mark();
            int newI = cellList[i][j].getPrevCell().getI();
            j = cellList[i][j].getPrevCell().getJ();
            i = newI;
        }
        cellList[i][j].mark();

    }

    private static boolean buildAround(int i, int j) {
        if (i - 1 >= 0) {
            if (!cellList[i - 1][j].isBarrier() && !cellList[i - 1][j].isA() && !cellList[i - 1][j].isB() && cellList[i - 1][j].getFront() == 0) {


                System.out.println("Зашёл в верх");
                cellList[i - 1][j].setFront(frontNumber);
                lastCellIndex++;
                cellList[i - 1][j].setIndex(lastCellIndex);
                cellList[i - 1][j].setDirection(Direction.BOTTOM);
                cellMap.put(lastCellIndex, cellList[i - 1][j]);
                cellList[i - 1][j].setPrevCell(cellList[i][j]);
            }
            if (cellList[i - 1][j].isB()) {
                cellList[i - 1][j].setPrevCell(cellList[i][j]);
                reverse(i - 1, j);
                return true;
            }

        }
        if (j + 1 < w) {
            if (!cellList[i][j + 1].isBarrier() && !cellList[i][j + 1].isA() && !cellList[i][j + 1].isB() && cellList[i][j + 1].getFront() == 0) {
                System.out.println("Зашёл вправо");
                cellList[i][j + 1].setFront(frontNumber);
                lastCellIndex++;
                cellList[i][j + 1].setIndex(lastCellIndex);
                cellList[i][j + 1].setDirection(Direction.LEFT);

                cellMap.put(lastCellIndex, cellList[i][j + 1]);
                cellList[i][j + 1].setPrevCell(cellList[i][j]);
            }
            if (cellList[i][j + 1].isB()) {
                cellList[i][j + 1].setPrevCell(cellList[i][j]);
                reverse(i, j + 1);
                return true;
            }

        }
        if (i + 1 < h) {
            if (!cellList[i + 1][j].isBarrier() && !cellList[i + 1][j].isA() && !cellList[i + 1][j].isB() && cellList[i + 1][j].getFront() == 0) {
                System.out.println("Вниз");
                cellList[i + 1][j].setFront(frontNumber);
                lastCellIndex++;
                cellList[i + 1][j].setIndex(lastCellIndex);
                cellList[i + 1][j].setDirection(Direction.UP);
                cellList[i + 1][j].setPrevCell(cellList[i][j]);
                cellMap.put(lastCellIndex, cellList[i + 1][j]);
            }
            if (cellList[i + 1][j].isB()) {
                cellList[i + 1][j].setPrevCell(cellList[i][j]);
                reverse(i + 1, j);
                return true;
            }
        }
        if (j - 1 >= 0) {
            if (!cellList[i][j - 1].isBarrier() && !cellList[i][j - 1].isA() && !cellList[i][j - 1].isB() && cellList[i][j - 1].getFront() == 0) {
                System.out.println("Вktdj");
                cellList[i][j - 1].setFront(frontNumber);
                lastCellIndex++;
                cellList[i][j - 1].setIndex(lastCellIndex);
                cellList[i][j - 1].setDirection(Direction.RIGHT);
                cellList[i][j - 1].setPrevCell(cellList[i][j]);
                cellMap.put(lastCellIndex, cellList[i][j - 1]);
            }
            if (cellList[i][j - 1].isB()) {
                cellList[i][j - 1].setPrevCell(cellList[i][j]);
                reverse(i, j - 1);
                return true;
            }

        }
        return false;

    }


    private static void threeFronts() {
        if (aLetter != null && bLetter != null) {
            if (lastCellIndex == 0) {
                int i = aLetter.getI();
                int j = aLetter.getJ();
                buildAround(i, j);
            } else {
                int endOfFront = lastCellIndex;
                System.out.println(endOfFront);
                for (int l = firstFrontIndex; l <= endOfFront; l++) {
                    int i = cellMap.get(l).getI();
                    int j = cellMap.get(l).getJ();
                    if (buildAround(i, j)) {
                        break;
                    }
                    ;
                }

            }
            frontNumber++;
            if (frontNumber == 4) {
                frontNumber = 1;
            }

        }

    }


    private static void nextFront() {
        if (aLetter != null && bLetter != null) {
            if (frontNumber == 1) {
                int i = aLetter.getI();
                int j = aLetter.getJ();
                buildAround(i, j);
            } else {
                int endOfFront = lastCellIndex;
                System.out.println(endOfFront);
                for (int l = firstFrontIndex; l <= endOfFront; l++) {
                    int i = cellMap.get(l).getI();
                    int j = cellMap.get(l).getJ();
                    if (buildAround(i, j)) {
                        break;
                    }
                    ;
                }

            }
            frontNumber++;

        }
    }


//    public static void drawField(int he, int wi) {
//        w = wi;
//        h = he;
//        int cellSize = w < h ? 600 / h : 600 / w;
//        double innerCellSize = (double) cellSize / 3;
//        cellList = new GridCell[h][w];
//        GridPane grid = new GridPane();
//        grid.setGridLinesVisible(true);
//
//
//        RadioButton radioButton1 = new RadioButton();
//        radioButton1.setText("A, B");
//        RadioButton radioButton2 = new RadioButton();
//        radioButton2.setText("[////]");
//        RadioButton radioButton3 = new RadioButton();
//        radioButton3.setText("->");
//
//        RadioButton radioButtonWave = new RadioButton();
//        radioButtonWave.setText("Wave");
//        RadioButton radioButtonThreeFronts = new RadioButton();
//        radioButtonThreeFronts.setText("Three fronts");
//
//
//        ToggleGroup toggleGroupType = new ToggleGroup();
//        radioButtonWave.setToggleGroup(toggleGroupType);
//        radioButtonThreeFronts.setToggleGroup(toggleGroupType);
//        toggleGroupType.selectToggle(radioButtonWave);
//
//
//        ToggleGroup toggleGroup = new ToggleGroup();
//        radioButton1.setToggleGroup(toggleGroup);
//        radioButton2.setToggleGroup(toggleGroup);
//        radioButton3.setToggleGroup(toggleGroup);
//
//        toggleGroup.selectToggle(radioButton3);
//
//        Button buttonNext = new Button();
//        buttonNext.setText("След. шаг");
//        buttonNext.setOnAction(event -> {
//            RadioButton rb = (RadioButton) toggleGroupType.getSelectedToggle();
//            if (rb.equals(radioButtonWave)) {
//                nextFront();
//            } else {
//                threeFronts();
//            }
//        });
//        buttonNext.setMaxWidth(Double.POSITIVE_INFINITY);
//
//
//        Scene scene = new Scene(grid, 600, 700);
//
//        MainApplication.setScene(scene, "Поле");
//        for (int i = 0; i < h; i++) {
//            grid.getRowConstraints().add(new RowConstraints(cellSize));
//            for (int j = 0; j < w; j++) {
//                if (i < 1) {
//                    grid.getColumnConstraints().add(new ColumnConstraints(cellSize));
//                }
//
//                    GridCell gc = new GridCell(i, j, innerCellSize);
//                    cellList[i][j] = gc;
//                    grid.add(gc.getGrid(), j, i);
//
//
//            }
//        }
//        grid.add(buttonNext, 0, h, w, 1);
//        grid.add(radioButton1, 0, h + 1, w / 2, 1);
//        grid.add(radioButton2, 0, h + 2, w / 2, 1);
//        grid.add(radioButton3, 0, h + 3, w / 2, 1);
//
//        grid.add(radioButtonWave, w / 2, h + 1, w / 2, 1);
//        grid.add(radioButtonThreeFronts, w / 2, h + 2, w / 2, 1);
//
//        GridPane.setHalignment(buttonNext, HPos.CENTER);
//        grid.getChildren().forEach(item -> {
//            item.setOnMouseClicked(mouseEvent -> {
//
//
//                RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
//                if (rb.equals(radioButton1)) {
//                    Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
//                    Integer colIndex = GridPane.getColumnIndex(clickedNode);
//                    Integer rowIndex = GridPane.getRowIndex(clickedNode);
//                    if (rowIndex == null && colIndex == null) {
//                        clickedNode = clickedNode.getParent().getParent();
//
//                        colIndex = GridPane.getColumnIndex(clickedNode);
//                        rowIndex = GridPane.getRowIndex(clickedNode);
//                    }
//                    if (rowIndex != null && colIndex != null) {
//                        if (rowIndex < h && colIndex < w) {
//                            if (aLetter == null) {
//                                aLetter = cellList[rowIndex][colIndex];
//                                aLetter.setA();
//                                check = false;
//                            } else if (bLetter == null) {
//                                bLetter = cellList[rowIndex][colIndex];
//                                bLetter.setB();
//                                check = true;
//                            } else if (check) {
//                                check = false;
//                                aLetter.unSetA();
//                                aLetter = cellList[rowIndex][colIndex];
//                                aLetter.setA();
//                                if (bLetter == cellList[rowIndex][colIndex]) {
//                                    bLetter = null;
//                                }
//                            } else {
//                                check = true;
//                                bLetter.unSetB();
//                                bLetter = cellList[rowIndex][colIndex];
//                                bLetter.setB();
//                                if (aLetter == cellList[rowIndex][colIndex]) {
//                                    aLetter = null;
//                                }
//                            }
//
//                        }
//                    }
//                } else if (rb.equals(radioButton2)) {
//                    Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
//                    Integer colIndex = GridPane.getColumnIndex(clickedNode);
//                    Integer rowIndex = GridPane.getRowIndex(clickedNode);
//                    if (rowIndex == null && colIndex == null) {
//                        clickedNode = clickedNode.getParent().getParent();
//
//                        colIndex = GridPane.getColumnIndex(clickedNode);
//                        rowIndex = GridPane.getRowIndex(clickedNode);
//                    }
//                    if (rowIndex != null && colIndex != null) {
//                        if (rowIndex < h && colIndex < w) {
//                            if (cellList[rowIndex][colIndex].isBarrier()) {
//                                cellList[rowIndex][colIndex].unSet();
//                            } else {
//
//                                cellList[rowIndex][colIndex].setBarrier();
//                            }
//                        }
//                    }
//
//                }
//            });
//        });
//
//
//    }


    public static void drawField(int he, int wi) {
        w = wi;
        h = he;
        int cellSize = w < h ? 600 / h : 600 / w;
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);


        RadioButton radioButton1 = new RadioButton();
        radioButton1.setText("A, B");
        RadioButton radioButton2 = new RadioButton();
        radioButton2.setText("[////]");
        RadioButton radioButton3 = new RadioButton();
        radioButton3.setText("->");

        RadioButton radioButtonWave = new RadioButton();
        radioButtonWave.setText("Wave");
        RadioButton radioButtonThreeFronts = new RadioButton();
        radioButtonThreeFronts.setText("Three fronts");


        ToggleGroup toggleGroupType = new ToggleGroup();
        radioButtonWave.setToggleGroup(toggleGroupType);
        radioButtonThreeFronts.setToggleGroup(toggleGroupType);
        toggleGroupType.selectToggle(radioButtonWave);


        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);

        toggleGroup.selectToggle(radioButton3);

        Button buttonNext = new Button();
        buttonNext.setText("След. шаг");
        buttonNext.setOnAction(event -> {
            RadioButton rb = (RadioButton) toggleGroupType.getSelectedToggle();
            if (rb.equals(radioButtonWave)) {
                nextFront();
            } else {
                threeFronts();
            }
        });
        buttonNext.setMaxWidth(Double.POSITIVE_INFINITY);


        Scene scene = new Scene(grid, 600, 700);

        MainApplication.setScene(scene, "Поле");
        for (int i = 0; i < h; i++) {
            grid.getRowConstraints().add(new RowConstraints(cellSize));
            for (int j = 0; j < w; j++) {
                if (i < 1) {
                    grid.getColumnConstraints().add(new ColumnConstraints(cellSize));
                }

                Pane pane = new Pane();
                pane.getChildren().add(new Label());
                pane.setOnMouseClicked(e -> {

                    pane.getChildren();
                    RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
                    if (rb.equals(radioButton1)) {

                        if (aLetter == null) {
                            aLetter = cellList[rowIndex][colIndex];
                            aLetter.setA();
                            check = false;
                        } else if (bLetter == null) {
                            bLetter = cellList[rowIndex][colIndex];
                            bLetter.setB();
                            check = true;
                        } else if (check) {
                            check = false;
                            aLetter.unSetA();
                            aLetter = cellList[rowIndex][colIndex];
                            aLetter.setA();
                            if (bLetter == cellList[rowIndex][colIndex]) {
                                bLetter = null;
                            }
                        } else {
                            check = true;
                            bLetter.unSetB();
                            bLetter = cellList[rowIndex][colIndex];
                            bLetter.setB();
                            if (aLetter == cellList[rowIndex][colIndex]) {
                                aLetter = null;
                            }
                        }


                    } else if (rb.equals(radioButton2)) {
                        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
                        Integer colIndex = GridPane.getColumnIndex(clickedNode);
                        Integer rowIndex = GridPane.getRowIndex(clickedNode);
                        if (rowIndex == null && colIndex == null) {
                            clickedNode = clickedNode.getParent().getParent();

                            colIndex = GridPane.getColumnIndex(clickedNode);
                            rowIndex = GridPane.getRowIndex(clickedNode);
                        }
                        if (rowIndex != null && colIndex != null) {
                            if (rowIndex < h && colIndex < w) {
                                if (cellList[rowIndex][colIndex].isBarrier()) {
                                    cellList[rowIndex][colIndex].unSet();
                                } else {

                                    cellList[rowIndex][colIndex].setBarrier();
                                }
                            }
                        }

                    }


                });
                grid.add(pane, j, i);


            }
        }
        grid.add(buttonNext, 0, h, w, 1);
        grid.add(radioButton1, 0, h + 1, w / 2, 1);
        grid.add(radioButton2, 0, h + 2, w / 2, 1);
        grid.add(radioButton3, 0, h + 3, w / 2, 1);

        grid.add(radioButtonWave, w / 2, h + 1, w / 2, 1);
        grid.add(radioButtonThreeFronts, w / 2, h + 2, w / 2, 1);

        GridPane.setHalignment(buttonNext, HPos.CENTER);


    }
}




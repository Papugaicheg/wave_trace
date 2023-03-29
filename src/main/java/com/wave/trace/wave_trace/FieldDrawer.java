package com.wave.trace.wave_trace;

import com.wave.trace.wave_trace.elements.Direction;
import com.wave.trace.wave_trace.elements.GridCell;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.FileReader;
import java.util.*;

public class FieldDrawer {
    private static GridCell aLetter;
    private static GridCell bLetter;

    private static Map<Integer, GridCell> cellMap = new HashMap<>();
    private static Integer lastCellIndex = 0;
    private static Integer firstFrontIndex = 1;
    private static Integer frontNumber = 1;
    private static int w;
    private static int h;

    private static Set<Integer> weights = new HashSet<>();
    private static Map<Integer, List<GridCell>> listForAllFronts = new HashMap<>();
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

    private static boolean cellCalculationForTracking(int i, int j, int k, int p, Direction direction, boolean isRabin) {
        if (!cellList[k][p].isBarrier() && !cellList[k][p].isA() && !cellList[k][p].isB() && cellList[k][p].getFront() == 0) {
            int front = 0;
            cellList[k][p].setLengthFromA(cellList[i][j].getLengthFromA()+1);
            if (isRabin) {
                front =Math.abs(k - bLetter.getI()) + Math.abs(p - bLetter.getJ()) + cellList[k][p].getLengthFromA();
            } else {
                front = Math.abs(k - bLetter.getI()) + Math.abs(p - bLetter.getJ());
            }

            weights.add(front);

            List<GridCell> list = listForAllFronts.getOrDefault(front, new ArrayList<>());
            list.add(cellList[k][p]);
            listForAllFronts.put(front, list);
            cellList[k][p].setFront(front);
            lastCellIndex++;
            cellList[k][p].setIndex(lastCellIndex);
            cellList[k][p].setDirection(direction);
            cellList[k][p].setPrevCell(cellList[i][j]);
        }
        if (cellList[k][p].isB()) {
            cellList[k][p].setPrevCell(cellList[i][j]);
            reverse(k, p);
            return true;
        } else {
            return false;
        }
    }

    private static boolean buildAroundForTracking(int i, int j, boolean isRabin) {

        if (i - 1 >= 0)
            if (cellCalculationForTracking(i, j, i - 1, j, Direction.BOTTOM, isRabin)) {
                return true;
            }


        if (j + 1 < w)
            if (cellCalculationForTracking(i, j, i, j + 1, Direction.LEFT, isRabin)) {
                return true;
            }


        if (i + 1 < h)
            if (cellCalculationForTracking(i, j, i + 1, j, Direction.UP, isRabin)) {
                return true;
            }

        if (j - 1 >= 0)
            if (cellCalculationForTracking(i, j, i, j - 1, Direction.RIGHT, isRabin)) {
                return true;
            }

        return false;

    }

    private static boolean cellCalculation(int i, int j, int k, int p, Direction direction) {
        if (!cellList[k][p].isBarrier() && !cellList[k][p].isA() && !cellList[k][p].isB() && cellList[k][p].getFront() == 0) {
            cellList[k][p].setFront(frontNumber);
            lastCellIndex++;
            cellList[k][p].setIndex(lastCellIndex);
            cellList[k][p].setDirection(direction);
            cellMap.put(lastCellIndex, cellList[k][p]);
            cellList[k][p].setPrevCell(cellList[i][j]);
        }
        if (cellList[k][p].isB()) {
            cellList[k][p].setPrevCell(cellList[i][j]);
            reverse(k, p);
            return true;
        } else {
            return false;
        }


    }

    private static boolean cellCalculationCoords(int i, int j, int k, int p, Direction direction,Integer index) {
        if (!cellList[k][p].isBarrier() && !cellList[k][p].isA() && !cellList[k][p].isB() && cellList[k][p].getFront() == 0) {
            cellList[k][p].setFront(index);
            lastCellIndex++;
            cellList[k][p].setIndex(lastCellIndex);
            cellList[k][p].setDirection(direction);
            cellMap.put(lastCellIndex, cellList[k][p]);
            cellList[k][p].setPrevCell(cellList[i][j]);
        }
        if (cellList[k][p].isB()) {
            cellList[k][p].setPrevCell(cellList[i][j]);
            reverse(k, p);
            return true;
        } else {
            return false;
        }


    }
    private static boolean buildAroundCoords(int i, int j) {

        if (i - 1 >= 0)
            if (cellCalculationCoords(i, j, i - 1, j, Direction.BOTTOM,3)) {
                return true;
            }


        if (j + 1 < w)
            if (cellCalculationCoords(i, j, i, j + 1, Direction.LEFT,4)) {
                return true;
            }


        if (i + 1 < h)
            if (cellCalculationCoords(i, j, i + 1, j, Direction.UP,1)) {
                return true;
            }

        if (j - 1 >= 0)
            if (cellCalculationCoords(i, j, i, j - 1, Direction.RIGHT,2)) {
                return true;
            }

        return false;

    }
    private static boolean buildAround(int i, int j) {

        if (i - 1 >= 0)
            if (cellCalculation(i, j, i - 1, j, Direction.BOTTOM)) {
                return true;
            }


        if (j + 1 < w)
            if (cellCalculation(i, j, i, j + 1, Direction.LEFT)) {
                return true;
            }


        if (i + 1 < h)
            if (cellCalculation(i, j, i + 1, j, Direction.UP)) {
                return true;
            }

        if (j - 1 >= 0)
            if (cellCalculation(i, j, i, j - 1, Direction.RIGHT)) {
                return true;
            }

        return false;

    }


    private static void threeFronts() {
        nextFront();
        if (frontNumber == 4) {
            frontNumber = 1;
        }
    }

    private static GridCell findMaxCell(List<GridCell> list) {
        GridCell maxIndexOfGridCell = null;
        int maxIndex = 0;

        for (GridCell cell : list) {
            if (!cell.isUsed() && (cell.getIndex() > maxIndex)) {
                maxIndex = cell.getIndex();
                maxIndexOfGridCell = cell;
            }
        }
        return maxIndexOfGridCell;
    }

    private static void trackingTarget(boolean isRabin) {

        if (aLetter != null && bLetter != null) {
            if (lastCellIndex == 0) {
                int i = aLetter.getI();
                int j = aLetter.getJ();
                buildAroundForTracking(i, j,isRabin);
            } else {
                List<GridCell> list;
                GridCell cell = null;
                int count = 0;
                int maxIndex = 0;
                while (true) {
                    if (weights.size() != 0) {
                        int min = Collections.min(weights);
                        list = listForAllFronts.get(min);
                        cell = findMaxCell(list);
                        if (cell == null) {
                            weights.remove(min);
                            listForAllFronts.remove(min);
                        } else {
                            break;
                        }
                    } else
                        break;

                }
                if (cell != null) {
                    int i = cell.getI();
                    int j = cell.getJ();
                    buildAroundForTracking(i, j,isRabin);
                    cell.setUsed(true);
                }

            }

        }


        frontNumber++;

    }




    private static void nextFront() {
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

                }

            }
            frontNumber++;

        }
    }
    private static void nextFrontCoords() {
        if (aLetter != null && bLetter != null) {
            if (lastCellIndex == 0) {
                int i = aLetter.getI();
                int j = aLetter.getJ();
                buildAroundCoords(i, j);
            } else {
                int endOfFront = lastCellIndex;
                System.out.println(endOfFront);
                for (int l = firstFrontIndex; l <= endOfFront; l++) {
                    int i = cellMap.get(l).getI();
                    int j = cellMap.get(l).getJ();
                    if (buildAroundCoords(i, j)) {
                        break;
                    }

                }

            }
            frontNumber++;

        }
    }


    public static void drawField(int he, int wi) {
        w = wi;
        h = he;
        int cellSize = w < h ? 600 / h : 600 / w;
        double innerCellSize = (double) cellSize / 3;
        cellList = new GridCell[h][w];

        GridPane grid = new GridPane();
        GridPane gridButtons = new GridPane();
        grid.setGridLinesVisible(true);


        RadioButton radioButton1 = new RadioButton();
        radioButton1.setText("A, B");
        RadioButton radioButton2 = new RadioButton();
        radioButton2.setText("[////]");
        RadioButton radioButton3 = new RadioButton();
        radioButton3.setText("->");

        RadioButton radioButtonWave = new RadioButton();
        radioButtonWave.setText("Ли");
        RadioButton radioButtonThreeFronts = new RadioButton();
        radioButtonThreeFronts.setText("Веса по модулю 3");
        RadioButton radioButtonTargetTracking = new RadioButton();
        radioButtonTargetTracking.setText("Слежение за целью");

        RadioButton radioButtonRabin = new RadioButton();
        radioButtonRabin.setText("Рабин");

        RadioButton radioButtonWaveCoords = new RadioButton();
        radioButtonWaveCoords.setText("Путевые координаты");


        ToggleGroup toggleGroupType = new ToggleGroup();
        radioButtonWave.setToggleGroup(toggleGroupType);
        radioButtonThreeFronts.setToggleGroup(toggleGroupType);
        radioButtonTargetTracking.setToggleGroup(toggleGroupType);
        radioButtonRabin.setToggleGroup(toggleGroupType);
        toggleGroupType.selectToggle(radioButtonWave);

        radioButtonWaveCoords.setToggleGroup(toggleGroupType);

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
            } else if (rb.equals(radioButtonThreeFronts)) {
                threeFronts();
            } else if (rb.equals(radioButtonRabin)) {
                trackingTarget(true);
            } else if(rb.equals(radioButtonTargetTracking)){
                trackingTarget(false);
            }else if(rb.equals(radioButtonWaveCoords)){
                nextFrontCoords();
            }
        });
        buttonNext.setMaxWidth(Double.POSITIVE_INFINITY);


        VBox vBox = new VBox(2, grid, gridButtons);

        Scene scene = new Scene(vBox, 600, 750);

        MainApplication.setScene(scene, "Поле");
        for (int i = 0; i < h; i++) {
            grid.getRowConstraints().add(new RowConstraints(cellSize));
            for (int j = 0; j < w; j++) {
                if (i < 1) {
                    grid.getColumnConstraints().add(new ColumnConstraints(cellSize));
                }

                GridCell gc = new GridCell(i, j, innerCellSize);
                cellList[i][j] = gc;
                grid.add(gc.getGrid(), j, i);


            }
        }
        grid.add(buttonNext, 0, h, w, 1);
        gridButtons.add(radioButton1, 0, 1, w / 2, 1);
        gridButtons.add(radioButton2, 0, 2, w / 2, 1);
        gridButtons.add(radioButton3, 0, 3, w / 2, 1);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        gridButtons.getColumnConstraints().add(col1);

        gridButtons.add(radioButtonWave, 1, 1, w / 2, 1);
        gridButtons.add(radioButtonThreeFronts, 1, 2, w / 2, 1);
        gridButtons.add(radioButtonRabin, 1, 3, w / 2, 1);
        gridButtons.add(radioButtonTargetTracking, 1, 4, w / 2, 1);
        gridButtons.add(radioButtonWaveCoords, 1, 5, w / 2, 1);
        gridButtons.getColumnConstraints().add(col1);

        GridPane.setHalignment(buttonNext, HPos.CENTER);
        grid.getChildren().forEach(item -> {
            item.setOnMouseClicked(mouseEvent -> {


                RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
                if (rb.equals(radioButton1)) {
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
        });


    }


//    public static void drawField(int he, int wi) {
//        w = wi;
//        h = he;
//        int cellSize = w < h ? 600 / h : 600 / w;
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
//                Pane pane = new Pane();
//                pane.getChildren().add(new Label());
//                pane.setOnMouseClicked(e -> {
//
//                    pane.getChildren();
//                    RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
//                    if (rb.equals(radioButton1)) {
//
//                        if (aLetter == null) {
//                            aLetter = cellList[rowIndex][colIndex];
//                            aLetter.setA();
//                            check = false;
//                        } else if (bLetter == null) {
//                            bLetter = cellList[rowIndex][colIndex];
//                            bLetter.setB();
//                            check = true;
//                        } else if (check) {
//                            check = false;
//                            aLetter.unSetA();
//                            aLetter = cellList[rowIndex][colIndex];
//                            aLetter.setA();
//                            if (bLetter == cellList[rowIndex][colIndex]) {
//                                bLetter = null;
//                            }
//                        } else {
//                            check = true;
//                            bLetter.unSetB();
//                            bLetter = cellList[rowIndex][colIndex];
//                            bLetter.setB();
//                            if (aLetter == cellList[rowIndex][colIndex]) {
//                                aLetter = null;
//                            }
//                        }
//
//
//                    } else if (rb.equals(radioButton2)) {
//                        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
//                        Integer colIndex = GridPane.getColumnIndex(clickedNode);
//                        Integer rowIndex = GridPane.getRowIndex(clickedNode);
//                        if (rowIndex == null && colIndex == null) {
//                            clickedNode = clickedNode.getParent().getParent();
//
//                            colIndex = GridPane.getColumnIndex(clickedNode);
//                            rowIndex = GridPane.getRowIndex(clickedNode);
//                        }
//                        if (rowIndex != null && colIndex != null) {
//                            if (rowIndex < h && colIndex < w) {
//                                if (cellList[rowIndex][colIndex].isBarrier()) {
//                                    cellList[rowIndex][colIndex].unSet();
//                                } else {
//
//                                    cellList[rowIndex][colIndex].setBarrier();
//                                }
//                            }
//                        }
//
//                    }
//
//
//                });
//                grid.add(pane, j, i);
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
//
//
//    }
}




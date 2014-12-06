package sample.map;

import sample.Const;

import java.util.List;

public class Map {

    private Cell[][] cells = new Cell[Const.ROW_COUNT][Const.ROW_SIZE];

    public Map() {
        initMap();
    }

    private void initMap() {

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                List<Cell> neighbors = getNeighbors(i, j);
                cells[i][j].setNeighbors(neighbors);
            }
        }

    }

    private List<Cell> getNeighbors(int i, int j) {
        return null;
    }

}

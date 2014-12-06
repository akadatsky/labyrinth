package sample.map;

import sample.Const;

import java.util.ArrayList;
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

    private List<Cell> getNeighbors(int x, int y) {
        List<Cell> result = new ArrayList<Cell>();

        if (x != 0) {
            result.add(cells[x - 1][y]);
        }
        if (x != Const.ROW_SIZE - 1) {
            result.add(cells[x + 1][y]);
        }

        if (y != 0) {
            result.add(cells[x][y - 1]);
        }
        if (y != Const.ROW_COUNT - 1) {
            result.add(cells[x][y + 1]);
        }

        return result;
    }

}

package sample.map;

import java.util.List;

public class Cell {

    private List<Cell> neighbors;

    public Cell(int x, int y) {

    }

    public void setNeighbors(List<Cell> neighbors) {
        this.neighbors = neighbors;
    }
}

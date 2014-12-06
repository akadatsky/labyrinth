package sample.map;

import javafx.scene.canvas.GraphicsContext;
import sample.Const;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private Cell[][] cells = new Cell[Const.ROW_COUNT][Const.ROW_SIZE];
    private GraphicsContext gc;

    public Map(GraphicsContext gc) {
        this.gc = gc;
        initMap();
    }

    private void initMap() {

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(gc, i, j);
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

    public void draw() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.draw();
            }
        }
    }

    public void reInit() {
        initMap();
    }

    public void selectStart(int x, int y) {
        replaceType(Cell.CellType.START, Cell.CellType.EMPTY);
        Cell cell = getCell(x, y);
        cell.setType(Cell.CellType.START);
    }

    public void selectEnd(int x, int y) {
        replaceType(Cell.CellType.END, Cell.CellType.EMPTY);
        Cell cell = getCell(x, y);
        cell.setType(Cell.CellType.END);
    }

    public void selectEmpty(int x, int y) {
        Cell cell = getCell(x, y);
        cell.setType(Cell.CellType.EMPTY);
    }

    public void selectBlock(int x, int y) {
        Cell cell = getCell(x, y);
        cell.setType(Cell.CellType.BLOCK);
    }

    private Cell getCell(int x, int y) {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.isMyPoint(x, y)) {
                    return cell;
                }
            }
        }
        return null;
    }

    private void replaceType(Cell.CellType from, Cell.CellType to) {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getType() == from) {
                    cell.setType(to);
                }
            }
        }
    }

    public void findPath() {
        Cell startCell = findCellByType(Cell.CellType.START);
        Cell endCell = findCellByType(Cell.CellType.END);
        if (startCell == null || endCell == null) {
            return;
        }

    }

    private Cell findCellByType(Cell.CellType type) {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getType() == type) {
                    return cell;
                }
            }
        }
        return null;
    }

}

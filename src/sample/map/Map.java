package sample.map;

import javafx.scene.canvas.GraphicsContext;
import sample.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Map {

    private Cell[][] cells = new Cell[Const.ROW_COUNT][Const.ROW_SIZE];
    private GraphicsContext gc;

    private List<Cell> openList = new ArrayList<Cell>();
    private List<Cell> closedList = new ArrayList<Cell>();
    private Cell startCell;
    private Cell endCell;

    private boolean showTestedCells = false;

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
        replaceType(Cell.CellType.PATH, Cell.CellType.EMPTY);
        replaceType(Cell.CellType.OPENED, Cell.CellType.EMPTY);
        Cell cell = getCell(x, y);
        cell.setType(Cell.CellType.START);
    }

    public void selectEnd(int x, int y) {
        replaceType(Cell.CellType.END, Cell.CellType.EMPTY);
        replaceType(Cell.CellType.PATH, Cell.CellType.EMPTY);
        replaceType(Cell.CellType.OPENED, Cell.CellType.EMPTY);
        Cell cell = getCell(x, y);
        cell.setType(Cell.CellType.END);
    }

    public void selectEmpty(int x, int y) {
        replaceType(Cell.CellType.PATH, Cell.CellType.EMPTY);
        replaceType(Cell.CellType.OPENED, Cell.CellType.EMPTY);
        Cell cell = getCell(x, y);
        cell.setType(Cell.CellType.EMPTY);
    }

    public void selectBlock(int x, int y) {
        replaceType(Cell.CellType.PATH, Cell.CellType.EMPTY);
        replaceType(Cell.CellType.OPENED, Cell.CellType.EMPTY);
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
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.setG(0);
                cell.setH(0);
                cell.setF(0);
            }
        }
        startCell = findCellByType(Cell.CellType.START);
        endCell = findCellByType(Cell.CellType.END);
        if (startCell == null || endCell == null) {
            return;
        }
        openList.clear();
        closedList.clear();
        openList.add(startCell);
        startCell.setG(0);
        while (!openList.isEmpty()) {
            Cell currentCell = getCellWithMinF();
            if (currentCell == null) {
                // no path
                return;
            }
            if (currentCell.getNeighbors().contains(endCell)) {
                // path founded
                endCell.setParent(currentCell);
                drawPath(showTestedCells);
                return;
            }
            for (Cell nearCell : currentCell.getNeighbors()) {
                if (nearCell.getType() == Cell.CellType.BLOCK) {
                    continue;
                }
                if (closedList.contains(nearCell)) {
                    continue;
                }
                if (!openList.contains(nearCell)) {
                    openList.add(nearCell);
                    nearCell.setParent(currentCell);
                    setupFGH(nearCell);
                } else {
                    double oldG = nearCell.getG();
                    double newG = currentCell.getG() + Const.STEP_PRICE;
                    if (newG < oldG) {
                        nearCell.setParent(currentCell);
                        nearCell.setG(newG);
                        nearCell.setF(newG + nearCell.getH());
                        resortList();
                    }
                }
            }
            openList.remove(currentCell);
            closedList.add(currentCell);
        }
    }

    private void setupFGH(Cell nearCell) {
        Cell parent = nearCell.getParent();
        double g = parent.getG() + Const.STEP_PRICE;
        nearCell.setG(g);
        double h = (Math.abs(endCell.getX() - nearCell.getX()) + Math.abs(endCell.getY() - nearCell.getY())) * Const.STEP_PRICE;
        nearCell.setH(h);
        nearCell.setF(g + h);
    }

    private void drawPath(boolean showTested) {
        if (showTested) {
            showTestedCells();
        }
        Cell currentCell = endCell;
        while (currentCell.getParent() != null) {
            currentCell = currentCell.getParent();
            if (currentCell == startCell) {
                currentCell.setType(Cell.CellType.START);
                return;
            }
            currentCell.setType(Cell.CellType.PATH);
        }
    }

    private void showTestedCells() {
        for (Cell cell : closedList) {
            cell.setType(Cell.CellType.OPENED);
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

    public Cell getCellWithMinF() {
        if (openList.isEmpty()) {
            return null;
        } else {
            resortList();
            return openList.get(0);
        }
    }

    private void resortList() {
        Collections.sort(openList, new Comparator<Cell>() {
            @Override
            public int compare(Cell c1, Cell c2) {
                return Double.compare(c1.getF(), c2.getF());
            }
        });
    }

    public void changeViewMode() {
        replaceType(Cell.CellType.OPENED, Cell.CellType.EMPTY);
        startCell.setType(Cell.CellType.START);
        showTestedCells = !showTestedCells;
        drawPath(showTestedCells);
    }
}

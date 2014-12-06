package sample.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Const;

import java.util.List;
import java.util.Random;

public class Cell {

    public static enum CellType {EMPTY, BLOCK, START, PATH, END, OPENED}

    private static Random rand = new Random();

    private List<Cell> neighbors;
    private CellType type;
    private int x;
    private int y;
    private int size;
    private GraphicsContext gc;

    private int xStart;
    private int xEnd;
    private int yStart;
    private int yEnd;

    private Cell parent;

    // price sum
    private double f;

    // price from start
    private double g;

    // price to finish
    private double h;

    public Cell(GraphicsContext gc, int x, int y) {
        this.x = x;
        this.y = y;
        this.gc = gc;
        size = Const.SELL_SIZE;

        if (rand.nextDouble() < Const.FREE_PERCENT / 100.0) {
            type = CellType.EMPTY;
        } else {
            type = CellType.BLOCK;
        }


        xStart = x * size;
        xEnd = xStart + size;

        yStart = y * size;
        yEnd = yStart + size;
    }

    public void setNeighbors(List<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public List<Cell> getNeighbors() {
        return neighbors;
    }

    public void draw() {
        switch (type) {
            case EMPTY:
                gc.setFill(Color.AQUA);
                break;
            case BLOCK:
                gc.setFill(Color.BLACK);
                break;
            case START:
                gc.setFill(Color.BLUE);
                break;
            case PATH:
                gc.setFill(Color.DARKGREEN);
                break;
            case OPENED:
                gc.setFill(Color.YELLOW);
                break;
            case END:
                gc.setFill(Color.RED);
                break;
        }
        gc.fillRect(xStart, yStart, size, size);
    }

    public boolean isMyPoint(int x, int y) {
        return x >= xStart && x <= xEnd && y >= yStart && y <= yEnd;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public CellType getType() {
        return type;
    }


    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public Cell getParent() {
        return parent;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "f=" + f +
                ", g=" + g +
                ", h=" + h +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

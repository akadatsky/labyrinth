package sample.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Const;

import java.util.List;
import java.util.Random;

public class Cell {

    private static Random rand = new Random();

    private List<Cell> neighbors;
    private boolean open;
    private int x;
    private int y;
    private int size;
    private GraphicsContext gc;

    public Cell(GraphicsContext gc, int x, int y) {
        this.x = x;
        this.y = y;
        this.gc = gc;
        size = Const.SELL_SIZE;
        open = rand.nextDouble() < Const.FREE_PERCENT / 100.0;
    }

    public void setNeighbors(List<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public List<Cell> getNeighbors() {
        return neighbors;
    }

    public void draw() {
        if (open) {
            gc.setFill(Color.AQUA);
        } else {
            gc.setFill(Color.BLACK);
        }
        gc.fillRect(x * size, y * size, size, size);
    }
}

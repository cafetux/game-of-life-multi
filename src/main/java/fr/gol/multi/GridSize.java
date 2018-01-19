package fr.gol.multi;


public class GridSize {

    private int width;
    private int height;

    public GridSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean accept(int x, int y) {
        return x>0 && y>0 && x<=width && y<=height;
    }
}

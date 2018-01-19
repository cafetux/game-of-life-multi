package fr.gol.multi;

import java.util.*;

import static java.util.Optional.ofNullable;

/**
 */
public class Cells {

    private final int width;
    private final int heigth;
    private Map<Position,Cell> livingCells = new HashMap<>();


    public Cells(int maxX, int maxY) {
        this.width = maxX;
        this.heigth=maxY;
    }

    public Cells() {
        this.width = 10;
        this.heigth= 10;
    }

    public long count(){
        return livingCells.size();
    }

    public void add(int x, int y) {
        livingCells.put(new Position(x, y), new Cell());
    }

    public Cells tic(){
        Cells afterTic = new Cells();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < heigth; y++) {

            long nbNeigbours = Arrays.asList(
                    getCell(x + 1, y),
                    getCell(x - 1, y),
                    getCell(x, y + 1),
                    getCell(x, y - 1),
                    getCell(x + 1, y + 1),
                    getCell(x - 1, y - 1),
                    getCell(x + 1, y - 1),
                    getCell(x - 1, y + 1)).stream()
                    .filter(Cell::isLiving)
                    .count();
            if ((getCell(x, y).isLiving() && nbNeigbours == 2) || nbNeigbours == 3) {
                afterTic.add(x, y);
            }
        }
    }
    return afterTic;
    }

    private Cell getCell(int x,int y){
        return ofNullable(livingCells.get(new Position(x, y))).orElse(Cell.DEAD);
    }

    @Override
    public String toString() {
        return "Cells{" +
                "livingCells=\n " + livingCells +
                '}';
    }

    public boolean hasCell(int x, int y) {
        return getCell(x,y).isLiving();
    }
}

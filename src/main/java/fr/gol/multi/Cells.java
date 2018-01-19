package fr.gol.multi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 */
public class Cells {

    private final int width;
    private final int heigth;
    private Set<Position> livingCells = new HashSet<>();


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
        livingCells.add(new Position(x,y));
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
                    .filter(c -> c)
                    .count();
            if ((getCell(x, y) && nbNeigbours == 2) || nbNeigbours == 3) {
                afterTic.add(x, y);
            }
        }
    }
    return afterTic;
    }
    private Boolean getCell(int x,int y){
        return livingCells.contains(new Position(x,y));
    }
    @Override
    public String toString() {
        return "Cells{" +
                "livingCells=\n " + livingCells +
                '}';
    }

    public boolean hasCell(int x, int y) {
        return livingCells.contains(new Position(x,y));
    }
}

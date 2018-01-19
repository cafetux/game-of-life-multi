package fr.gol.multi;

import java.util.*;

import static java.util.Optional.ofNullable;

/**
 */
public class Cells {

    private final GridSize size;
    private Map<Position,Cell> livingCells = new HashMap<>();


    private Cells(int maxX, int maxY) {
        this.size=new GridSize(maxX,maxY);
    }

    private Cells(int squareSize) {
        this.size=new GridSize(squareSize,squareSize);
    }

    private Cells(GridSize size) {
        this.size=size;
    }

    public static Cells square(int size){
        return new Cells(size);
    }

    public static Cells grid(int width,int height){
        return new Cells(width,height);
    }

    public long count(){
        return livingCells.size();
    }

    public void add(int x, int y) {
        if(!size.accept(x,y)){
            throw new IllegalArgumentException("Grid with size of "+size+" not accept position "+new Position(x,y));
        }
        livingCells.put(new Position(x, y), new Cell());
    }

    public Cells tic(){
        Cells afterTic = new Cells(size);
        for (int x = 0; x < size.getWidth(); x++) {
            for (int y = 0; y < size.getHeight(); y++) {

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

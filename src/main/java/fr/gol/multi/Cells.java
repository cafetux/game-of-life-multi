package fr.gol.multi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

/**
 *
 */
public class Cells {
    public static final Cell LIVING_CELL = new Cell();
    private Map<Position,Cell> cells = new HashMap<>();

    public Cells(){

    }

    public long size() {
        return cells.size();
    }

    public void addLivingCell(Position position) {
        cells.put(position, LIVING_CELL);
    }

    public Cell getCell(int x, int y) {
        return ofNullable(cells.get(new Position(x, y))).orElse(Cell.DEAD);
    }


    public List<Cell> neighboursOf(int x, int y) {
        return Arrays.asList(
                getCell(x + 1, y),
                getCell(x - 1, y),
                getCell(x, y + 1),
                getCell(x, y - 1),
                getCell(x + 1, y + 1),
                getCell(x - 1, y - 1),
                getCell(x + 1, y - 1),
                getCell(x - 1, y + 1));
    }

}

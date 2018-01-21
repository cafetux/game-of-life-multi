package fr.gol.multi;

import fr.gol.multi.cell.Cell;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 *
 */
public class Cells {

    private Map<Position,Cell> cells = new HashMap<>();

    public Cells(){

    }

    private Cells(Map<Position, Cell> cells) {
        this.cells.putAll(cells);
    }

    public long size() {
        return cells.size();
    }

    public void addLivingCell(Position position, Player player) {
        cells.put(position, new Cell(player));
    }

    public Cell getCell(int x, int y) {
        return ofNullable(cells.get(new Position(x, y))).orElse(Cell.DEAD);
    }


    public List<Cell> neighboursOf(int x, int y) {
        List<Cell> neighbours = new ArrayList<>();
        neighbours.addAll(Arrays.asList(
                getCell(x + 1, y),
                getCell(x - 1, y),
                getCell(x, y + 1),
                getCell(x, y - 1),
                getCell(x + 1, y + 1),
                getCell(x - 1, y - 1),
                getCell(x + 1, y - 1),
                getCell(x - 1, y + 1)));
        return neighbours;
    }

    public Cells filter(Predicate<Cell> filter){
        return new Cells(this.cells.entrySet().stream().filter(e->filter.test(e.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

}

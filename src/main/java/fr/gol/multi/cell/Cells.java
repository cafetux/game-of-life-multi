package fr.gol.multi.cell;

import fr.gol.multi.Position;
import fr.gol.multi.player.Player;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

/**
 *
 */
public class Cells {

    public static final java.util.stream.Collector<Map.Entry<Position, Cell>, ?, Map<Position, Cell>> TO_MAP = Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
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
        return maybeCell(x, y).orElse(Cell.DEAD);
    }

    private Optional<Cell> maybeCell(int x, int y) {
        return ofNullable(cells.get(new Position(x, y)));
    }


    public List<Cell> neighboursOf(int x, int y) {
        List<Cell> neighbours = new ArrayList<>();
        maybeCell(x + 1, y).ifPresent(neighbours::add);
        maybeCell(x - 1, y).ifPresent(neighbours::add);
        maybeCell(x, y + 1).ifPresent(neighbours::add);
        maybeCell(x, y - 1).ifPresent(neighbours::add);
        maybeCell(x + 1, y + 1).ifPresent(neighbours::add);
        maybeCell(x + 1, y - 1).ifPresent(neighbours::add);
        maybeCell(x - 1, y + 1).ifPresent(neighbours::add);
        maybeCell(x - 1, y - 1).ifPresent(neighbours::add);
        return neighbours;
    }

    public Cells filter(Predicate<Cell> filter){
        return new Cells($(filter).collect(TO_MAP));
    }

    private Stream<Map.Entry<Position, Cell>> $(Predicate<Cell> filter) {
        return this.cells.entrySet().stream().filter(e->filter.test(e.getValue()));
    }

}

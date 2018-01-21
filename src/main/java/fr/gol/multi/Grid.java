package fr.gol.multi;

import fr.gol.multi.cell.Cell;
import fr.gol.multi.cell.Cells;
import fr.gol.multi.cell.predicate.OwnedByPlayer;
import fr.gol.multi.player.Player;
import fr.gol.multi.player.function.PlayerCounter;
import fr.gol.multi.player.function.PlayerCounterComnbiner;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 */
public class Grid {

    public static final PlayerCounterComnbiner COMBINER = new PlayerCounterComnbiner();
    public static final PlayerCounter ACCUMULATOR = new PlayerCounter();

    private final GridSize size;
    private Cells livingCells = new Cells();

    private Grid(int maxX, int maxY) {
        this.size=new GridSize(maxX,maxY);
    }

    private Grid(int squareSize) {
        this.size=new GridSize(squareSize,squareSize);
    }

    private Grid(GridSize size) {
        this.size=size;
    }

    public static Grid square(int size){
        return new Grid(size);
    }

    public static Grid grid(int width,int height){
        return new Grid(width,height);
    }

    public long countLivingCells(){
        return livingCells.size();
    }

    public void add(int x, int y, Player player) {
        if(!size.accept(x,y)){
            throw new IllegalArgumentException("Grid with size of "+size+" not accept position "+new Position(x,y));
        }
        livingCells.addLivingCell(new Position(x, y),player);
    }

    public Grid tic(){
        Grid afterTic = new Grid(size);
        for (int x = 0; x < size.getWidth(); x++) {
            for (int y = 0; y < size.getHeight(); y++) {
                List<Cell> neighbours = livingCells.neighboursOf(x, y);
                long nbNeigbours = neighbours.size();
                if ((hasCell(x, y) && nbNeigbours == 2) || nbNeigbours == 3) {
                    Player owner = currentCellPlayer(x, y).orElse(selectPlayerFromNeighbours(neighbours));
                    afterTic.add(x, y, owner);
                }
            }
        }
        return afterTic;
    }

    private Optional<Player> currentCellPlayer(int x, int y) {
        return ofNullable(livingCells.getCell(x, y))
                .map(Cell::getOwner);
    }

    private Player selectPlayerFromNeighbours(List<Cell> neighbours) {
        Map<Player, Long> count = groupByPlayer(neighbours);
        List<Map.Entry<Player, Long>> sorted = sort(count);
        if(onlyOneNeighbourPlayer(sorted) || onePlayerHaveMoreNeignboursThanOthers(sorted))
        {
           return sorted.get(0).getKey();
        }
        return Player.NEUTRAL;
    }

    private boolean onePlayerHaveMoreNeignboursThanOthers(List<Map.Entry<Player, Long>> sorted) {
        return !Objects.equals(sorted.get(0).getValue(), sorted.get(1).getValue());
    }

    private boolean onlyOneNeighbourPlayer(List<Map.Entry<Player, Long>> sorted) {
        return sorted.size()==1;
    }

    private List<Map.Entry<Player, Long>> sort(Map<Player, Long> count) {
        return count.entrySet().stream().sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue())).collect(Collectors.toList());
    }

    private Map<Player, Long> groupByPlayer(List<Cell> neighbours) {
        Map<Player, Long> identity = new HashMap<>();
        return neighbours.stream().reduce(identity, ACCUMULATOR, COMBINER);
    }

    @Override
    public String toString() {
        return "Grid{" +
                "size=\n " + size +
                "livingCells=\n " + livingCells +
                '}';
    }

    public boolean hasCell(int x, int y) {
        return livingCells.getCell(x,y).isLiving();
    }

    public long countLivingCells(String nickname) {
        return livingCells.filter(new OwnedByPlayer(nickname)).size();
    }

    public Player ownerOfCell(int x, int y) {
        return livingCells.getCell(x,y).getOwner();
    }
}

package fr.gol.multi;

import fr.gol.multi.cell.Cell;
import fr.gol.multi.cell.predicate.OwnedByPlayer;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 */
public class Grid {

    private final GridSize size;
    private Cells livingCells = new Cells();
    public static final BinaryOperator<Map<Player, Long>> PLAYER_PRESENCE_COMBINER = new BinaryOperator<Map<Player, Long>>() {
        @Override
        public Map<Player, Long> apply(Map<Player, Long> playerLongMap, Map<Player, Long> playerLongMap2) {
            Map<Player, Long> result = new HashMap<>();
            Set<Player> keys = playerLongMap.keySet();
            keys.addAll(playerLongMap2.keySet());

            for (Player player : keys) {
                Long count = 0l;
                if (playerLongMap2.containsKey(player)) {
                    count += playerLongMap.get(player);
                }
                if (playerLongMap2.containsKey(player)) {
                    count += playerLongMap2.get(player);
                }
                result.put(player, count);
            }
            return result;
        }
    };
    public static final BiFunction<Map<Player, Long>, ? super Cell, Map<Player, Long>> PLAYER_PRESENCE_ACC = new BiFunction<Map<Player, Long>, Cell, Map<Player, Long>>() {
        @Override
        public Map<Player, Long> apply(Map<Player, Long> playerLongMap, Cell cell) {
            Long count = 1l;
            if (playerLongMap.containsKey(cell.getOwner())) {
                count += playerLongMap.get(cell.getOwner());
            }
            playerLongMap.put(cell.getOwner(), count);
            return playerLongMap;
        }
    };


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
                    Player owner = livingCells.getCell(x, y).getOwner();
                    if(owner==null){
                        Map<Player, Long> count = groupByPlayer(neighbours);
                        List<Map.Entry<Player, Long>> sorted = sort(count);
                        if(sorted.size()==1 || !Objects.equals(sorted.get(0).getValue(), sorted.get(1).getValue()))
                        {
                            owner = sorted.get(0).getKey();
                        } else {
                            owner = Player.NEUTRAL;
                        }
                    }
                    afterTic.add(x, y, owner);
                }
            }
        }
        return afterTic;
    }

    private List<Map.Entry<Player, Long>> sort(Map<Player, Long> count) {
        return count.entrySet().stream().sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue())).collect(Collectors.toList());
    }

    private Map<Player, Long> groupByPlayer(List<Cell> neighbours) {
        Map<Player, Long> identity = new HashMap<>();
        return neighbours.stream().reduce(identity, PLAYER_PRESENCE_ACC, PLAYER_PRESENCE_COMBINER);
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

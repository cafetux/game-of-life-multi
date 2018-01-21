package fr.gol.multi.cell;

import fr.gol.multi.Player;

/**
 *
 */
public class Cell {

    public static final Cell DEAD = new Cell(null){
        public boolean isLiving(){
            return false;
        }
    };

    private final Player owner;

    public Cell(Player player) {
        this.owner=player;
    }

    public boolean isLiving(){
        return true;
    }

    public Player getOwner() {
        return owner;
    }
}

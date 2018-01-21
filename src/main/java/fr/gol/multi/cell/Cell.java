package fr.gol.multi.cell;

import fr.gol.multi.player.Owned;
import fr.gol.multi.player.Player;

/**
 *
 */
public class Cell implements Owned {

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

    @Override
    public Player getOwner() {
        return owner;
    }
}

package fr.gol.multi;

/**
 *
 */
public class Cell {

    public static final Cell DEAD = new Cell(){
        public boolean isLiving(){
            return false;
        }
    };

    public boolean isLiving(){
        return true;
    }
}

package fr.gol.multi;

import fr.gol.multi.cell.predicate.OwnedByPlayer;

/**
 */
public class Grid {

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

            long nbNeigbours = livingCells.neighboursOf(x, y).size();
            if ((hasCell(x, y) && nbNeigbours == 2) || nbNeigbours == 3) {
                Player owner = livingCells.getCell(x, y).getOwner();
                if(owner==null){
                    owner = livingCells.neighboursOf(x,y).get(0).getOwner();
                }
                afterTic.add(x, y, owner);
            }
        }
    }
    return afterTic;
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

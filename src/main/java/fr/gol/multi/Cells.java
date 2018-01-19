package fr.gol.multi;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 */
public class Cells {

    private Boolean[][] livingCells = new Boolean[5][5] ;

    public long count(){
        return Arrays.stream(livingCells).flatMap(Arrays::stream).filter(c->c== TRUE).count();
    }

    public void add(int x, int y) {
        livingCells[x][y]= TRUE;
    }

    public Cells tic(){
        Cells afterTic = new Cells();
        for(int x = 0; x <livingCells.length; x++) {
            for (int y = 0; y < livingCells[x].length; y++) {
                long nbNeigbours = Arrays.asList(
                        getCell(x+1,y),
                        getCell(x-1,y),
                        getCell(x,y+1),
                        getCell(x,y-1),
                        getCell(x+1,y+1),
                        getCell(x-1,y-1),
                        getCell(x+1,y-1),
                        getCell(x-1,y+1)).stream()
                        .filter(c -> c)
                        .count();
                if( (getCell(x,y) && nbNeigbours == 2) || nbNeigbours == 3){
                    afterTic.add(x,y);
                }
            }
        }
        return afterTic;
    }
    private Boolean getCell(int x,int y){
        try {
            return Optional.ofNullable(livingCells[x][y]).orElse(FALSE);
        }catch(ArrayIndexOutOfBoundsException e){
            return FALSE;
        }
    }
    @Override
    public String toString() {
        return "Cells{" +
                "livingCells=\n " + Arrays.stream(livingCells).map(Arrays::toString).map(x->x.concat("\n")).collect(Collectors.toList()) +
                '}';
    }

    public boolean hasCell(int x, int y) {
        return (livingCells[x][y] == TRUE) || FALSE;
    }
}

package fr.gol.multi;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class CellsTest {

    @Test
    public void should_only_return_neighbours_that_are_living(){
        Cells cells = new Cells();
        cells.addLivingCell(new Position(3,3),new Player("hahaha"));
        cells.addLivingCell(new Position(3,2),new Player("hahaha"));
        cells.addLivingCell(new Position(2,1),new Player("hahaha"));
        assertThat(cells.neighboursOf(2,2)).hasSize(3);
    }


    @Test
    public void should_ignore_itself_on_neighbours(){
        Cells cells = new Cells();
        cells.addLivingCell(new Position(3,3),new Player("hahaha"));
        assertThat(cells.neighboursOf(3,3)).isEmpty();
    }


}
package fr.gol.multi;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 *
 */
public class MutiplayerTest {


    public static final Player CAFETUX = new Player("cafetux");

    @Test
    public void should_link_cell_to_player(){
        Grid grid = Grid.square(10);
        grid.add(3, 3, CAFETUX);
        Assertions.assertThat(grid.countLivingCells("cafetux")).isEqualTo(1);
        Assertions.assertThat(grid.countLivingCells("jean")).isEqualTo(0);
    }
}

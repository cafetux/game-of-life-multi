package fr.gol.multi;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class MutiplayerTest {


    public static final Player CAFETUX = new Player("cafetux");
    public static final Player GEO = new Player("GEO");

    @Test
    public void should_link_cell_to_player(){
        Grid grid = Grid.square(10);
        grid.add(3, 3, CAFETUX);
        assertThat(grid.countLivingCells("cafetux")).isEqualTo(1);
        assertThat(grid.countLivingCells("jean")).isEqualTo(0);
    }


    /**
     *      [_][_][_][_][_]       [_][_][_][_][_]
     *      [_][_][C][_][_]       [_][_][_][_][_]
     *      [_][_][C][_][_]  =>   [_][C][C][C][_]
     *      [_][_][C][_][_]       [_][_][_][_][_]
     */
    @Test
    public void should_keep_player_on_new_living_cell(){
        Grid grid = Grid.square(10);
        grid.add(3, 3, CAFETUX);
        grid.add(3, 2, CAFETUX);
        grid.add(3, 1, CAFETUX);
        grid=grid.tic();

        assertThat(grid.ownerOfCell(3, 2)).as("still living cell should keep owner").isEqualTo(CAFETUX);

        assertThat(grid.ownerOfCell(2, 2)).as("new living cell should have neigbours owner").isEqualTo(CAFETUX);
        assertThat(grid.ownerOfCell(4, 2)).as("new living cell shouold have neigbours owner").isEqualTo(CAFETUX);
    }




}

package fr.gol.multi;

import org.assertj.core.api.Condition;
import org.junit.Test;

import static fr.gol.multi.MutiplayerTest.CAFETUX;
import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class GolTest {


    public static final Condition<Grid> NO_CELLS = new Condition<>(cs -> cs.countLivingCells() == 0, "should be empty");
    public static final Condition<Grid> ONE_CELL = new Condition<>(cs -> cs.countLivingCells() == 1, "should have one cell");
    public static final Condition<Grid> THREE_CELLS = new Condition<>(cs -> cs.countLivingCells() == 3, "should have three cells");
    private Grid result;

    @Test
    public void should_add_living_cell_to_system(){
        Grid grid = Grid.square(10);
        addCell(grid, 3, 3);
        assertThat(grid).has(ONE_CELL);
    }

    private void addCell(Grid grid, int x, int y) {
        grid.add(x, y, CAFETUX);
    }

    @Test
    public void should_add_some_cells_to_system(){
        Grid grid = Grid.square(10);
        addCell(grid, 3, 3);
        addCell(grid, 2, 1);
        addCell(grid, 3, 1);

        assertThat(grid).has(THREE_CELLS);   }

    @Test
    public void should_not_born_when_no_cells(){
        Grid grid = Grid.square(10);
        grid =tic(grid);
        assertThat(grid).has(NO_CELLS);
    }

    @Test
    public void should_die_when_only_one_cell(){
        Grid grid = Grid.square(10);
        addCell(grid, 3, 3);
        grid =tic(grid);
        assertThat(grid).has(NO_CELLS);
    }

    /**
     *   [_][_][_][_]
     *   [_][_][O][_]
     *   [_][O][O][_]
     *   [_][_][_][_]
     */
    @Test
    public void should_born_when_three_neigbours(){
        Grid grid = Grid.square(10);
        addCell(grid, 3, 3);
        addCell(grid, 2, 2);
        addCell(grid, 3, 2);
        assertThat(tic(grid).hasCell(2,3)).as(grid.toString() + "\n\n" + tic(grid).toString() + "\n should have living cell on [2,3]").isTrue();
    }

    /**
     *   [_][_][_][_]
     *   [_][_][O][_]
     *   [_][O][_][_]
     *   [_][_][_][_]
     */
    @Test
    public void should_not_born_when_two_neigbours(){
        Grid grid = Grid.square(10);
        addCell(grid, 3, 3);
        addCell(grid, 2, 2);
        assertThat(tic(grid).hasCell(3,2)).as(grid.toString() + "\n\n" + tic(grid).toString() + "\n should have no cell on [3,2]").isFalse();
        assertThat(tic(grid).hasCell(2,3)).as(grid.toString() + "\n\n" + tic(grid).toString() + "\n should have no cell on [2,3]").isFalse();
    }

    /**
     *   [_][_][_][_]
     *   [_][O][O][_]
     *   [_][O][O][_]
     *   [_][_][_][_]
     */
    @Test
    public void should_stance_when_three_neigbours(){
        Grid grid = Grid.square(10);
        addCell(grid, 2, 3);
        addCell(grid, 3, 3);
        addCell(grid, 2, 2);
        addCell(grid, 3, 2);
        result = tic(grid);
        assertThat(result.hasCell(2, 3)).as(grid.toString()+"\n\n"+ result.toString() + "\n should have living cell on [2,3]").isTrue();
        assertThat(result.hasCell(3, 3)).as(grid.toString()+"\n\n"+ result.toString() + "\n should have living cell on [2,3]").isTrue();
        assertThat(result.hasCell(2, 2)).as(grid.toString()+"\n\n"+ result.toString() + "\n should have living cell on [2,3]").isTrue();
        assertThat(result.hasCell(3, 2)).as(grid.toString()+"\n\n"+ result.toString() + "\n should have living cell on [2,3]").isTrue();
    }

    /**
     *   [_][_][_][_]       [_][_][_][_]
     *   [_][_][_][_]       [_][_][O][_]
     *   [_][O][O][O]  ==>  [_][_][O][_]
     *   [_][_][_][_]       [_][_][O][_]
     */
    @Test
    public void should_stance_when_two_neigbours(){
        Grid grid = Grid.square(10);
        addCell(grid, 2, 2);
        addCell(grid, 3, 2);
        addCell(grid, 4, 2);

        result = tic(grid);

        assertThat(result.hasCell(3, 2)).as(grid.toString()+"\n\n"+ result.toString() + "\n should have living cell on [2,3]").isTrue();
    }

    /**
     *   [_][_][_][_]       [_][_][_][_]
     *   [_][_][O][O]       [_][_][O][O]
     *   [_][_][O][_]  ==>  [_][_][_][_]
     *   [_][O][O][_]       [_][O][O][_]
     */
    @Test
    public void should_die_when_too_many_neigbours(){
        Grid grid = Grid.square(10);
        addCell(grid, 2, 1);
        addCell(grid, 3, 1);
        addCell(grid, 3, 2);
        addCell(grid, 3, 3);
        addCell(grid, 4, 3);

        result = tic(grid);

        assertThat(result.hasCell(3, 2)).as(grid.toString()+"\n\n"+ result.toString() + "\n should not have living cell on [2,3]").isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_cannot_add_cell_out_of_the_grid(){
        Grid grid = Grid.square(3);
        addCell(grid, 4, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_reject_0_as_valid_coordinate(){
        Grid grid = Grid.square(3);
        addCell(grid, 0, 0);
    }

    private Grid tic(Grid grid) {
        return grid.tic();
    }

}

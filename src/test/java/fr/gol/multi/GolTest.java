package fr.gol.multi;

import org.assertj.core.api.Condition;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class GolTest {


    public static final Condition<Cells> NO_CELLS = new Condition<>(cs -> cs.count() == 0, "should be empty");
    public static final Condition<Cells> ONE_CELL = new Condition<>(cs -> cs.count() == 1, "should have one cell");
    public static final Condition<Cells> THREE_CELLS = new Condition<>(cs -> cs.count() == 3, "should have three cells");
    private Cells result;

    @Test
    public void should_add_living_cell_to_system(){
        Cells cells = Cells.square(10);
        cells.add(3,3);
        assertThat(cells).has(ONE_CELL);
    }

    @Test
    public void should_add_some_cells_to_system(){
        Cells cells = Cells.square(10);
        cells.add(3,3);
        cells.add(2,1);
        cells.add(3,1);

        assertThat(cells).has(THREE_CELLS);   }

    @Test
    public void should_not_born_when_no_cells(){
        Cells cells = Cells.square(10);
        cells=tic(cells);
        assertThat(cells).has(NO_CELLS);
    }

    @Test
    public void should_die_when_only_one_cell(){
        Cells cells = Cells.square(10);
        cells.add(3,3);
        cells=tic(cells);
        assertThat(cells).has(NO_CELLS);
    }

    /**
     *   [_][_][_][_]
     *   [_][_][O][_]
     *   [_][O][O][_]
     *   [_][_][_][_]
     */
    @Test
    public void should_born_when_three_neigbours(){
        Cells cells = Cells.square(10);
        cells.add(3,3);
        cells.add(2,2);
        cells.add(3,2);
        assertThat(tic(cells).hasCell(2,3)).as(cells.toString() + "\n\n" + tic(cells).toString() + "\n should have living cell on [2,3]").isTrue();
    }

    /**
     *   [_][_][_][_]
     *   [_][_][O][_]
     *   [_][O][_][_]
     *   [_][_][_][_]
     */
    @Test
    public void should_not_born_when_two_neigbours(){
        Cells cells = Cells.square(10);
        cells.add(3,3);
        cells.add(2,2);
        assertThat(tic(cells).hasCell(3,2)).as(cells.toString() + "\n\n" + tic(cells).toString() + "\n should have no cell on [3,2]").isFalse();
        assertThat(tic(cells).hasCell(2,3)).as(cells.toString() + "\n\n" + tic(cells).toString() + "\n should have no cell on [2,3]").isFalse();
    }

    /**
     *   [_][_][_][_]
     *   [_][O][O][_]
     *   [_][O][O][_]
     *   [_][_][_][_]
     */
    @Test
    public void should_stance_when_three_neigbours(){
        Cells cells = Cells.square(10);
        cells.add(2,3);
        cells.add(3,3);
        cells.add(2,2);
        cells.add(3,2);
        result = tic(cells);
        assertThat(result.hasCell(2, 3)).as(cells.toString()+"\n\n"+ result.toString() + "\n should have living cell on [2,3]").isTrue();
        assertThat(result.hasCell(3, 3)).as(cells.toString()+"\n\n"+ result.toString() + "\n should have living cell on [2,3]").isTrue();
        assertThat(result.hasCell(2, 2)).as(cells.toString()+"\n\n"+ result.toString() + "\n should have living cell on [2,3]").isTrue();
        assertThat(result.hasCell(3, 2)).as(cells.toString()+"\n\n"+ result.toString() + "\n should have living cell on [2,3]").isTrue();
    }

    /**
     *   [_][_][_][_]       [_][_][_][_]
     *   [_][_][_][_]       [_][_][O][_]
     *   [_][O][O][O]  ==>  [_][_][O][_]
     *   [_][_][_][_]       [_][_][O][_]
     */
    @Test
    public void should_stance_when_two_neigbours(){
        Cells cells = Cells.square(10);
        cells.add(2,2);
        cells.add(3,2);
        cells.add(4,2);

        result = tic(cells);

        assertThat(result.hasCell(3, 2)).as(cells.toString()+"\n\n"+ result.toString() + "\n should have living cell on [2,3]").isTrue();
    }

    /**
     *   [_][_][_][_]       [_][_][_][_]
     *   [_][_][O][O]       [_][_][O][O]
     *   [_][_][O][_]  ==>  [_][_][_][_]
     *   [_][O][O][_]       [_][O][O][_]
     */
    @Test
    public void should_die_when_too_many_neigbours(){
        Cells cells = Cells.square(10);
        cells.add(2,1);
        cells.add(3,1);
        cells.add(3,2);
        cells.add(3,3);
        cells.add(4,3);

        result = tic(cells);

        assertThat(result.hasCell(3, 2)).as(cells.toString()+"\n\n"+ result.toString() + "\n should not have living cell on [2,3]").isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_cannot_add_cell_out_of_the_grid(){
        Cells cells = Cells.square(3);
        cells.add(4,4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_reject_0_as_valid_coordinate(){
        Cells cells = Cells.square(3);
        cells.add(0,0);
    }

    private Cells tic(Cells cells) {
        return cells.tic();
    }

}

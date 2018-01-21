package fr.gol.multi.cell.predicate;

import fr.gol.multi.cell.Cell;

import java.util.function.Predicate;

/**
 *
 */
public class OwnedByPlayer implements Predicate<Cell> {

    private final String expectedNickName;

    public OwnedByPlayer(String expectedNickName) {
        this.expectedNickName = expectedNickName;
    }


    @Override
    public boolean test(Cell cell) {
        return cell.getOwner().getNickname().equalsIgnoreCase(this.expectedNickName);
    }
}

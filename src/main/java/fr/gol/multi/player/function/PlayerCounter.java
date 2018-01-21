package fr.gol.multi.player.function;

import fr.gol.multi.player.Owned;
import fr.gol.multi.player.Player;

import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 */
public class PlayerCounter implements BiFunction<Map<Player, Long>, Owned, Map<Player, Long>> {

    @Override
    public Map<Player, Long> apply(Map<Player, Long> playerLongMap, Owned o) {
        Long count = 1l;
        if (playerLongMap.containsKey(o.getOwner())) {
            count += playerLongMap.get(o.getOwner());
        }
        playerLongMap.put(o.getOwner(), count);
        return playerLongMap;
    }

}

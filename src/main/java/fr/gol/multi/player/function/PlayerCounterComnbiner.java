package fr.gol.multi.player.function;

import fr.gol.multi.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;

/**
 *
 */
public class PlayerCounterComnbiner implements BinaryOperator<Map<Player, Long>> {

    @Override
    public Map<Player, Long> apply(Map<Player, Long> playerLongMap, Map<Player, Long> playerLongMap2) {
        Map<Player, Long> result = new HashMap<>();
        Set<Player> keys = playerLongMap.keySet();
        keys.addAll(playerLongMap2.keySet());

        for (Player player : keys) {
            Long count = 0l;
            if (playerLongMap2.containsKey(player)) {
                count += playerLongMap.get(player);
            }
            if (playerLongMap2.containsKey(player)) {
                count += playerLongMap2.get(player);
            }
            result.put(player, count);
        }
        return result;
    }
}

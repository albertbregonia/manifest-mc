package kanin.manifest.api;

import org.bukkit.entity.Player;

public class Movement {
    
    public static void boostForward(Player player, int magnitude) {
        player.getLocation().getDirection().multiply(magnitude);
    }

}

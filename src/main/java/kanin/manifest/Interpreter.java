package kanin.manifest;

import java.util.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * import org.bukkit.scheduler.BukkitRunnable;
 * To do:
 * minecraft git
 * code parsing
 */
public class Interpreter implements Listener {

    private Plugin plugin;
    private HashMap<UUID, Inventory> scripts = new HashMap<>();

    public Interpreter(Plugin plugin) {
        this.plugin = plugin;
        System.out.println("Enabled Interpreter!");
    }

    public static String getCardinal(float yaw) {
        if (yaw >= 315 || yaw < 45) 
            return "south";
        else if (yaw < 135)
            return "west";
        else if (yaw < 225) 
            return "north";
        else 
            return "east";
    }

    public boolean nearbyBlock(Location location, Material type, int radius) {
        for(int x=-radius; x<=radius; x++)
            for(int y=-radius; y<=radius; y++)
                for(int z=-radius; z<=radius; z++)
                    if(location.add(x, y, z).getBlock().getType() == Material.LECTERN)
                        return true;
        return false;
    }

    @EventHandler
    public void playerJoinHandler(PlayerJoinEvent e) { giveInterpreter(e.getPlayer()); }

    @EventHandler
    public void playerRespawnHandler(PlayerRespawnEvent e) { giveInterpreter(e.getPlayer()); }

    public void giveInterpreter(Player p) {
        ItemStack interpreter = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta meta = interpreter.getItemMeta();
        meta.setLore(Arrays.asList(new String[]{"Run/View your Programs"}));
        interpreter.setItemMeta(meta);
        scripts.put(p.getUniqueId(), Bukkit.createInventory(p, 54, p.getName()));
        p.getInventory().setItem(8, interpreter);
    }

    @EventHandler
    public void openInterpreter(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if(item == null || item.getType() != Material.REDSTONE_TORCH)
            return;
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasLore() || !meta.getLore().get(0).equalsIgnoreCase("Run/View your Programs") || 
           !scripts.containsKey(p.getUniqueId()))
            return;
        e.setCancelled(true);
        p.openInventory(scripts.get(p.getUniqueId()));
    }
}

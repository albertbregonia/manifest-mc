package kanin.manifest;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new Interpreter(this), this);
        System.out.println("Manifest Handlers Fully Initialized!");
    }

    @Override
    public void onDisable() {
        
    }
}

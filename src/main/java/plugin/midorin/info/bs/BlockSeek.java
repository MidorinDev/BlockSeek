package plugin.midorin.info.bs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.midorin.info.bs.commands.Main;
import plugin.midorin.info.bs.commands.Mode;

import java.util.ArrayList;
import java.util.List;

public final class BlockSeek extends JavaPlugin
{
    public static JavaPlugin plugin;
    public static List<Player> players = new ArrayList<Player>();

    @Override
    public void onEnable()
    {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);

        getCommand("mode").setExecutor(new Mode());
        getCommand("blockseek").setExecutor(new Main());

        Scoreboards.setup();
        Items.setup();
        Mode.gui.setItem(20, Items.milk_bucket);
        Mode.gui.setItem(22, Items.water_bucket);
        Mode.gui.setItem(24, Items.lava_bucket);
    }

    @Override
    public void onDisable()
    {
        Scoreboards.reset();
    }

    public static JavaPlugin getPlugin()
    {
        return plugin;
    }
}

package me.thebmanswan541.SurvivalGames;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.thebmanswan541.SurvivalGames.command.CommandManager;
import me.thebmanswan541.SurvivalGames.exceptions.ArenaNotFoundException;
import me.thebmanswan541.SurvivalGames.listeners.KitSelector;
import me.thebmanswan541.SurvivalGames.listeners.MoveListener;
import me.thebmanswan541.SurvivalGames.listeners.StartingListener;
import me.thebmanswan541.SurvivalGames.managers.ArenaManager;
import me.thebmanswan541.SurvivalGames.util.Arena;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class SurvivalGames extends JavaPlugin {

    public static String tag = ChatColor.GRAY+"["+ChatColor.RED+"SG"+ChatColor.GRAY+"]: ";
    public static Arena arena;

    public void onEnable() {
        ArenaManager.getInstance().setup();
        if (ArenaManager.getInstance().getArenas().size() > 0) {
            arena = ArenaManager.getInstance().getActiveArena();
        }
        getCommand("sg").setExecutor(new CommandManager());
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new StartingListener(), this);
        pm.registerEvents(new MoveListener(), this);
        pm.registerEvents(new KitSelector(), this);
    }

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("SurvivalGames");
    }

    public static void saveLocation(Location location, ConfigurationSection section) {
        section.set("world", location.getWorld().getName());
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("pitch", location.getPitch());
        section.set("yaw", location.getYaw());
    }

    public static Location parseLocation(ConfigurationSection location) throws ArenaNotFoundException {
        World world = Bukkit.getWorld(location.getString("world"));
        double x = location.getDouble("x");
        double y = location.getDouble("y");
        double z = location.getDouble("z");
        float pitch = (float) location.getDouble("pitch");
        float yaw = (float) location.getDouble("yaw");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static WorldEditPlugin getWorldEdit() {
        return (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
    }
}

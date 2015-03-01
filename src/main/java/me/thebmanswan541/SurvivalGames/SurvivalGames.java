package me.thebmanswan541.SurvivalGames;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.thebmanswan541.SurvivalGames.command.CommandManager;
import me.thebmanswan541.SurvivalGames.listeners.StartingListener;
import me.thebmanswan541.SurvivalGames.managers.ArenaManager;
import me.thebmanswan541.SurvivalGames.util.Arena;
import me.thebmanswan541.SurvivalGames.util.Countdown;
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
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class SurvivalGames extends JavaPlugin {

    public static String tag = ChatColor.GRAY+"["+ChatColor.RED+"SG"+ChatColor.GRAY+"]: ";
    public static Arena arena;
    private static int countdownID;

    public void onEnable() {
        ArenaManager.getInstance().setup();
        arena = ArenaManager.getInstance().getActiveArena();
        getCommand("sg").setExecutor(new CommandManager());
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new StartingListener(), this);
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

    public static Location parseLocation(ConfigurationSection location) {
        World world = Bukkit.getWorld(location.getString("world"));
        double x = location.getDouble("x");
        double y = location.getDouble("y");
        double z = location.getDouble("z");
        float pitch = (float) location.getDouble("pitch");
        float yaw = (float) location.getDouble("yaw");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static void startCountdown() {
        countdownID = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Countdown(), 0, 20);
    }

    public static void stopCountdown() {
        Bukkit.getScheduler().cancelTask(countdownID);
    }

    public static WorldEditPlugin getWorldEdit() {
        return (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
    }
}

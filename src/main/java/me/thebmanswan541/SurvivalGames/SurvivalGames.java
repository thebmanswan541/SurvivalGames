package me.thebmanswan541.SurvivalGames;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly
 * prohibited. Thanks.
 * **********************************************************
 */
public class SurvivalGames extends JavaPlugin {

    public static String tag = ChatColor.GRAY+"["+ChatColor.RED+"SG"+ChatColor.GRAY+"] ";

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("SurvivalGames");
    }

    public static Location parseLocation(ConfigurationSection location) {
        World world = Bukkit.getWorld(location.getString("world"));
        double x = location.getDouble("x");
        double y = location.getDouble("y");
        double z = location.getDouble("z");
        float pitch = (float) location.getDouble("pitch");
        float yaw = (float) location.getDouble("yaw");
        return new Location(world, x, y, z, pitch, yaw);
    }

}

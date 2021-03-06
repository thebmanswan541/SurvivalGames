package me.thebmanswan541.SurvivalGames.command.cmds;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.command.SubCommand;
import me.thebmanswan541.SurvivalGames.managers.ArenaManager;
import me.thebmanswan541.SurvivalGames.util.Arena;
import me.thebmanswan541.SurvivalGames.util.Deathmatch;
import me.thebmanswan541.SurvivalGames.util.FileManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class AddSpawn implements SubCommand{

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(SurvivalGames.tag + ChatColor.RED + "Please specify the arena that you are adding a spawn to.");
            return true;
        } else if (args.length > 0) {
            StringBuilder x = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
               x.append(args[i] + " ");
            }
            String name = x.toString().trim();

            if (name.toLowerCase().equals("deathmatch") || name.toUpperCase().equals("DEATHMATCH")) {
                if (FileManager.getConfig().<ConfigurationSection>get("deathmatch") == null) {
                    player.sendMessage(SurvivalGames.tag+ChatColor.RED+"The deathmatch arena has not been set up yet!");
                    return true;
                }

                if (!Deathmatch.getInstance().getBounds().contains(player.getLocation())) {
                    player.sendMessage(SurvivalGames.tag + ChatColor.RED + "You must be inside of the deathmatch arena's bounds!");
                    return true;
                }

                if (FileManager.getConfig().<ConfigurationSection>get("deathmatch.spawns") == null) {
                    FileManager.getConfig().createSection("deathmatch.spawns");
                }

                Deathmatch.getInstance().addSpawn(player.getLocation());
                SurvivalGames.saveLocation(player.getLocation(), FileManager.getConfig().createSection("deathmatch.spawns."+FileManager.getConfig().<ConfigurationSection>get("deathmatch.spawns").getKeys(false).size()));
                FileManager.getConfig().save();
                player.sendMessage(SurvivalGames.tag + ChatColor.GREEN + "Added a new spawn to the deathmatch arena!");
                return true;
            }

            if (ArenaManager.getInstance().getArena(name) == null) {
                player.sendMessage(SurvivalGames.tag + ChatColor.RED + "The specified arena does not exist!");
                return true;
            }

            Arena a = ArenaManager.getInstance().getArena(name);

            if (!a.getBounds().contains(player.getLocation())) {
                player.sendMessage(SurvivalGames.tag+ChatColor.RED+"You must be inside of "+a.getID()+"'s bounds!");
                return true;
            }

            if (FileManager.getArenas().<ConfigurationSection>get(a.getID()+".spawns") == null) {
                FileManager.getArenas().createSection(a.getID()+".spawns");
            }
            a.addSpawn(player.getLocation());
            SurvivalGames.saveLocation(player.getLocation(), FileManager.getArenas().createSection(a.getID()+".spawns."+FileManager.getArenas().<ConfigurationSection>get(a.getID()+".spawns").getKeys(false).size()));
            FileManager.getArenas().save();
            ArenaManager.getInstance().setup();
            player.sendMessage(SurvivalGames.tag+ChatColor.GREEN+"Added a new spawn to arena "+name+"!");
        }
        return true;
    }

    @Override
    public String help(Player p) {
        return "§l- "+ChatColor.RESET+ChatColor.YELLOW+"/sg addspawn <arenaname>"+ChatColor.GRAY+" - "+ChatColor.YELLOW+"Adds a spawn point to the specified arena";
    }
}

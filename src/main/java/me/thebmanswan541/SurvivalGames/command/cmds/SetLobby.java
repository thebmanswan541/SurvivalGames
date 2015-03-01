package me.thebmanswan541.SurvivalGames.command.cmds;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.command.SubCommand;
import me.thebmanswan541.SurvivalGames.managers.ArenaManager;
import me.thebmanswan541.SurvivalGames.util.FileManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class SetLobby implements SubCommand{

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(SurvivalGames.tag+ChatColor.RED + "Please specify a name of the arena!");
            return true;
        }

        String name = args[0];

        if (ArenaManager.getInstance().getArena(name) == null) {
            player.sendMessage(SurvivalGames.tag+ChatColor.RED+"The specified arena does not exist!");
            return true;
        }

        SurvivalGames.saveLocation(player.getLocation(), FileManager.getArenas().createSection(name + ".lobby"));
        FileManager.getArenas().save();
        ArenaManager.getInstance().setup();
        player.sendMessage(SurvivalGames.tag+ChatColor.GREEN+"Successfully set the lobby location of arena "+name+"!");
        return true;
    }

    @Override
    public String help(Player p) {
        return "";
    }
}

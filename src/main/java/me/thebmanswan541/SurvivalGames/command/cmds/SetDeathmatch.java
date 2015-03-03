package me.thebmanswan541.SurvivalGames.command.cmds;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.command.SubCommand;
import me.thebmanswan541.SurvivalGames.managers.ArenaManager;
import net.md_5.bungee.api.ChatColor;
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
public class SetDeathmatch implements SubCommand {

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 0) {
            ArenaManager.getInstance().setDeathmatchLocation(player.getLocation());
            player.sendMessage(SurvivalGames.tag+ ChatColor.GREEN+"Successfully set the deathmatch location!");
        }
        return true;
    }

    @Override
    public String help(Player p) {
        return "§l- "+ChatColor.RESET+ChatColor.YELLOW+"/sg setdeathmatch"+ChatColor.GRAY+" - "+ChatColor.YELLOW+"Sets the death match location for the server";
    }
}

package me.thebmanswan541.SurvivalGames.command.cmds;

import com.sk89q.worldedit.bukkit.selections.Selection;
import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.command.SubCommand;
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
public class SetDeathmatch implements SubCommand {

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 0) {
            Selection sel = SurvivalGames.getWorldEdit().getSelection(player);
            if (sel == null) {
                player.sendMessage(ChatColor.RED+"Please make a WorldEdit selection of the deathmatch arena.");
                return true;
            }

            FileManager.getConfig().set("deathmatch.world", sel.getWorld().getName());
            SurvivalGames.saveLocation(sel.getMinimumPoint(), FileManager.getConfig().<ConfigurationSection>get("deathmatch.cornerA"));
            SurvivalGames.saveLocation(sel.getMaximumPoint(), FileManager.getConfig().<ConfigurationSection>get("deathmatch.cornerB"));
            FileManager.getConfig().save();
            player.sendMessage(SurvivalGames.tag+ ChatColor.GREEN+"Successfully created the deathmatch arena! Now you must set up the spawns!");
        }
        return true;
    }

    @Override
    public String help(Player p) {
        return "§l- "+ChatColor.RESET+ChatColor.YELLOW+"/sg setdeathmatch"+ChatColor.GRAY+" - "+ChatColor.YELLOW+"Creates the deathmatch arena with a WorldEdit selection";
    }
}

package me.thebmanswan541.SurvivalGames.command.cmds;

import com.sk89q.worldedit.bukkit.selections.Selection;
import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.command.SubCommand;
import me.thebmanswan541.SurvivalGames.managers.ArenaManager;
import me.thebmanswan541.SurvivalGames.util.FileManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class CreateArena implements SubCommand{

    public static Permission createArena = new Permission("sg.createArena");

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (player.hasPermission(createArena)) {
            if (args.length == 0) {
                player.sendMessage(SurvivalGames.tag+ChatColor.RED + "Please specify a name for the arena!");
                return true;
            }

            String name = args[0];

            if (ArenaManager.getInstance().getArena(name) != null) {
                player.sendMessage(SurvivalGames.tag + ChatColor.RED + "An arena with this name already exists!");
                return true;
            }

            Selection sel = SurvivalGames.getWorldEdit().getSelection(player);

            if (sel == null) {
                player.sendMessage(SurvivalGames.tag+ChatColor.RED+"Please make a WorldEdit selection of the arena.");
                return true;
            }
            FileManager.getArenas().set(name + ".world", sel.getWorld().getName());
            SurvivalGames.saveLocation(sel.getMinimumPoint(), FileManager.getArenas().createSection(name+".cornerA"));
            SurvivalGames.saveLocation(sel.getMaximumPoint(), FileManager.getArenas().createSection(name+".cornerB"));
            FileManager.getArenas().save();
            ArenaManager.getInstance().setup();
            player.sendMessage(SurvivalGames.tag+ChatColor.GREEN+"Created arena: "+name+". "+ChatColor.YELLOW+"Now you must set the lobby location and spawn locations!");
            return true;
        } else {
            player.sendMessage(SurvivalGames.tag+ChatColor.RED+"You do not have permission.");
        }
        return true;
    }

    @Override
    public String help(Player p) {
        return "";
    }
}

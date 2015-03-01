package me.thebmanswan541.SurvivalGames.command;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.command.cmds.CreateArena;
import me.thebmanswan541.SurvivalGames.command.cmds.SetLobby;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class CommandManager implements CommandExecutor{

    private HashMap<String, SubCommand> cmds;

    public CommandManager() {
        this.cmds = new HashMap<String, SubCommand>();
        cmds.put("createarena", new CreateArena());
        cmds.put("setlobby", new SetLobby());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED+"Only players can use the SurvivalGames commands!");
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("sg")) {
            if (args == null || args.length < 1) {
                p.sendMessage(SurvivalGames.tag+ChatColor.RED+"Type /sg help for help!");
                return true;
            }

            String sub = args[0];
            Vector<String> l = new Vector<String>();
            l.addAll(Arrays.asList(args));
            l.remove(0);
            args = l.toArray(new String[0]);
            try {
                cmds.get(sub).onCommand(p, args);
            } catch (Exception e) {
                e.printStackTrace();
                p.sendMessage(SurvivalGames.tag+ChatColor.RED+"Could not find the command '"+args.toString()+"'!");
                return true;
            }
        }

        return true;
    }
}

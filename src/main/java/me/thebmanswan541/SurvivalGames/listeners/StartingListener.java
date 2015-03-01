package me.thebmanswan541.SurvivalGames.listeners;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.managers.ScoreboardManager;
import me.thebmanswan541.SurvivalGames.util.Arena;
import me.thebmanswan541.SurvivalGames.util.Countdown;
import me.thebmanswan541.SurvivalGames.util.FileManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class StartingListener implements Listener{

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.COUNTDOWN)) {
            try {
                p.teleport(SurvivalGames.parseLocation(FileManager.getArenas().<ConfigurationSection>get(SurvivalGames.arena.getID() + ".lobby")));
            }catch(Exception ex) {
                ex.printStackTrace();
                p.sendMessage(SurvivalGames.tag+ChatColor.RED+"Lobby or default arena has not been set up yet!");
            }
            ScoreboardManager.refreshStartScoreboard();
            e.setJoinMessage(SurvivalGames.tag + ChatColor.AQUA + p.getName() + ChatColor.YELLOW + " joined the game " + ChatColor.YELLOW + "(" + ChatColor.LIGHT_PURPLE + Bukkit.getOnlinePlayers().size() + ChatColor.YELLOW + "/" + ChatColor.LIGHT_PURPLE + Bukkit.getMaxPlayers() + ChatColor.YELLOW + ")!");
            SurvivalGames.arena.addPlayer(p);
            if (Bukkit.getOnlinePlayers().size() == 8) {
                SurvivalGames.arena.setState(Arena.ArenaState.COUNTDOWN);
                ScoreboardManager.cancelWaiting();
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendMessage(SurvivalGames.tag + ChatColor.YELLOW + "The game is starting in " + ChatColor.LIGHT_PURPLE + "60" + ChatColor.YELLOW + " seconds!");
                }
                SurvivalGames.startCountdown();
                ScoreboardManager.refreshStartScoreboard();
            } else if (Bukkit.getOnlinePlayers().size() == 12 && Countdown.getCountdownTime() > 30) {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendMessage(SurvivalGames.tag + ChatColor.YELLOW + "The game is starting in " + ChatColor.LIGHT_PURPLE + "30" + ChatColor.YELLOW + " seconds!");
                    Countdown.setCountdownTime(30);
                    ScoreboardManager.refreshStartScoreboard();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.COUNTDOWN)) {
            ScoreboardManager.refreshStartScoreboard();
            e.setQuitMessage(null);
            SurvivalGames.arena.removePlayer(p);
            if (Bukkit.getOnlinePlayers().size() < 8) {
                SurvivalGames.arena.setState(Arena.ArenaState.WAITING);
                ScoreboardManager.refreshStartScoreboard();
                SurvivalGames.stopCountdown();
                Countdown.setCountdownTime(60);
            }
        }
    }

}

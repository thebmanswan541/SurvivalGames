package me.thebmanswan541.SurvivalGames.listeners;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.util.Arena;
import me.thebmanswan541.SurvivalGames.util.Countdown;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
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
            e.setJoinMessage(SurvivalGames.tag + ChatColor.AQUA + p.getName() + ChatColor.YELLOW + " joined the game " + ChatColor.YELLOW + "(" + ChatColor.LIGHT_PURPLE + Bukkit.getOnlinePlayers().size() + ChatColor.YELLOW + "/" + Bukkit.getMaxPlayers() + ChatColor.LIGHT_PURPLE + ")!");
            SurvivalGames.arena.addPlayer(p);
            if (Bukkit.getOnlinePlayers().size() == 8) {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendMessage(SurvivalGames.tag + ChatColor.YELLOW + "The game is starting in " + ChatColor.LIGHT_PURPLE + "60" + ChatColor.YELLOW + " seconds!");
                }
                SurvivalGames.startCountdown();
            } else if (Bukkit.getOnlinePlayers().size() == 12 && Countdown.getCountdownTime() > 30) {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendMessage(SurvivalGames.tag + ChatColor.YELLOW + "The game is starting in " + ChatColor.LIGHT_PURPLE + "30" + ChatColor.YELLOW + " seconds!");
                    Countdown.setCountdownTime(30);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.COUNTDOWN)) {
            e.setQuitMessage(null);
            SurvivalGames.arena.removePlayer(p);
            if (Bukkit.getOnlinePlayers().size() < 8) {
                SurvivalGames.stopCountdown();
                Countdown.setCountdownTime(60);
            }
        }
    }

}

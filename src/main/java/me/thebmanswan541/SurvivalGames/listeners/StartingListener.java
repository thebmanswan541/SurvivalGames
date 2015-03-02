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
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class StartingListener implements Listener{

    public static Countdown c;

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (SurvivalGames.arena == null) {
            p.sendMessage(SurvivalGames.tag+ChatColor.RED+"§lPlease create a default arena!");
        } else {
            if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
                try {
                    p.teleport(SurvivalGames.parseLocation(FileManager.getArenas().<ConfigurationSection>get(SurvivalGames.arena.getID() + ".lobby")));
                } catch (Exception ex) {
                    p.sendMessage(SurvivalGames.tag + ChatColor.RED + "Lobby has not been set up yet!");
                }
                ScoreboardManager.refreshStartScoreboard();
                e.setJoinMessage(SurvivalGames.tag + ChatColor.AQUA + p.getName() + ChatColor.YELLOW + " joined the game " + ChatColor.YELLOW + "(" + ChatColor.LIGHT_PURPLE + Bukkit.getOnlinePlayers().size() + ChatColor.YELLOW + "/" + ChatColor.LIGHT_PURPLE + Bukkit.getMaxPlayers() + ChatColor.YELLOW + ")!");
                if (Bukkit.getOnlinePlayers().size() == 1) {
                    SurvivalGames.arena.setState(Arena.ArenaState.LOBBY_COUNTDOWN);
                    ScoreboardManager.cancelWaiting();
                    c = new Countdown(SurvivalGames.arena, 60, 60, 30, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
                    c.runTaskTimer(SurvivalGames.getPlugin(), 0, 20);
                    ScoreboardManager.refreshStartScoreboard();
                } else if (Bukkit.getOnlinePlayers().size() == 12 && c.getTimeLeft() > 30) {
                    c.setCountdownTime(30);
                    ScoreboardManager.refreshStartScoreboard();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
            ScoreboardManager.refreshStartScoreboard();
            e.setQuitMessage(null);
            if (Bukkit.getOnlinePlayers().size() < 8 && c.getTimeLeft() < 60) {
                SurvivalGames.arena.setState(Arena.ArenaState.WAITING);
                ScoreboardManager.refreshStartScoreboard();
                c.stopCountdown();
            }
        }
    }

}

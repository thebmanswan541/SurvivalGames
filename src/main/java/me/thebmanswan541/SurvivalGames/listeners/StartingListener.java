package me.thebmanswan541.SurvivalGames.listeners;

import me.thebmanswan541.SurvivalGames.exceptions.ArenaNotFoundException;
import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.managers.ScoreboardManager;
import me.thebmanswan541.SurvivalGames.util.Arena;
import me.thebmanswan541.SurvivalGames.util.Countdown;
import me.thebmanswan541.SurvivalGames.util.FileManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
    public static Countdown d;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.getInventory().clear();
        p.setGameMode(GameMode.ADVENTURE);
        if (SurvivalGames.arena == null) {
            p.sendMessage(SurvivalGames.tag+ChatColor.RED+"§lPlease create a default arena!");
        } else {
            if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
                try {
                    p.teleport(SurvivalGames.parseLocation(FileManager.getArenas().<ConfigurationSection>get(SurvivalGames.arena.getID() + ".lobby")));
                } catch (ArenaNotFoundException ex) {
                    p.sendMessage(SurvivalGames.tag + ChatColor.RED + "Lobby has not been set up yet!");
                }

                ScoreboardManager.refreshStartScoreboard(Bukkit.getOnlinePlayers().size());
                e.setJoinMessage(SurvivalGames.tag + ChatColor.GRAY + p.getName() + ChatColor.YELLOW + " joined the game " + ChatColor.YELLOW + "(" + ChatColor.LIGHT_PURPLE + Bukkit.getOnlinePlayers().size() + ChatColor.YELLOW + "/" + ChatColor.LIGHT_PURPLE + Bukkit.getMaxPlayers() + ChatColor.YELLOW + ")!");
                if (Bukkit.getOnlinePlayers().size() >= 1) {
                    SurvivalGames.arena.setState(Arena.ArenaState.LOBBY_COUNTDOWN);
                    ScoreboardManager.cancelWaiting();
                    ScoreboardManager.refreshStartScoreboard(Bukkit.getOnlinePlayers().size());
                    c = new Countdown(SurvivalGames.arena, 60, 60, 30, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
                    c.runTaskTimer(SurvivalGames.getPlugin(), 0, 20);
                } else if (Bukkit.getOnlinePlayers().size() == 12 && c.getTimeLeft() > 30) {
                    c.setCountdownTime(30);
                    ScoreboardManager.refreshStartScoreboard(Bukkit.getOnlinePlayers().size());
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
            ScoreboardManager.refreshStartScoreboard(Bukkit.getOnlinePlayers().size()-1);
            e.setQuitMessage(null);
            if (Bukkit.getOnlinePlayers().size()-1 < 2 && c.getTimeLeft() < 60) {
                SurvivalGames.arena.setState(Arena.ArenaState.WAITING);
                ScoreboardManager.refreshStartScoreboard(Bukkit.getOnlinePlayers().size() - 1);
                c.stopCountdown();
            }
        }
    }

}

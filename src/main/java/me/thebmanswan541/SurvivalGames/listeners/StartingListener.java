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
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;

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
    public static Permission vipPerm = new Permission("sg.vip");
    public static Permission mvpPerm = new Permission("sg.mvp");

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission(mvpPerm)) {
            p.setDisplayName(ChatColor.AQUA+p.getName());
        } else if (p.hasPermission(vipPerm)) {
            p.setDisplayName(ChatColor.GREEN+p.getName());
        } else {
            p.setDisplayName(ChatColor.GRAY+p.getName());
        }
        p.getInventory().clear();
        p.getInventory().setItem(8, SurvivalGames.exitToLobby);
        p.setGameMode(GameMode.ADVENTURE);
        for (PotionEffect pl : p.getActivePotionEffects()) {
            p.removePotionEffect(pl.getType());
        }
        SurvivalGames.spectators.setAutoTeleport(p, true);
        SurvivalGames.spectators.setNightVision(p, true);
        SurvivalGames.spectators.setShowSpectators(p, false);
        SurvivalGames.spectators.setAlwaysFlying(p, false);
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
                e.setJoinMessage(SurvivalGames.tag + p.getDisplayName() + ChatColor.YELLOW + " joined the game " + ChatColor.YELLOW + "(" + ChatColor.AQUA + Bukkit.getOnlinePlayers().size() + ChatColor.YELLOW + "/" + ChatColor.AQUA + Bukkit.getMaxPlayers() + ChatColor.YELLOW + ")!");
                if (Bukkit.getOnlinePlayers().size() >= 1 && c == null) {
                    SurvivalGames.arena.setState(Arena.ArenaState.LOBBY_COUNTDOWN);
                    ScoreboardManager.cancelWaiting();
                    ScoreboardManager.refreshStartScoreboard(Bukkit.getOnlinePlayers().size());
                    c = new Countdown(SurvivalGames.arena, 30, 30, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
                    c.runTaskTimer(SurvivalGames.getPlugin(), 0, 20);
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
            ScoreboardManager.refreshStartScoreboard(Bukkit.getOnlinePlayers().size()-1);
            e.setQuitMessage(SurvivalGames.tag+e.getPlayer().getDisplayName()+ChatColor.YELLOW+" has quit!");
            if (Bukkit.getOnlinePlayers().size()-1 < 2 && c.getTimeLeft() < 30) {
                SurvivalGames.arena.setState(Arena.ArenaState.WAITING);
                ScoreboardManager.refreshStartScoreboard(Bukkit.getOnlinePlayers().size() - 1);
                c.stopCountdown();
                c = null;
            }
        }
    }

}

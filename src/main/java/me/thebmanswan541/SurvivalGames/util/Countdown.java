package me.thebmanswan541.SurvivalGames.util;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.listeners.KitSelector;
import me.thebmanswan541.SurvivalGames.listeners.StartingListener;
import me.thebmanswan541.SurvivalGames.managers.KitManager;
import me.thebmanswan541.SurvivalGames.managers.ScoreboardManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class Countdown extends BukkitRunnable {

    private Arena a;
    private int time;
    private ArrayList<Integer> times;

    public Countdown(Arena a, int time, int... times) {
        this.a = a;
        this.time = time;
        this.times = new ArrayList<Integer>();

        for (int c : times) {
            this.times.add(c);
        }
    }

    public Integer getTimeLeft() {
        return time;
    }

    @Override
    public void run() {
        if (time == 0) {
            if (SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    SurvivalGames.arena.addPlayer(p);
                    p.getInventory().setItem(0, KitSelector.kitSelector);
                    p.sendMessage(ChatColor.BOLD + "======================================");
                    p.sendMessage(" ");
                    p.sendMessage(ChatColor.BOLD+"                   Survival Games");
                    p.sendMessage(ChatColor.YELLOW+"                    §lLet the games begin!");
                    p.sendMessage(" ");
                    p.sendMessage(ChatColor.BOLD + "======================================");
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You will be able to move in 30 seconds! Choose a kit by right-clicking the Kit Selector!");
                }
                SurvivalGames.arena.setState(Arena.ArenaState.START_COUNTDOWN);
                StartingListener.c.cancel();
                StartingListener.c = new Countdown(SurvivalGames.arena, 30, 15, 10, 5, 4, 3, 2, 1);
                StartingListener.c.runTaskTimer(SurvivalGames.getPlugin(), 0, 20);
            }  else if (SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN)) {
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    p.getInventory().clear();
                    p.sendMessage(SurvivalGames.tag + ChatColor.YELLOW + "The games have begun! You will recieve the "+ChatColor.GREEN+KitSelector.selectedKit.get(p).getName()+ChatColor.YELLOW+" kit in 60 seconds! Invulnerability runs out in 20 seconds!");
                }
                SurvivalGames.arena.setState(Arena.ArenaState.GRACE_PERIOD);
                StartingListener.c.cancel();
                StartingListener.c = new Countdown(SurvivalGames.arena, 20, 5, 4, 3, 2, 1);
                StartingListener.c.runTaskTimer(SurvivalGames.getPlugin(), 0, 20);
            } else if (SurvivalGames.arena.isState(Arena.ArenaState.GRACE_PERIOD)) {
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You no longer have invulnerability!");
                }
                SurvivalGames.arena.setState(Arena.ArenaState.IN_GAME);
                StartingListener.c.cancel();
                StartingListener.c = new Countdown(SurvivalGames.arena, 40, 5, 4, 3, 2, 1);
                StartingListener.c.runTaskTimer(SurvivalGames.getPlugin(), 0, 20);
            } else if (SurvivalGames.arena.isState(Arena.ArenaState.IN_GAME)) {
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    for (ItemStack is : KitSelector.selectedKit.get(p).getItems()) {
                        p.getInventory().addItem(is);
                    }
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You got your "+ChatColor.GREEN+KitSelector.selectedKit.get(p).getName()+" kit!");
                    KitSelector.selectedKit.remove(p);
                }
                StartingListener.c.cancel();
            }
            cancel();
            return;
        }
        if (times.contains(time)) {
            if (SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(SurvivalGames.tag + ChatColor.YELLOW + "The game is starting in " + ChatColor.LIGHT_PURPLE + time + ChatColor.YELLOW + " seconds!");
                }
            } else if (SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN)) {
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    p.sendMessage(SurvivalGames.tag + ChatColor.YELLOW + "You will be able to move in " + time + " seconds!");
                }
            } else if (SurvivalGames.arena.isState(Arena.ArenaState.GRACE_PERIOD)) {
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"Invulnerability wears off in "+time+" seconds!");
                }
            } else if (SurvivalGames.arena.isState(Arena.ArenaState.IN_GAME)) {
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You will get your kit items in "+time+" seconds!");
                }
            }
        }
        ScoreboardManager.refreshStartScoreboard();
        time--;
    }

    public void stopCountdown() {
        this.cancel();
    }

    public void setCountdownTime(int time) {
        this.time = time;
    }

}

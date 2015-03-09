package me.thebmanswan541.SurvivalGames.util;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.exceptions.ArenaNotFoundException;
import me.thebmanswan541.SurvivalGames.kits.Kit;
import me.thebmanswan541.SurvivalGames.listeners.KillListener;
import me.thebmanswan541.SurvivalGames.listeners.KitSelector;
import me.thebmanswan541.SurvivalGames.listeners.StartingListener;
import me.thebmanswan541.SurvivalGames.managers.ArenaManager;
import me.thebmanswan541.SurvivalGames.managers.KitManager;
import me.thebmanswan541.SurvivalGames.managers.ScoreboardManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;
import java.util.Random;

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
                SurvivalGames.arena.setState(Arena.ArenaState.START_COUNTDOWN);
                StartingListener.c.cancel();
                StartingListener.c = new Countdown(SurvivalGames.arena, 30, 15, 10, 5, 4, 3, 2, 1);
                StartingListener.c.runTaskTimer(SurvivalGames.getPlugin(), 0, 20);
                StartingListener.d = new Countdown(SurvivalGames.arena, 900, 600, 300, 60, 5, 4, 3, 2, 1);
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
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    KillListener.resetKills(p);
                    ScoreboardManager.refreshMainScoreboard(p);
                }
            }  else if (SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN)) {
                SurvivalGames.arena.setState(Arena.ArenaState.GRACE_PERIOD);
                StartingListener.c.cancel();
                StartingListener.c = new Countdown(SurvivalGames.arena, 20, 5, 4, 3, 2, 1);
                StartingListener.c.runTaskTimer(SurvivalGames.getPlugin(), 0, 20);
                StartingListener.d.runTaskTimer(SurvivalGames.getPlugin(), 0, 20);
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    p.getInventory().clear();
                    p.sendMessage(SurvivalGames.tag + ChatColor.YELLOW + "The games have begun!");
                    if (KitSelector.selectedKit.get(p) == null) {
                        Random r = new Random();
                        int rkit = r.nextInt(KitManager.getInstance().getKits().size());
                        KitSelector.selectedKit.put(p, KitManager.getInstance().getKits().get(rkit));
                        p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You did not choose a kit so you were assigned to the "+ChatColor.GREEN+KitSelector.selectedKit.get(p).getName()+ChatColor.YELLOW+" kit!");
                        p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You will receive the "+ChatColor.GREEN+KitSelector.selectedKit.get(p).getName()+ChatColor.YELLOW+" kit in 60 seconds!");
                    } else {
                        p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You will receive the "+ChatColor.GREEN+KitSelector.selectedKit.get(p).getName()+ChatColor.YELLOW+" kit in 60 seconds!");
                    }
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"Invulnerability runs out in 20 seconds!");
                }
            } else if (SurvivalGames.arena.isState(Arena.ArenaState.GRACE_PERIOD)) {
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You no longer have invulnerability!");
                }
                SurvivalGames.arena.setState(Arena.ArenaState.RECEIVE_KIT);
                StartingListener.c.stopCountdown();
                StartingListener.c = new Countdown(SurvivalGames.arena, 40, 5, 4, 3, 2, 1);
                StartingListener.c.runTaskTimer(SurvivalGames.getPlugin(), 0, 20);
            } else if (SurvivalGames.arena.isState(Arena.ArenaState.RECEIVE_KIT)) {
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    for (ItemStack is : KitSelector.selectedKit.get(p).getItems()) {
                        p.getInventory().addItem(is);
                    }
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You got your "+ChatColor.GREEN+KitSelector.selectedKit.get(p).getName()+ChatColor.YELLOW+" kit!");
                    KitSelector.selectedKit.remove(p);
                }
                SurvivalGames.arena.setState(Arena.ArenaState.IN_GAME);
                StartingListener.c.stopCountdown();
            } else if (SurvivalGames.arena.isState(Arena.ArenaState.IN_GAME)) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"Deathmatch starting! Teleporting all players to the deathmatch arena...");
                    SurvivalGames.arena.removePlayer(p);
                    Deathmatch.getInstance().addPlayer(p);
                }
                SurvivalGames.arena.setState(Arena.ArenaState.DEATHMATCH);
                StartingListener.d.stopCountdown();
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
            } else if (SurvivalGames.arena.isState(Arena.ArenaState.RECEIVE_KIT)) {
                for (Player p : SurvivalGames.arena.getPlayers()) {
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You will get your kit items in "+time+" seconds!");
                }
            } else if (SurvivalGames.arena.isState(Arena.ArenaState.IN_GAME)) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (time > 60) {
                        if (time/60 == 1) {
                            p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"Deathmatch starting in 1 minute!");
                        } else {
                            p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"Deathmatch starting in "+time/60+" minutes!");
                        }
                    } else {
                        p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"Deathmatch starting in "+time+" seconds!");
                    }
                }
            }
        }
        if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
            refreshTimeOnStartBoard(StartingListener.c.getTimeLeft());
        } else if (!SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN)){
            refreshTimeOnMainBoard(StartingListener.d.getTimeLeft());
        }
        time--;
    }

    public void stopCountdown() {
        this.cancel();
    }

    public void setCountdownTime(int time) {
        this.time = time;
    }

    private void refreshTimeOnStartBoard(int time) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            int resetTime = time + 1;
            p.getScoreboard().resetScores("Starting in " + ChatColor.GREEN + resetTime + "s");
            Score e = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("Starting in " + ChatColor.GREEN + time + "s");
            e.setScore(3);
        }
    }

    private void refreshTimeOnMainBoard(int time) {
        for (Player p : SurvivalGames.arena.getPlayers()) {
            int resetTime = time + 1;
            String numtime = ScoreboardManager.getNumberToTimeFormat(resetTime);
            p.getScoreboard().resetScores("deathmatch: " + ChatColor.GREEN + numtime);
            Score f = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("deathmatch: " + ChatColor.GREEN + ScoreboardManager.getNumberToTimeFormat(time));
            f.setScore(2);
        }
    }

}

package me.thebmanswan541.SurvivalGames.managers;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.listeners.StartingListener;
import me.thebmanswan541.SurvivalGames.util.Arena;
import me.thebmanswan541.SurvivalGames.util.Countdown;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class ScoreboardManager {

    private static Scoreboard s;
    private static int waitingID;
    private Countdown c;

    public static void refreshStartScoreboard() {
        s = Bukkit.getScoreboardManager().getNewScoreboard();
        final Objective o = s.registerNewObjective("Start", "dummy");
        o.setDisplayName(ChatColor.YELLOW+"§lSURVIVAL "+ChatColor.GOLD+"§lGAMES");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score a = o.getScore("§1");
        Score b = o.getScore(ChatColor.WHITE+"Map: "+ChatColor.GREEN+ SurvivalGames.arena.getID());
        Score c = o.getScore(ChatColor.WHITE+"Players: "+ChatColor.GREEN+Bukkit.getOnlinePlayers().size()+"/"+Bukkit.getMaxPlayers());
        Score d = o.getScore("§2");
        if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING)) {
            waitingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalGames.getPlugin(), new Runnable() {
                int index = 0;
                Score e;

                public void run() {
                    index++;
                    if (index == 1) {
                        if (o.getScore("Waiting...") != null) {
                            o.getScoreboard().resetScores("Waiting...");
                        }
                        e = o.getScore("Waiting");
                        e.setScore(3);
                    } else if (index == 2) {
                        o.getScoreboard().resetScores("Waiting");
                        e = o.getScore("Waiting.");
                        e.setScore(3);
                    } else if (index == 3) {
                        o.getScoreboard().resetScores("Waiting.");
                        e = o.getScore("Waiting..");
                        e.setScore(3);
                    } else if (index == 4) {
                        index = 0;
                        o.getScoreboard().resetScores("Waiting..");
                        e = o.getScore("Waiting...");
                        e.setScore(3);
                    }
                }
            }, 0, 20);
        } else if (SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
            Score e = o.getScore(ChatColor.WHITE+"Starting in "+ChatColor.GREEN+ StartingListener.c.getTimeLeft()+"s");
            e.setScore(3);
        }
        Score f = o.getScore("§4");
        Score g = o.getScore("www.sgtest.org");
        g.setScore(1);
        f.setScore(2);
        d.setScore(4);
        c.setScore(5);
        b.setScore(6);
        a.setScore(7);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(s);
        }
    }

    public static void cancelWaiting() {
        Bukkit.getScheduler().cancelTask(waitingID);
    }

    private static Scoreboard main;

    public static void refreshMainScoreboard() {
        main = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = main.registerNewObjective("Main board", "dummy");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.YELLOW+"§lSURVIVAL "+ChatColor.GOLD+"§lGAMES");
        Score a = o.getScore("§0");
        Score b = o.getScore("Kills: "+ChatColor.GREEN);
        Score c = o.getScore("Players left: "+ChatColor.GREEN+SurvivalGames.arena.getPlayers().size());
        Score d = o.getScore("§1");
        Score e = o.getScore("Time until death match: "+ChatColor.GREEN);
        Score f = o.getScore("§2");
        a.setScore(6);
        b.setScore(5);
        c.setScore(4);
        d.setScore(3);
        e.setScore(2);
        f.setScore(1);
    }



}

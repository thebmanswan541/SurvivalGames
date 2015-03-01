package me.thebmanswan541.SurvivalGames.managers;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
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
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class ScoreboardManager {

    private static Scoreboard s;
    private static Objective o;
    private static int waitingID;

    public static void refreshStartScoreboard() {
        s = Bukkit.getScoreboardManager().getNewScoreboard();
        o = s.registerNewObjective("Start", "dummy");
        o.setDisplayName(ChatColor.YELLOW+"§lSURVIVAL "+ChatColor.GOLD+"§lGAMES");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score a = o.getScore("§1");
        Score b = o.getScore(ChatColor.WHITE+"Map: "+ChatColor.GREEN+ SurvivalGames.arena.getID());
        Score c = o.getScore(ChatColor.WHITE+"Players: "+ChatColor.GREEN+Bukkit.getOnlinePlayers().size()+"/"+Bukkit.getMaxPlayers());
        Score d = o.getScore("§2");
        waitingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalGames.getPlugin(), new Runnable() {
            int index = 0;
            Score e;
            @Override
            public void run() {
                if (index == 0) {
                    if (o.getScore("Waiting...") != null) {
                        o.getScoreboard().resetScores("Waiting...");
                    }
                    e = o.getScore("Waiting");
                    e.setScore(3);
                } else if (index == 1) {
                    if (o.getScore("Waiting") != null) {
                        o.getScoreboard().resetScores("Waiting");
                    }
                    e = o.getScore("Waiting.");
                    e.setScore(3);
                } else if (index == 2) {
                    if (o.getScore("Waiting.") != null) {
                        o.getScoreboard().resetScores("Waiting.");
                    }
                    e = o.getScore("Waiting..");
                    e.setScore(3);
                } else if (index == 3) {
                    if (o.getScore("Waiting..") != null) {
                        o.getScoreboard().resetScores("Waiting..");
                    }
                    e = o.getScore("Waiting...");
                    e.setScore(3);
                    index = -1;
                }
                index++;
            }
        }, 0, 20);
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



}

package me.thebmanswan541.SurvivalGames.managers;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.listeners.KillListener;
import me.thebmanswan541.SurvivalGames.listeners.StartingListener;
import me.thebmanswan541.SurvivalGames.util.Arena;
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

    public static void refreshStartScoreboard(int numPlayers) {
        s = Bukkit.getScoreboardManager().getNewScoreboard();
        final Objective o = s.registerNewObjective("Start", "dummy");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.YELLOW+"§lBlitz SG");
        animateBSGDisplayName(o);
        Score a = o.getScore("§1");
        Score b = o.getScore(ChatColor.WHITE+"Map: "+ChatColor.GREEN+ SurvivalGames.arena.getID());
        Score c = o.getScore(ChatColor.WHITE+"Players: "+ChatColor.GREEN+numPlayers+"/"+Bukkit.getMaxPlayers());
        Score d = o.getScore("§2");
        if (SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
            if (o.getScore("Starting in "+ChatColor.GREEN+"60s") == null) {
                Score e = o.getScore(ChatColor.WHITE + "Starting in " + ChatColor.GREEN + "60s");
                e.setScore(3);
            }
        } else if (SurvivalGames.arena.isState(Arena.ArenaState.WAITING)) {
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
        }
        Score f = o.getScore("§4");
        Score g = o.getScore("www.hypixel.net");
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

    public static void refreshMainScoreboard(Player p) {
        main = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = main.registerNewObjective("Main board", "dummy");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.YELLOW+"§lBLITZ SG");
        Score a = o.getScore("§0");
        Score b = o.getScore("Kills: "+ChatColor.GREEN+ KillListener.getKills(p));
        Score c = o.getScore("Players left: "+ChatColor.GREEN+SurvivalGames.arena.getPlayers().size());
        Score d = o.getScore("§1");
        Score e = o.getScore("Time until");
        Score f;
        if (StartingListener.d.getTimeLeft().equals(1)) {
            f = o.getScore("deathmatch: "+ChatColor.RED+"N/A");
        } else {
            f = o.getScore("deathmatch: " + ChatColor.GREEN + "15:00");
        }
        Score g = o.getScore("§2");
        a.setScore(7);
        b.setScore(6);
        c.setScore(5);
        d.setScore(4);
        e.setScore(3);
        f.setScore(2);
        g.setScore(1);
        p.setScoreboard(main);
    }

    public static String getNumberToTimeFormat(int time) {
        int minute = time/60;
        int timeToTenth = minute*60;
        int second = time-timeToTenth;
        if (second < 10) {
            if (minute < 10) {
                return "0"+minute+":0"+second;
            } else {
                return minute + ":0" + second;
            }
        }
        if (minute < 10) {
            if (second < 10) {
                return "0"+minute+":0"+second;
            } else {
                return "0"+minute+":"+second;
            }
        }
        return minute+":"+second;
    }

    private static void animateBSGDisplayName(final Objective o) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(SurvivalGames.getPlugin(), new Runnable() {
            public void run() {
                Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalGames.getPlugin(), new Runnable() {
                    int index = 0;
                    public void run() {
                        if (index == 0) {
                            o.setDisplayName(ChatColor.GOLD+"§lB"+ChatColor.YELLOW+"litz SG");
                            index++;
                        } else if (index == 1) {
                            o.setDisplayName(ChatColor.WHITE+"§lB"+ChatColor.GOLD+"l"+ChatColor.YELLOW+"itz SG");
                            index++;
                        } else if (index == 2) {
                            o.setDisplayName(ChatColor.WHITE+"§lBl"+ChatColor.GOLD+"i"+ChatColor.YELLOW+"tz SG");
                            index++;
                        } else if (index == 3) {
                            o.setDisplayName(ChatColor.WHITE+"§lBli"+ChatColor.GOLD+"t"+ChatColor.YELLOW+"z SG");
                            index++;
                        } else if (index == 4) {
                            o.setDisplayName(ChatColor.WHITE+"§lBlitz"+ChatColor.GOLD+" S"+ChatColor.YELLOW+"G");
                            index++;
                        } else if (index == 5) {
                            o.setDisplayName(ChatColor.WHITE+"§lBlitz S"+ChatColor.GOLD+"G");
                            index++;
                        } else if (index == 6) {
                            o.setDisplayName(ChatColor.WHITE+"§lBlitz SG");
                            index++;
                        } else if (index == 7) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(SurvivalGames.getPlugin(), new Runnable() {
                                public void run() {
                                    o.setDisplayName(ChatColor.YELLOW+"§lBlitz SG");
                                    index++;
                                }
                            }, 20);
                        }
                    }
                }, 0, 3);
            }
        }, 100);
    }

}

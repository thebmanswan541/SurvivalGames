package me.thebmanswan541.SurvivalGames.util;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class Countdown implements Runnable{

    private static int countdownTime = 60;

    public static int getCountdownTime() {
        return countdownTime;
    }

    public static void setCountdownTime(int time) {
        countdownTime = time;
    }

    @Override
    public void run() {
        if (getCountdownTime() == 0) {
          // TODO: start the game
        } else if (getCountdownTime() == 30)  {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(SurvivalGames.tag+ ChatColor.YELLOW+"The game is starting in "+ChatColor.LIGHT_PURPLE+"30"+ChatColor.YELLOW+" seconds!");
            }
        } else if (getCountdownTime() <= 10) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(SurvivalGames.tag+ ChatColor.YELLOW+"The game is starting in "+ChatColor.LIGHT_PURPLE+countdownTime+ChatColor.YELLOW+" seconds!");
            }
        }
        countdownTime--;
    }

}

package me.thebmanswan541.SurvivalGames.listeners;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.util.Arena;
import me.thebmanswan541.SurvivalGames.util.Deathmatch;
import me.thebmanswan541.SurvivalGames.util.SpectatorList;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.PacketPlayOutRespawn;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;

import java.util.HashMap;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class KillListener implements Listener{

    private static HashMap<Player, Integer> kills = new HashMap<Player, Integer>();

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        final Player p = e.getEntity();
        p.getWorld().strikeLightningEffect(p.getLocation());
        Bukkit.getScheduler().scheduleSyncDelayedTask(SurvivalGames.getPlugin(), new Runnable() {
            @Override
            public void run() {
              SurvivalGames.spectators.addPlayer(p);
            }
        }, 20L);
        int playersLeft = SurvivalGames.arena.getPlayers().size();
        SurvivalGames.arena.removePlayer(p);
        if (SurvivalGames.arena.getPlayers().size() == 1) {
            if (SurvivalGames.arena.isState(Arena.ArenaState.DEATHMATCH)) {
                Deathmatch.getInstance().removePlayer(p);
            }
            SurvivalGames.arena.removePlayer(p.getKiller());
            //TODO: Send players to lobby server
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
        } else {
            p.getKiller().getScoreboard().resetScores("Kills: "+ChatColor.GREEN+ getKills(p.getKiller()));
            p.getKiller().getScoreboard().resetScores("Players left: "+ChatColor.GREEN+playersLeft);
            kills.put(p.getKiller(), getKills(p.getKiller()) + 1);
            p.getKiller().sendMessage(SurvivalGames.tag+ChatColor.WHITE+"You killed "+ChatColor.GREEN+p.getName()+ChatColor.WHITE+"!");
            p.getKiller().sendMessage(SurvivalGames.tag + ChatColor.GOLD + "+40 coins!");
            for (Player pl : Bukkit.getOnlinePlayers()) {
                refreshBoard(pl);
                pl.sendMessage(SurvivalGames.tag + ChatColor.YELLOW + p.getName() + ChatColor.RESET + " was killed by " + ChatColor.YELLOW + p.getKiller().getName() + ChatColor.RESET + "! Only " + ChatColor.GREEN + SurvivalGames.arena.getPlayers().size() + ChatColor.RESET + " players remain!");
            }
        }
    }

    public static int getKills(Player p) {
        return kills.get(p);
    }

    public static void resetKills(Player p) {
        kills.put(p, 0);
    }

    private void refreshBoard(Player p) {
        Score kills = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("Kills: "+ChatColor.GREEN+ getKills(p));
        Score playersLeft = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("Players left: "+ChatColor.GREEN+SurvivalGames.arena.getPlayers().size());
        playersLeft.setScore(4);
        kills.setScore(5);
    }

}

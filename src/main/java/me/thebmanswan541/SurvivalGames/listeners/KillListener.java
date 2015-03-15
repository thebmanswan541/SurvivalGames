package me.thebmanswan541.SurvivalGames.listeners;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.util.Arena;
import me.thebmanswan541.SurvivalGames.util.Deathmatch;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
        final Player deadPlayer = e.getEntity();
        Player killer = e.getEntity().getKiller();

        Bukkit.getScheduler().scheduleSyncDelayedTask(SurvivalGames.getPlugin(), new Runnable() {
            public void run() {
                PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
                ((CraftPlayer) deadPlayer).getHandle().playerConnection.a(packet);
                deadPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 4));
                deadPlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 4));
                deadPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
            }
        }, 20);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        SurvivalGames.spectators.addPlayer(e.getPlayer());
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

package me.thebmanswan541.SurvivalGames.listeners;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.util.Arena;
import me.thebmanswan541.SurvivalGames.util.Deathmatch;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
    private Player thirdPlace;
    private Player secondPlace;
    private Player firstPlace;

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        final Player deadPlayer = e.getEntity();
        Player killer = e.getEntity().getKiller();
        int playersLeft = SurvivalGames.arena.getPlayers().size()-1;
        e.setDeathMessage(null);
        if (SurvivalGames.arena.isState(Arena.ArenaState.RECEIVE_KIT)) {
            KitSelector.removeFromKitQueue(deadPlayer);
        }
        deadPlayer.getWorld().strikeLightningEffect(deadPlayer.getLocation());
        Bukkit.getScheduler().scheduleSyncDelayedTask(SurvivalGames.getPlugin(), new Runnable() {
            public void run() {
                PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
                ((CraftPlayer) deadPlayer).getHandle().playerConnection.a(packet);
                deadPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 4));
                deadPlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 4));
                deadPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
            }
        }, 20);

        if (playersLeft == 2) {
            this.thirdPlace = deadPlayer;
        } else if (playersLeft < 1) {
            this.secondPlace = deadPlayer;
            this.firstPlace = SurvivalGames.arena.getPlayers().get(0);
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (killer == null) {
                p.sendMessage(SurvivalGames.tag + deadPlayer.getDisplayName() + ChatColor.YELLOW + " was killed!");
            } else {
                p.sendMessage(SurvivalGames.tag + deadPlayer.getDisplayName() + ChatColor.YELLOW + " was killed by " + killer.getDisplayName()+ChatColor.YELLOW+"!");
            }
            if (playersLeft > 1) {
                p.sendMessage(SurvivalGames.tag + ChatColor.YELLOW + "There are " + ChatColor.RED + playersLeft + ChatColor.YELLOW + " players remaining!");
            } else {
                p.sendMessage(ChatColor.GREEN + "-----------------------------------------------------");
                p.sendMessage(ChatColor.BOLD+"                    Blitz Survival Games");
                p.sendMessage("");
                p.sendMessage(ChatColor.YELLOW+"                        1st Place "+ChatColor.GRAY+"- "+firstPlace.getDisplayName());
                p.sendMessage(ChatColor.GOLD+"                        2nd Place "+ChatColor.GRAY+"- "+secondPlace.getDisplayName());
                p.sendMessage(ChatColor.RED+"                        3rd Place "+ChatColor.GRAY+"- "+thirdPlace.getDisplayName());
                p.sendMessage("");
                p.sendMessage(ChatColor.GREEN + "-----------------------------------------------------");
                StartingListener.d.stopCountdown();
            }
        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        SurvivalGames.arena.removePlayer(e.getPlayer());
        refreshBoard(e.getPlayer());
    }

    public static int getKills(Player p) {
        return kills.get(p);
    }

    public static void resetKills(Player p) {
        kills.put(p, 0);
    }

    private void refreshBoard(Player p) {
        Score kills = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("Kills: "+ChatColor.GREEN+ getKills(p));
        Score playersAreLeft = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("Players left: "+ChatColor.GREEN+SurvivalGames.arena.getPlayers().size());
        playersAreLeft.setScore(4);
        kills.setScore(5);
    }

}

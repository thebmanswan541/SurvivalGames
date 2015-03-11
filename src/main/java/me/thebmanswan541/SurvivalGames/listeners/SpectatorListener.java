package me.thebmanswan541.SurvivalGames.listeners;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.util.Arena;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class SpectatorListener implements Listener {


    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        if (SurvivalGames.spectators.hasPlayer(e.getPlayer()) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN) || SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN) || SurvivalGames.arena.isState(Arena.ArenaState.WAITING)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (SurvivalGames.spectators.hasPlayer(e.getPlayer()) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN) || SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN) || SurvivalGames.arena.isState(Arena.ArenaState.WAITING)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (SurvivalGames.spectators.hasPlayer(p) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN) || SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN) || SurvivalGames.arena.isState(Arena.ArenaState.WAITING)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        Player p = (Player) e.getEntity();
        if (SurvivalGames.spectators.hasPlayer(p) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN) || SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN) || SurvivalGames.arena.isState(Arena.ArenaState.WAITING)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.REDSTONE_COMPARATOR) {
                p.openInventory(SurvivalGames.spectators.getGUI(p));
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (SurvivalGames.spectators.hasPlayer(p) || SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN)) {
            e.setCancelled(true);
            if (e.getInventory().getName().contains("Spectator")) {
                if (e.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
                    if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                        p.removePotionEffect(PotionEffectType.SPEED);
                    }
                    p.sendMessage(ChatColor.RED+"You no longer have any speed effects!");
                } else if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS) {
                    if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                        p.removePotionEffect(PotionEffectType.SPEED);
                    }
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                    p.sendMessage(ChatColor.GREEN+"You now have Speed I!");
                } else if (e.getCurrentItem().getType() == Material.IRON_BOOTS) {
                    if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                        p.removePotionEffect(PotionEffectType.SPEED);
                    }
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
                    p.sendMessage(ChatColor.GREEN+"You now have Speed II!");
                } else if (e.getCurrentItem().getType() == Material.GOLD_BOOTS) {
                    if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                        p.removePotionEffect(PotionEffectType.SPEED);
                    }
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
                    p.sendMessage(ChatColor.GREEN+"You now have Speed III!");
                } else if (e.getCurrentItem().getType() == Material.DIAMOND_BOOTS) {
                    if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                        p.removePotionEffect(PotionEffectType.SPEED);
                    }
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
                    p.sendMessage(ChatColor.GREEN+"You now have Speed IV!");
                }
                p.closeInventory();
            }
        }
    }

}

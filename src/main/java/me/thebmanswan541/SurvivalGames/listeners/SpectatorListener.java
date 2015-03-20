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
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import sun.font.ScriptRun;

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
        if (SurvivalGames.spectators.hasPlayer(p)) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                e.setCancelled(true);
                if (p.getItemInHand().getType() == Material.REDSTONE_COMPARATOR) {
                    p.openInventory(SurvivalGames.spectators.getGUI(p));
                } else if (p.getItemInHand().getType() == Material.COMPASS) {
                    p.openInventory(SurvivalGames.spectators.getTeleporterGUI().get(0));
                }
            }
        }
    }

    @EventHandler
    public void onStopFly(PlayerToggleFlightEvent e) {
        if (SurvivalGames.spectators.hasPlayer(e.getPlayer())) {
            if (SurvivalGames.spectators.getAlwaysFlying(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (SurvivalGames.spectators.hasPlayer(p) || SurvivalGames.arena.isState(Arena.ArenaState.WAITING) || SurvivalGames.arena.isState(Arena.ArenaState.LOBBY_COUNTDOWN) || SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN)) {
            e.setCancelled(true);
            // For the spectator inventory
            if (e.getInventory().getName().contains("Spectator")) {
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
                        if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                            p.removePotionEffect(PotionEffectType.SPEED);
                        }
                        p.setFlySpeed(0.1F);
                        p.sendMessage(ChatColor.RED + "You no longer have any speed effects!");
                    } else if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS) {
                        if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                            p.removePotionEffect(PotionEffectType.SPEED);
                        }
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                        p.setFlySpeed(0.11F);
                        p.sendMessage(ChatColor.GREEN + "You now have Speed I!");
                    } else if (e.getCurrentItem().getType() == Material.IRON_BOOTS) {
                        if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                            p.removePotionEffect(PotionEffectType.SPEED);
                        }
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
                        p.setFlySpeed(0.12F);
                        p.sendMessage(ChatColor.GREEN + "You now have Speed II!");
                    } else if (e.getCurrentItem().getType() == Material.GOLD_BOOTS) {
                        if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                            p.removePotionEffect(PotionEffectType.SPEED);
                        }
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
                        p.setFlySpeed(0.13F);
                        p.sendMessage(ChatColor.GREEN + "You now have Speed III!");
                    } else if (e.getCurrentItem().getType() == Material.DIAMOND_BOOTS) {
                        if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                            p.removePotionEffect(PotionEffectType.SPEED);
                        }
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
                        p.setFlySpeed(0.14F);
                        p.sendMessage(ChatColor.GREEN + "You now have Speed IV!");
                    } else if (e.getCurrentItem().getType() == Material.COMPASS) {
                        if (SurvivalGames.spectators.getAutoTeleport(p)) {
                            SurvivalGames.spectators.setAutoTeleport(p, false);
                            p.sendMessage(ChatColor.RED + "You will no longer auto teleport to targets!");
                        } else {
                            SurvivalGames.spectators.setAutoTeleport(p, true);
                            p.sendMessage(ChatColor.GREEN + "Once you select a player using your compass, it will auto teleport you to them!");
                        }
                    } else if (e.getCurrentItem().getType() == Material.ENDER_PEARL) {
                        SurvivalGames.spectators.setNightVision(p, false);
                        p.sendMessage(ChatColor.RED + "You no longer have night vision!");
                        p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    } else if (e.getCurrentItem().getType() == Material.EYE_OF_ENDER) {
                        SurvivalGames.spectators.setNightVision(p, true);
                        p.sendMessage(ChatColor.GREEN + "You now have night vision!");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 4));
                    } else if (e.getCurrentItem().getType() == Material.REDSTONE) {
                        SurvivalGames.spectators.setShowSpectators(p, false);
                        p.sendMessage(ChatColor.RED + "You can no longer see other spectators!");
                        for (Player pl : SurvivalGames.spectators.getSpectators()) {
                            p.hidePlayer(pl);
                        }
                    } else if (e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {
                        SurvivalGames.spectators.setShowSpectators(p, true);
                        p.sendMessage(ChatColor.GREEN + "You can now see other spectators!");
                        for (Player pl : SurvivalGames.spectators.getSpectators()) {
                            p.showPlayer(pl);
                        }
                    } else if (e.getCurrentItem().getType() == Material.FEATHER) {
                        if (SurvivalGames.spectators.getAlwaysFlying(p)) {
                            SurvivalGames.spectators.setAlwaysFlying(p, false);
                            p.setFlying(false);
                            p.sendMessage(ChatColor.RED + "You are now able to stop flying!");
                        } else {
                            SurvivalGames.spectators.setAlwaysFlying(p, true);
                            p.setFlying(true);
                            p.sendMessage(ChatColor.GREEN + "You are no longer able to stop flying!");
                        }
                    }
                    p.closeInventory();
                }
            } else if (e.getInventory().getName().contains("Teleporter")) {

            }
        }
    }

}

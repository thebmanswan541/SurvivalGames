package me.thebmanswan541.SurvivalGames.listeners;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.kits.Kit;
import me.thebmanswan541.SurvivalGames.managers.KitManager;
import me.thebmanswan541.SurvivalGames.util.Arena;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class KitSelector implements Listener{

    public static HashMap<Player, Kit> selectedKit = new HashMap<Player, Kit>();
    public static ItemStack kitSelector = new ItemStack(Material.PAPER); {
        ItemMeta meta = kitSelector.getItemMeta();
        meta.setDisplayName(ChatColor.RED+"Kit Selector "+ChatColor.GRAY+"(Right-Click)");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY+"Right-click to open the kit selector.");
        meta.setLore(lore);
        kitSelector.setItemMeta(meta);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN)) {
                Player p = e.getPlayer();
                if (p.getItemInHand().getType() == Material.PAPER) {
                    e.setCancelled(true);
                    p.openInventory(KitManager.getInstance().getGui());
                }
            }
        }
    }

    @EventHandler
    public void onKitSelect(InventoryClickEvent e) {
        if (e.getInventory().getName().contains("Kit Selector")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem().getType() != null) {
                if (e.getCurrentItem().getType() == Material.BOW) {
                    if (selectedKit.containsKey(p)) {
                        selectedKit.remove(p);
                    }
                    selectedKit.put(p, KitManager.getInstance().getKit(1));
                    p.closeInventory();
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"Selected the "+ChatColor.GREEN+"Archer"+ChatColor.YELLOW+" kit! You will recieve your kit 60 seconds after the game starts!");
                } else if (e.getCurrentItem().getType() == Material.WOOD_SWORD) {
                    if (selectedKit.containsKey(p)) {
                        selectedKit.remove(p);
                    }
                    selectedKit.put(p, KitManager.getInstance().getKit(2));
                    p.closeInventory();
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"Selected the "+ChatColor.GREEN+"Swordsman"+ChatColor.YELLOW+" kit! You will recieve your kit 60 seconds after the game starts!");
                } else if (e.getCurrentItem().getType() == Material.GLASS_BOTTLE) {
                    if (selectedKit.containsKey(p)) {
                        selectedKit.remove(p);
                    }
                    selectedKit.put(p, KitManager.getInstance().getKit(3));
                    p.closeInventory();
                    p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"Selected the "+ChatColor.GREEN+"Chemist"+ChatColor.YELLOW+" kit! You will recieve your kit 60 seconds after the game starts!");
                }
            }
        }
    }

}

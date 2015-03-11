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
    public static ItemStack kitSelector = new ItemStack(Material.BOW); {
        ItemMeta meta = kitSelector.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"Kit Selector "+ChatColor.GRAY+"(Right-Click)");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY+"Right-click to select a kit!");
        meta.setLore(lore);
        kitSelector.setItemMeta(meta);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN)) {
                Player p = e.getPlayer();
                if (p.getItemInHand().getType() == Material.BOW) {
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
                for (Kit kit : KitManager.getInstance().getKits()) {
                    if (e.getCurrentItem().getType() == kit.getKitIcon().getType()) {
                        if (selectedKit.containsKey(p)) {
                            selectedKit.remove(p);
                        }
                        selectedKit.put(p, kit);
                        p.closeInventory();
                        p.sendMessage(SurvivalGames.tag+ChatColor.YELLOW+"You have chosen the "+ChatColor.GREEN+kit.getName()+ChatColor.YELLOW+" kit. You will get your items 60 seconds after the game starts.");
                        break;
                    }
                }
            }
        }
    }

}

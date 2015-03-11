package me.thebmanswan541.SurvivalGames.util;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
public class SpectatorList {

    private ArrayList<Player> players;
    private Inventory spectatorGUI;
    private ItemStack teleporter = new ItemStack(Material.COMPASS); {
        ItemMeta meta = teleporter.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"§lTeleporter"+ChatColor.RESET+ChatColor.GRAY+" (Right-Click)");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY+"Right-click to spectate players!");
        meta.setLore(lore);
        teleporter.setItemMeta(meta);
    }
    private ItemStack spectatorSettings = new ItemStack(Material.REDSTONE_COMPARATOR); {
        ItemMeta meta = spectatorSettings.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA+"§lSpectator Settings "+ChatColor.RESET+ChatColor.GRAY+"(Right Click)");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY+"Right-click to change your spectator settings!");
        meta.setLore(lore);
        spectatorSettings.setItemMeta(meta);
    }
    private ItemStack speed0 = new ItemStack(Material.LEATHER_BOOTS); {
        ItemMeta meta = speed0.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"No Speed");
        speed0.setItemMeta(meta);
    }

    private ItemStack speed1 = new ItemStack(Material.CHAINMAIL_BOOTS); {
        ItemMeta meta = speed1.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"Speed I");
        speed1.setItemMeta(meta);
    }

    private ItemStack speed2 = new ItemStack(Material.IRON_BOOTS); {
        ItemMeta meta = speed2.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"Speed II");
        speed2.setItemMeta(meta);
    }
    private ItemStack speed3 = new ItemStack(Material.GOLD_BOOTS); {
        ItemMeta meta = speed3.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"Speed III");
        speed3.setItemMeta(meta);
    }

    private ItemStack speed4 = new ItemStack(Material.DIAMOND_BOOTS); {
        ItemMeta meta = speed4.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"Speed IV");
        speed4.setItemMeta(meta);
    }


    public SpectatorList() {
        this.players = new ArrayList<Player>();
        this.spectatorGUI = Bukkit.createInventory(null, 27, "Spectator Settings");
        spectatorGUI.setItem(11, speed0);
        spectatorGUI.setItem(12, speed1);
        spectatorGUI.setItem(13, speed2);
        spectatorGUI.setItem(14, speed3);
        spectatorGUI.setItem(15, speed4);
    }

    public ArrayList<Player> getSpectators() {
        return players;
    }

    public Inventory getGUI(Player p) {
        ItemStack disableTeleport = new ItemStack(Material.COMPASS); {
            ItemMeta meta = disableTeleport.getItemMeta();
            meta.setDisplayName(ChatColor.RED+"Disable Auto Teleport");
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GRAY+"Click to disable Auto");
        }
        return spectatorGUI;
    }

    public boolean hasPlayer(Player player) {
        return players.contains(player);
    }

    public void addPlayer(Player player) {
        players.add(player);

        PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
        ((CraftPlayer) player).getHandle().playerConnection.a(packet);
        player.sendMessage(ChatColor.GREEN + "You died, use your compass to spectate players!");
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, Integer.MAX_VALUE));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, Integer.MAX_VALUE));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
        player.getInventory().setItem(8, SurvivalGames.exitToLobby);
        player.getInventory().setItem(4, spectatorSettings);
        player.getInventory().setItem(0, teleporter);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

}

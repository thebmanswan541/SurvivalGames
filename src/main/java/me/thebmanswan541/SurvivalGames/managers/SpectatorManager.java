package me.thebmanswan541.SurvivalGames.managers;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.listeners.KitSelector;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
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
public class SpectatorManager {

    private ArrayList<Player> players;
    private Inventory spectatorGUI;
    private HashMap<Player, Boolean> autoTeleport;
    private HashMap<Player, Boolean> nightVision;
    private HashMap<Player, Boolean> spectators;
    private HashMap<Player, Boolean> alwaysFlying;
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


    public SpectatorManager() {
        this.players = new ArrayList<Player>();
        this.spectatorGUI = Bukkit.createInventory(null, 36, "Spectator Settings");
        this.autoTeleport = new HashMap<Player, Boolean>();
        this.nightVision = new HashMap<Player, Boolean>();
        this.spectators = new HashMap<Player, Boolean>();
        this.alwaysFlying = new HashMap<Player, Boolean>();
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
        ItemStack autoTeleportIcon = new ItemStack(Material.COMPASS); {
            ItemMeta meta = autoTeleportIcon.getItemMeta();
            List<String> lore = new ArrayList<String>();
            if (autoTeleport.get(p)) {
                meta.setDisplayName(ChatColor.RED + "Disable Auto Teleport");
                lore.add(ChatColor.GRAY + "Click to disable auto teleport!");
            } else {
                meta.setDisplayName(ChatColor.GREEN + "Enable Auto Teleport");
                lore.add(ChatColor.GRAY + "Click to enable auto teleport!");
            }
            meta.setLore(lore);
            autoTeleportIcon.setItemMeta(meta);
        }
        ItemStack nightVisionIcon;
        if (nightVision.get(p)) {
            nightVisionIcon = new ItemStack(Material.ENDER_PEARL); {
                ItemMeta meta = nightVisionIcon.getItemMeta();
                meta.setDisplayName(ChatColor.RED+"Disable Night Vision");
                List<String> lore = new ArrayList<String>();
                lore.add(ChatColor.GRAY+"Click to disable night vision!");
                meta.setLore(lore);
                nightVisionIcon.setItemMeta(meta);
            }
        } else {
            nightVisionIcon = new ItemStack(Material.EYE_OF_ENDER); {
                ItemMeta meta = nightVisionIcon.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN+"Enable Night Vision");
                List<String> lore = new ArrayList<String>();
                lore.add(ChatColor.GRAY+"Click to enable night vision!");
                meta.setLore(lore);
                nightVisionIcon.setItemMeta(meta);
            }
        }
        ItemStack spectatorsIcon;
        if (spectators.get(p)) {
            spectatorsIcon = new ItemStack(Material.REDSTONE); {
                ItemMeta meta = spectatorsIcon.getItemMeta();
                meta.setDisplayName(ChatColor.RED+"Hide Spectators");
                List<String> lore = new ArrayList<String>();
                lore.add(ChatColor.GRAY+"Click to hide other spectators!");
                meta.setLore(lore);
                spectatorsIcon.setItemMeta(meta);
            }
        } else {
            spectatorsIcon = new ItemStack(Material.GLOWSTONE_DUST); {
                ItemMeta meta = spectatorsIcon.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN+"Show Spectators");
                List<String> lore = new ArrayList<String>();
                lore.add(ChatColor.GRAY+"Click to show other spectators!");
                meta.setLore(lore);
                spectatorsIcon.setItemMeta(meta);
            }
        }
        ItemStack alwaysFlyingIcon = new ItemStack(Material.FEATHER); {
            ItemMeta meta = alwaysFlyingIcon.getItemMeta();
            List<String> lore = new ArrayList<String>();
            if (alwaysFlying.get(p)) {
                meta.setDisplayName(ChatColor.RED+"Disable Always Flying");
                lore.add(ChatColor.GRAY+"Click to disable always flying!");
            } else {
                meta.setDisplayName(ChatColor.GREEN+"Enable Always Flying");
                lore.add(ChatColor.GRAY+"Click to enable always flying!");
            }
            meta.setLore(lore);
            alwaysFlyingIcon.setItemMeta(meta);
        }
        spectatorGUI.setItem(24, alwaysFlyingIcon);
        spectatorGUI.setItem(23, spectatorsIcon);
        spectatorGUI.setItem(20, autoTeleportIcon);
        spectatorGUI.setItem(21, nightVisionIcon);
        return spectatorGUI;
    }

    public ArrayList<Inventory> getTeleporterGUI() {
        //TODO: Add a pages with next/prev page itemstacks
        ArrayList<Player> alreadyIn = new ArrayList<Player>();
        ArrayList<Inventory>  invs = new ArrayList<Inventory>();
        invs.add(Bukkit.createInventory(null, 27, "Teleporter"));
        int pages = SurvivalGames.arena.getPlayers().size() % 21;
        for (int i = 0; i < pages; i++) {
            invs.add(Bukkit.createInventory(null, 27, "Teleporter"));
        }
        for (Inventory inv : invs) {
            inv.setItem(1, new ItemStack(Material.FEATHER));
            inv.setItem(9, new ItemStack(Material.FEATHER));
            inv.setItem(18, new ItemStack(Material.FEATHER));
            inv.setItem(8, new ItemStack(Material.FEATHER));
            inv.setItem(17, new ItemStack(Material.FEATHER));
            inv.setItem(26, new ItemStack(Material.FEATHER));
            for (Player p : SurvivalGames.arena.getPlayers()) {
                if (!alreadyIn.contains(p)) {
                    ItemStack player = new ItemStack(Material.SKULL_ITEM); {
                        SkullMeta meta = (SkullMeta) player.getItemMeta();
                        meta.setOwner(p.getName());
                        meta.setDisplayName(p.getDisplayName());
                        ArrayList<String> lore = new ArrayList<String>();
                        lore.add(ChatColor.GRAY+"Kit: "+ KitSelector.getSelectedKit(p).getName());
                        lore.add("§");
                        lore.add(ChatColor.GRAY+"Click to spectate!");
                        meta.setLore(lore);
                        player.setItemMeta(meta);
                    }
                    inv.addItem(player);
                }
                alreadyIn.add(p);
            }
        }
        return invs;
    }

    public boolean hasPlayer(Player player) {
        return players.contains(player);
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.sendMessage(ChatColor.GREEN + "You died, use your compass to spectate players!");
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.getInventory().setItem(8, SurvivalGames.exitToLobby);
        player.getInventory().setItem(4, spectatorSettings);
        player.getInventory().setItem(0, teleporter);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Boolean getAutoTeleport(Player p) {
        return autoTeleport.get(p);
    }

    public void setAutoTeleport(Player p, boolean value) {
        autoTeleport.put(p, value);
    }

    public Boolean getNightVision(Player p) {
        return nightVision.get(p);
    }

    public void setNightVision(Player p, boolean value) {
        nightVision.put(p, value);
    }

    public Boolean getShowSpectators(Player p) {
        return spectators.get(p);
    }

    public void setShowSpectators(Player p, boolean value) {
        spectators.put(p, value);
    }

    public Boolean getAlwaysFlying(Player p) {
        return alwaysFlying.get(p);
    }

    public void setAlwaysFlying(Player p, boolean value) {
        alwaysFlying.put(p, value);
    }

}

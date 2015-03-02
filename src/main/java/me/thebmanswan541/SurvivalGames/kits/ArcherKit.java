package me.thebmanswan541.SurvivalGames.kits;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
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
public class ArcherKit extends Kit{

    private List<ItemStack> items;
    private ItemStack kitIcon;

    public ArcherKit() {
        super(1);
        this.kitIcon = new ItemStack(Material.BOW, 1); {
            ItemMeta meta = kitIcon.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN+"Archer");
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GRAY+"Used for ranged attacks.");
            meta.setLore(lore);
            kitIcon.setItemMeta(meta);
        }
        this.items = new ArrayList<ItemStack>();
        this.items.add(new ItemStack(Material.BOW));
        this.items.add(new ItemStack(Material.ARROW, 16));
        this.items.add(new ItemStack(Material.COOKED_BEEF, 2));
    }


    @Override
    public String getName() {
        return "Archer";
    }

    @Override
    public List<ItemStack> getItems() {
        return items;
    }

    @Override
    public ItemStack getKitIcon() {
        return kitIcon;
    }
}

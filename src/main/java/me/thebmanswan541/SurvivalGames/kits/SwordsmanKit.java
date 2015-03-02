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
public class SwordsmanKit extends Kit{
    private ItemStack kitIcon;
    private List<ItemStack> items;

    public SwordsmanKit() {
        super(2);
        this.kitIcon = new ItemStack(Material.WOOD_SWORD); {
            ItemMeta meta = kitIcon.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN+"Swordsman");
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GRAY+"Close range combat");
            meta.setLore(lore);
            kitIcon.setItemMeta(meta);
        }
        this.items = new ArrayList<ItemStack>();
    }

    @Override
    public String getName() {
        return "Swordsman";
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

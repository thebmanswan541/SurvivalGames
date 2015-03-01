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
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class ArcherKit extends Kit{

    private int id;
    List<ItemStack> items = new ArrayList<ItemStack>();
    ItemStack kitIcon = new ItemStack(Material.BOW, 1); {
        ItemMeta meta = kitIcon.getItemMeta();
        meta.setDisplayName(ChatColor.RED+"Archer Kit");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY+"- "+ChatColor.RED+"1 Bow");
        lore.add(ChatColor.GRAY+"- "+ChatColor.RED+"2 Steak");
        lore.add(ChatColor.GRAY+"- "+ChatColor.RED+"16 Arrows");
        meta.setLore(lore);
        kitIcon.setItemMeta(meta);
    }

    public ArcherKit(int id) {
        super(id);
        this.id = id;
    }


    @Override
    public String getName() {
        return "Archer Kit";
    }

    @Override
    public List<ItemStack> getItems() {
        return items;
    }

    @Override
    public ItemStack getKitIcon() {
        return kitIcon;
    }

    @Override
    public Integer getID() {
        return id;
    }
}

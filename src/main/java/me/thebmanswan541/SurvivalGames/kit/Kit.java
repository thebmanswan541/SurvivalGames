package me.thebmanswan541.SurvivalGames.kit;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly
 * prohibited. Thanks.
 * **********************************************************
 */
public class Kit {

    private String name;
    private int id;
    private ItemStack kitIcon;
    private List<ItemStack> items;
    private String iconName;
    private List<String> iconLore;

    public Kit(String name, int id, ItemStack kitIcon, List<ItemStack> items, String iconName, List<String> iconLore) {
        this.name = name;
        this.id = id;
        this.kitIcon = kitIcon;
        this.items = items;
        this.iconName = iconName;
        kitIcon.getItemMeta().setDisplayName(this.iconName);
        this.iconLore = iconLore;
        kitIcon.getItemMeta().setLore(this.iconLore);
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public ItemStack getKitIcon() {
        return kitIcon;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public String getIconName() {
        return iconName;
    }

    public List<String> getIconLore() {
        return iconLore;
    }

    public String toString() {
        return "Kit: "+getName() + " | ID: "+getId();
    }
}

package me.thebmanswan541.SurvivalGames.kits;

import org.bukkit.inventory.ItemStack;

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
public abstract class Kit {

    private int id;

    public Kit(int id) {
        this.id = id;
    }

    public abstract String getName();

    public abstract Integer getID();

    public abstract ItemStack getKitIcon();

    public abstract List<ItemStack> getItems();
}

package me.thebmanswan541.SurvivalGames.managers;

import me.thebmanswan541.SurvivalGames.kits.ArcherKit;
import me.thebmanswan541.SurvivalGames.kits.ChemistKit;
import me.thebmanswan541.SurvivalGames.kits.Kit;
import me.thebmanswan541.SurvivalGames.kits.SwordsmanKit;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class KitManager {

    private static KitManager instance = new KitManager();

    public static KitManager getInstance() {
        return instance;
    }

    private ArrayList<Kit> kits;
    private Inventory gui;


    private KitManager() {
        kits = new ArrayList<Kit>();
        kits.add(new ArcherKit());
        kits.add(new SwordsmanKit());
        kits.add(new ChemistKit());

        gui = Bukkit.createInventory(null, 36, "Kit Selector");
        int index = 11;
        for (Kit kit : kits) {
            gui.setItem(index, kit.getKitIcon());
            index++; index++;
        }
    }

    public ArrayList<Kit> getKits() {
        return kits;
    }

    public Kit getKit(String name) {
        for (Kit kit : kits) {
            if (kit.getName().equals(name)) {
                return kit;
            }
        }
        return null;
    }

    public Kit getKit(int id) {
        for (Kit kit : kits) {
            if (kit.getID().equals(id)) {
                return kit;
            }
        }
        return null;
    }

    public Inventory getGui() {
        return gui;
    }
}

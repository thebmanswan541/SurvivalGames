package me.thebmanswan541.SurvivalGames.util;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.exceptions.ArenaNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

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
public class Deathmatch {

    private CuboidSelection bounds;
    private ArrayList<Spawn> spawns;
    private ArrayList<Player> players;

    private Deathmatch() {
        Location cornerA = null;
        Location cornerB = null;
        try {
            cornerA = SurvivalGames.parseLocation(FileManager.getConfig().<ConfigurationSection>get("deathmatch.cornerA"));
            cornerB = SurvivalGames.parseLocation(FileManager.getConfig().<ConfigurationSection>get("deathmatch.cornerB"));
        } catch (ArenaNotFoundException e) {

        }
        this.bounds = new CuboidSelection(Bukkit.getWorld(FileManager.getConfig().<String>get("deathmatch.world")), cornerA, cornerB);
        this.players = new ArrayList<Player>();
        this.spawns = new ArrayList<Spawn>();
        if (FileManager.getConfig().contains("deathmatch.spawns")) {
            for (String spawnID : FileManager.getConfig().<ConfigurationSection>get("deathmatch.spawns").getKeys(false)) {
                try {
                    this.spawns.add(new Spawn(SurvivalGames.parseLocation(FileManager.getConfig().<ConfigurationSection>get("deathmatch.spawns." + spawnID))));
                } catch (ArenaNotFoundException e) {

                }
            }
        }

    }

    static Deathmatch instance = new Deathmatch();

    public static Deathmatch getInstance() {
        return instance;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean containsPlayer(Player player) {
        return this.players.contains(player);
    }

    public void addPlayer(Player player) {
        for (Spawn spawn : spawns) {
            if (!spawn.hasPlayer()) {
                spawn.setPlayer(player);
                player.teleport(spawn.getLocation());
                break;
            }
        }
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void addSpawn(Location location) {
        this.spawns.add(new Spawn(location));
    }

    public CuboidSelection getBounds() {
        return bounds;
    }
}

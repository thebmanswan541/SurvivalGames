package me.thebmanswan541.SurvivalGames.util;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.exceptions.ArenaNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

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
public class Arena {

    public enum ArenaState {
        WAITING(ChatColor.WHITE, "Waiting..."), LOBBY_COUNTDOWN(ChatColor.BLUE, "Lobby Countdown"), START_COUNTDOWN(ChatColor.YELLOW, "Start Countdown"), RECEIVE_KIT(ChatColor.GOLD, "Receive kit"), IN_GAME(ChatColor.RED, "In-Game"), DEATHMATCH(ChatColor.RED, "Deathmatch"), RESETTING(ChatColor.WHITE, "Resetting...");

        private String name;
        private ChatColor color;

        ArenaState(ChatColor color, String name) {
            this.color = color;
            this.name = name;
        }

        public String getName() {
            return color+name;
        }
    }

    private ArenaState state;
    private String id;
    private CuboidSelection bounds;
    private ArrayList<Player> players;
    private ArrayList<Spawn> spawns;

    /**
     * @param id Name of arena.
     */
    public Arena(String id) {
        this.id = id;
        Location cornerA = null;
        Location cornerB = null;
        try {
            cornerA = SurvivalGames.parseLocation(FileManager.getArenas().<ConfigurationSection>get(id + ".cornerA"));
            cornerB = SurvivalGames.parseLocation(FileManager.getArenas().<ConfigurationSection>get(id + ".cornerB"));
        } catch(ArenaNotFoundException e) {

        }
        this.bounds = new CuboidSelection(Bukkit.getWorld(FileManager.getArenas().<String>get(id + ".world")),
                cornerA,
                cornerB
        );
        this.state = ArenaState.WAITING;
        this.players = new ArrayList<Player>();
        this.spawns = new ArrayList<Spawn>();
        if (FileManager.getArenas().contains(id+".spawns")) {
            for (String spawnId : FileManager.getArenas().<ConfigurationSection>get(id+".spawns").getKeys(false)) {
                Location spawn = null;
                try {
                    spawn = SurvivalGames.parseLocation(FileManager.getArenas().<ConfigurationSection>get(id + ".spawns." + spawnId));
                }catch(ArenaNotFoundException e) {

                }
                spawns.add(new Spawn(spawn));
            }
        }
    }

    public String getID() {
        return id;
    }

    public CuboidSelection getBounds() {
        return bounds;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean containsPlayer(Player player) {
        return this.players.contains(player);
    }

    /**
     * @param player Adds player to the game.
     */
    public void addPlayer(Player player) {
        for (Spawn spawn : spawns) {
            if (!spawn.hasPlayer()) {
                spawn.setPlayer(player);
                player.teleport(spawn.getLocation());
                break;
            }
        }

        players.add(player);
        player.getInventory().clear();

    }

    /**
     * @param player Removes player from the game.
     */
    public void removePlayer(Player player) {
        players.remove(player);
        SurvivalGames.spectators.addPlayer(player);
        // TODO: More information below
    }

    public void addSpawn(Location location) {
        spawns.add(new Spawn(location));
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public boolean isState(ArenaState state) {
        return this.state == state;
    }


}

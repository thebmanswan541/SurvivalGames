package me.thebmanswan541.SurvivalGames.util;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
public class Arena {

    public enum ArenaState {
        WAITING(ChatColor.WHITE, "Waiting..."), COUNTDOWN(ChatColor.BLUE, "Countdown"), STARTING(ChatColor.YELLOW, "Starting..."), GRACE_PERIOD(ChatColor.AQUA, "Grace Period"), IN_GAME(ChatColor.GOLD, "In-Game"), RESTARTING(ChatColor.RED, "Restarting...");

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
    private ArrayList<Player> players;
    private ArrayList<Spawn> spawns;

    /**
     * @param id Name of arena.
     */
    protected Arena(String id) {
        this.id = id;
        this.state = ArenaState.WAITING;
        this.players = new ArrayList<Player>();
        this.spawns = new ArrayList<Spawn>();
        if (FileManager.getArenas().contains(id+".spawns")) {
            for (String spawnId : FileManager.getArenas().<ConfigurationSection>get(id+".spawns").getKeys(false)) {
                spawns.add(new Spawn(SurvivalGames.parseLocation(FileManager.getArenas().<ConfigurationSection>get(id+".spawns."+spawnId))));
            }
        }
    }

    public String getID() {
        return id;
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
        if (isState(ArenaState.STARTING) || isState(ArenaState.IN_GAME)) {
            player.sendMessage(SurvivalGames.tag + ChatColor.RED + "The game has already started!");
        } else if (isState(ArenaState.RESTARTING)) {
            player.sendMessage(SurvivalGames.tag+ChatColor.RED+"This server is currently restarting.");
        } else if (players.size() +1 > spawns.size()) {
            player.sendMessage(SurvivalGames.tag+ChatColor.RED+"This game is currently full.");
        }

        // TODO: add more filters

        players.add(player);
    }

    /**
     * @param player Removes player from the game.
     */
    public void removePlayer(Player player) {
        players.remove(player);

        // TODO: More information below
    }

    public List<Spawn> getSpawns() {
        return spawns;
    }

    public ArenaState getState() {
        return state;
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public boolean isState(ArenaState state) {
        return this.state == state;
    }


}

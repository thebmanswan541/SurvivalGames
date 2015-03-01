package me.thebmanswan541.SurvivalGames.managers;

import me.thebmanswan541.SurvivalGames.util.Arena;
import me.thebmanswan541.SurvivalGames.util.FileManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using the for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class ArenaManager {

    private ArrayList<Arena> arenas;

    private ArenaManager(){
        this.arenas = new ArrayList<Arena>();
    }

    static ArenaManager instance = new ArenaManager();

    public static ArenaManager getInstance() {
        return instance;
    }

    public void setup() {
        arenas.clear();

        for (String arenaId : FileManager.getArenas().getKeys()) {
            arenas.add(new Arena(arenaId));
        }
    }

    public Arena getArena(String id) {
        for (Arena arena : arenas) {
            if (arena.getID().equals(id)) {
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(Player player) {
        for (Arena arena : arenas) {
            if (arena.containsPlayer(player)) {
                return arena;
            }
        }
        return null;
    }

    public ArrayList<Arena> getArenas() {
        return arenas;
    }

    public Arena getActiveArena() {
        Random r = new Random();
        int index = r.nextInt(getArenas().size());
        Arena a = getArenas().get(index);
        return a;
    }

}

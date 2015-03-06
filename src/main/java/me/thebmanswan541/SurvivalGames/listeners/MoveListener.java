package me.thebmanswan541.SurvivalGames.listeners;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import me.thebmanswan541.SurvivalGames.util.Arena;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class MoveListener implements Listener {

    @EventHandler
    public void Move(PlayerMoveEvent e){
        if (SurvivalGames.arena != null) {
            if (SurvivalGames.arena.isState(Arena.ArenaState.START_COUNTDOWN)) {
                Location loc = e.getFrom();
                if (e.getTo().getY() != loc.getY() || e.getTo().getZ() != loc.getZ() || e.getTo().getX() != loc.getX()) {
                    loc.setY(e.getTo().getY());
                    loc.setPitch(e.getTo().getPitch());
                    loc.setYaw(e.getTo().getYaw());
                    e.setTo(loc);
                }
            }
        }
    }
}

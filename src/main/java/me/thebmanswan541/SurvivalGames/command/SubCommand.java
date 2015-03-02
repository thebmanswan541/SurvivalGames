package me.thebmanswan541.SurvivalGames.command;

import org.bukkit.entity.Player;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public interface SubCommand {

    /**
     * @param player Command sender
     * @param args SubCommand arguments
     */
    public boolean onCommand(Player player, String[] args);

    /**
     * @param p Sends a help message of a specified SubCommand to the player
     */
    public String help(Player p);
}

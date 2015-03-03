package me.thebmanswan541.SurvivalGames.exceptions;

import net.md_5.bungee.api.ChatColor;

/**
 * **********************************************************
 * Project: SurvivalGames
 * Copyright TheBmanSwan (c) 2015. All Rights Reserved.
 * Upon using this for commercial use, the user must give
 * credit to TheBmanSwan. Distribution of the code is allowed
 * Claiming this project to be created by you is strictly prohibited.
 * **********************************************************
 */
public class ArenaNotFoundException extends Exception{

    public ArenaNotFoundException() {
        super(ChatColor.RED+"The main arena has not been set up yet!");
    }

    public ArenaNotFoundException(String message) {
        super(message);
    }

    public ArenaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArenaNotFoundException(Throwable cause) {
        super(cause);
    }

}

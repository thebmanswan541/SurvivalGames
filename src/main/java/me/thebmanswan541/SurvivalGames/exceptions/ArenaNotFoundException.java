package me.thebmanswan541.SurvivalGames.exceptions;

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
public class ArenaNotFoundException extends Exception {

    public ArenaNotFoundException() {
        super("Could not find the arena with the specified ID!");
    }

    public ArenaNotFoundException(String message) {
        super(message);
    }

}

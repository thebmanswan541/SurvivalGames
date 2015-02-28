package me.thebmanswan541.SurvivalGames.util;

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
        WAITING, COUNTDOWN, STARTING, IN_GAME, RESTARTING;
    }

    private ArenaState state;
    private String id;

    protected Arena(String id) {
        this.id = id;
        this.state = ArenaState.WAITING;
    }

    public String getID() {
        return id;
    }

}

package me.thebmanswan541.SurvivalGames.util;

import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
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
public class SpectatorList {

    private ArrayList<Player> players;

    public SpectatorList() {
        this.players = new ArrayList<Player>();
    }

    public ArrayList<Player> getSpectators() {
        return players;
    }

    public boolean hasPlayer(Player player) {
        return players.contains(player);
    }

    public void addPlayer(Player player) {
        players.add(player);

        PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
        ((CraftPlayer) player).getHandle().playerConnection.a(packet);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

}

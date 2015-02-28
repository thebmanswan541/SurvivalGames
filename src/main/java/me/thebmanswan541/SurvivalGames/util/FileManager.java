package me.thebmanswan541.SurvivalGames.util;

import me.thebmanswan541.SurvivalGames.SurvivalGames;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

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
public class FileManager {

    private static final FileManager conf = new FileManager("config"), arenas = new FileManager("arenas");

    public FileManager getConfig() {
        return conf;
    }

    public FileManager getArenas() {
        return arenas;
    }

    File f;
    FileConfiguration config;

    private FileManager(String path) {
        f = new File(SurvivalGames.getPlugin().getDataFolder(), path+".yml");

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(f);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String path) {
        return (T) config.get(path);
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }
    public ConfigurationSection createSection(String path) {
        ConfigurationSection cs = config.createSection(path);
        save();
        return cs;
    }

    public boolean contains(String path) {
        return config.contains(path);
    }

    private void save() {
        try {
            config.save(f);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}

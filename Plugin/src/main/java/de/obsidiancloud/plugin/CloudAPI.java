package de.obsidiancloud.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class CloudAPI extends JavaPlugin {
    private static CloudAPI instance;

    public static CloudAPI getInstance() {
        return instance;
    }

    public static CloudAPI getAPI() {
        return instance;
    }
}

package de.obsidiancloud.addon;

import de.obsidiancloud.common.command.BaseCommandProvider;

public class Addon extends BaseCommandProvider {
    private AddonManifest manifest;

    public void onEnable() {}
    public void onDisable() {}

    /**
     * Gets the manifest of the addon
     *
     * @return The manifest of the addon
     */
    public AddonManifest getManifest() {
        return manifest;
    }
}
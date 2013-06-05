/*
 * This file is part of Realms.
 *
 * Copyright (c) 2012, AlmuraDev <http://www.almuradev.com/>
 * Realms is licensed under the Almura Development License.
 *
 * Realms is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Realms is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License. If not,
 * see <http://www.gnu.org/licenses/> for the GNU General Public License.
 */
package com.almuradev.realms;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class RealmsConfiguration {
    private final RealmsPlugin plugin;
    private FileConfiguration config;
    private String fallbackWorld;
    private String denyMessage;
    private List<String> worldNames;

    public RealmsConfiguration(RealmsPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        // Read in default config.yml
        if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
            plugin.saveDefaultConfig();
        }
        config = plugin.getConfig();
        fallbackWorld = config.getString("fallback-world");
        denyMessage = config.getString("deny-message");
        worldNames = config.getStringList("worlds");
    }

    public String getFallbackWorld() {
        return fallbackWorld;
    }

    public String getDenyMessage() {
        return denyMessage;
    }

    public List<String> getWorldNames() {
        return Collections.unmodifiableList(worldNames);
    }
}

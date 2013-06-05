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

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

import java.io.IOException;

public class RealmsPlugin extends JavaPlugin {
    private RealmsConfiguration config;

    @Override
    public void onEnable() {
        // Handle configuration
        config = new RealmsConfiguration(this);
        config.onEnable();

        // Register events
        Bukkit.getServer().getPluginManager().registerEvents(new RealmsListener(this), this);

        // Start metrics
        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {
        }
    }

    public RealmsConfiguration getConfiguration() {
        return config;
    }
}

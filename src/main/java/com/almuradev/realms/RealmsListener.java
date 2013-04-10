/*
 * This file is part of Realms.
 *
 * © 2013 AlmuraDev <http://www.almuradev.com/>
 * Realms is licensed under the GNU General Public License.
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
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.SpoutcraftFailedEvent;

public class RealmsListener implements Listener {
    private final RealmsPlugin plugin;

    public RealmsListener(RealmsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        String denyMessage = plugin.getConfiguration().getDenyMessage();

        // If the player is entering a portal to a world that isn't the one they were already in, run these checks.
        if (!player.getWorld().getName().equalsIgnoreCase(event.getTo().getWorld().getName())) {
            // Compare the worlds in config.yml against the world the player is in
            for (String worldName : plugin.getConfiguration().getWorldNames()) {
                if (worldName.equalsIgnoreCase(event.getTo().getWorld().getName())) {
                    // Check for permission or if they have Spoutcraft
                    if (!VaultUtil.hasPermission(player.getName(), event.getTo().getWorld().getName(), "realms.bypass") && !SpoutManager.getPlayer(player).isSpoutCraftEnabled()) {
                        event.setCancelled(true);
                        player.sendMessage(denyMessage);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpoutcraftFailed(SpoutcraftFailedEvent event) {
        Player player = event.getPlayer();
        String fallbackWorld = plugin.getConfiguration().getFallbackWorld();
        String denyMessage = plugin.getConfiguration().getDenyMessage();

        // Compare the worlds in config.yml against the world the player is in
        for (String worldName : plugin.getConfiguration().getWorldNames()) {
            if (worldName.equalsIgnoreCase(player.getWorld().getName())) {
                // Check for permission or if they have Spoutcraft
                if (!VaultUtil.hasPermission(player.getName(), player.getWorld().getName(), "realms.bypass") && !SpoutManager.getPlayer(player).isSpoutCraftEnabled()) {
                    if (fallbackWorld != null && !fallbackWorld.isEmpty()) {
                        final World world = Bukkit.getWorld(fallbackWorld);

                        if (world != null) {
                            player.teleport(world.getSpawnLocation());
                            player.sendMessage(denyMessage);
                        } else {
                            player.kickPlayer(denyMessage);
                        }
                    } else {
                        // Kick the player if the fallback world is null or is not loaded in the current session
                        player.kickPlayer(denyMessage);
                    }
                }
            }
        }
    }
}
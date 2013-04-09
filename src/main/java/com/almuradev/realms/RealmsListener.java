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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.getspout.spoutapi.SpoutManager;

public class RealmsListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        for (String world : RealmsConfiguration.worlds) {
            if (world.equalsIgnoreCase(player.getWorld().getName())) {
                if (!VaultUtil.hasPermission(player.getName(), player.getWorld().getName(), "realms.bypass")) { // Check for permission
                    event.setCancelled(true); // Causes timeout if the teleport cause is from logging in
                } else if (!SpoutManager.getPlayer(player).isSpoutCraftEnabled()) { // Check if they have Spoutcraft
                    event.setCancelled(true); // Causes timeout if the teleport cause is from logging in
                }
            }
        }
    }
}

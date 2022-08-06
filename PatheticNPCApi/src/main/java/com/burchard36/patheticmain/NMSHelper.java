package com.burchard36.patheticmain;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface NMSHelper {
    <T> List<T> getOnlineNmsPlayers();

    <T> T createPlayerEntity(NPCBuilder builder);

    <T> List<T> getNmsPlayersInRange(Location near, double range);
    <T> T convertToNms(Player player);
}

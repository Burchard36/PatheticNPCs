package com.burchard36.main.nms;

import com.burchard36.main.NPCBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface NMSHelper {
    <T> List<T> getOnlineNmsPlayers();

    <T> T createPlayerEntity(NPCBuilder builder);

    <T> List<T> getNmsPlayersInRange(Location near, double range);
    <T> T convertToNms(Player player);
}

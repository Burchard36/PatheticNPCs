package com.burchard36.patheticmain.nms;

import com.burchard36.patheticmain.impl.PatheticNPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public interface NMSHelper {



    <T> List<T> getOnlineNmsPlayers();

    <T> List<T> getNmsPlayersInRange(Location near, double range);
    <T> T convertToNms(Player player);

    <T extends PatheticNPC> T createPlayerEntity(NPCBuilder builder);

    /**
     * Gets the Helper for the version that the server is currently running.
     * @return A {@link NMSHelper} abstraction instance. Null if not supported for this version.
     */
    static NMSHelper get() {
        String version = Bukkit.getBukkitVersion();
        if (version.contains("1.19.0")) return new com.burchard36.patheticmain.nms.v1_19_0.NMSHelper1_19_0();
        return null;
    }


}

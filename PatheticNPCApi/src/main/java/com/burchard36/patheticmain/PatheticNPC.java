package com.burchard36.patheticmain;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Currently, only PLAYER type NPC's are supported
 */
public interface PatheticNPC extends PatheticPacketCrafter {


    EntityType getEntityType();

    boolean isVisible();

    double rangeToShow();

    /**
     * Sends async packets to nearby players of the NPC
     * @param spigotLocation {@link Location} of where to spawn the entity back
     * @param callbackFunction Sync callback (Runs on main thread)
     */
    void asyncSpawnIn(Location spigotLocation, @Nullable CallbackVoid callbackFunction);

    /**
     * A method used to update the entity, can be called every tick,
     *
     * WARNING When implementing, DO NOT create a new Future inside this, the method is called async
     * Inside a Controller.
     *
     * @param callbackVoid Sync callback (Runs on main thread)
     */
    void asyncTickEntity(@Nullable CallbackVoid callbackVoid);

}

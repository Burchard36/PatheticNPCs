package com.burchard36.patheticmain;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import javax.annotation.Nullable;
import java.util.UUID;

@Getter
public class NPCBuilder {

    protected UUID npcUuid;
    protected String npcName;
    protected EntityType npcEntityType;
    protected Location spawnLocation;

    /**
     * Using this constructor automatically sets EntityType to {@link org.bukkit.entity.EntityType} of {@link org.bukkit.entity.Player}
     * @param desiredUuid {@link UUID} Of the player you want, set to null for random
     * @param name {@link String} of the name you want the NPC to have, no longer than 16.
     */
    public NPCBuilder(@Nullable UUID desiredUuid, @NonNull String name) {
        if (name.length() > 16) throw new IllegalArgumentException("Name of NPC cannot be greater than 16! You provided: " + name);

        if (desiredUuid == null) desiredUuid = UUID.randomUUID();
        this.changeEntityType(EntityType.PLAYER);
        this.changeNpcName(name);
        this.changeUuid(desiredUuid);
    }

    /**
     * Changes the UUID of this entity, must not be null
     * @param uuid A {@link UUID}
     * @return Instance of this builder class
     */
    @SuppressWarnings("UnusedReturnValue")
    public NPCBuilder changeUuid(@NonNull UUID uuid) {
        this.npcUuid = uuid;
        return this;
    }

    /**
     * Changes the entities name, must not be null
     * @param npcName {@link String} of the NPC, no longer than 16 characters
     * @return Instance of this builder class
     */
    @SuppressWarnings("UnusedReturnValue")
    public NPCBuilder changeNpcName(@NonNull String npcName) {
        if (npcName.length() > 16) throw new IllegalArgumentException("NPC Name cannot be longer than 16 characters! You provided: " + npcName);
        this.npcName = npcName;
        return this;
    }

    /**
     * Changes the type of Entity this NPC is
     * @param type {@link EntityType} to set the entity to
     * @return Instance of this builder class
     */
    @SuppressWarnings("UnusedReturnValue")
    public NPCBuilder changeEntityType(@NonNull EntityType type) {
        this.npcEntityType = type;
        return this;
    }

    /**
     * Sets the origin location of where the NPC should be spawning at
     * @param spawnLocation {@link Location} of the location to spawn entity at
     * @return Instance of this builder class
     */
    @SuppressWarnings("UnusedReturnValue")
    public NPCBuilder provideSpawnLocation(@NonNull Location spawnLocation) {
        this.spawnLocation = spawnLocation;
        return this;
    }


}

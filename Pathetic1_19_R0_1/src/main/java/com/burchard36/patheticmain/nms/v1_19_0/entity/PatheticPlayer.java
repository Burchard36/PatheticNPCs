package com.burchard36.patheticmain.nms.v1_19_0.entity;

import com.burchard36.patheticmain.impl.CallbackVoid;
import com.burchard36.patheticmain.impl.PatheticNPC;
import com.burchard36.patheticmain.nms.NMSHelper;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.burchard36.patheticmain.PatheticPlugin.THREAD_POOL;
import static com.burchard36.patheticmain.util.TaskRunner.runTask;

/**
 * A thread-safe, async-laced, packet-based implementation of a NMS Player
 *
 * If you are an API user and are using this class, are you alright? Do you need therapy?
 * Come, talk to me
 */
public class PatheticPlayer implements PatheticNPC {

    protected final AtomicBoolean isVisible;
    protected final AtomicBoolean isAlive;
    protected final NMSHelper nmsHelper;
    protected final ServerPlayerWrapper playerEntity;

    public PatheticPlayer(
            Location atLocation,
            String npcName,
            NMSHelper helper) {
        this.playerEntity = new ServerPlayerWrapper(atLocation, UUID.randomUUID(), npcName);
        this.isVisible = new AtomicBoolean(true);
        this.isAlive = new AtomicBoolean(true);
        this.nmsHelper = helper;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.PLAYER;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public double rangeToShow() {
        return 16 * 3; // visible for 3 chunks, temporary number
    }

    @Override
    public void asyncSpawnIn(Location spigotLocation, @Nullable CallbackVoid callbackFunction) {
        List<ServerPlayer> onlinePlayers = this.nmsHelper.getOnlineNmsPlayers();
        List<ServerPlayer> nearbyPlayers = this.nmsHelper.getNmsPlayersInRange(spigotLocation, this.rangeToShow());
        this.playerEntity.updatePosition(spigotLocation.getX(), spigotLocation.getY(), spigotLocation.getZ());
        CompletableFuture.runAsync(() -> {
            /* Send to all players that this NPC is visible */
            ClientboundPlayerInfoPacket informationPacket = this.craftEntityAddPacket();
            onlinePlayers.forEach(p -> p.connection.send(informationPacket));

            /* Only show to players in radius */
            ClientboundAddPlayerPacket npcSpawnPacket = this.craftSpawnEntityPacket();
            nearbyPlayers.forEach(p -> p.connection.send(npcSpawnPacket));

            /* Again, only rotate the head for players in radius */
            ClientboundRotateHeadPacket npcHeadRotationPacket = this.craftEntityHeadRotationPacket();
            nearbyPlayers.forEach(p -> p.connection.send(npcHeadRotationPacket));

            if (callbackFunction != null) runTask(callbackFunction::onComplete);
        }, THREAD_POOL);
    }


    @Override
    public void asyncTickEntity(@Nullable CallbackVoid callbackVoid) {
        // TODO Fully impl

        //TODO We need to compare a list of entities in between each tick, EG "Viewers"
        //TODO If a player doesnt exists in the new list, stop rendering to the client
        //TODO However, if a new client is in the seperate Lists then we can render the entity for them as wel
        //TODO Lastly, any players that are still in the list, of course render to them as well.


        if (callbackVoid != null) runTask(callbackVoid::onComplete);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ClientboundPlayerInfoPacket craftEntityAddPacket() {
        return new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, this.playerEntity.get());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ClientboundAddPlayerPacket craftSpawnEntityPacket() {
        return new ClientboundAddPlayerPacket(this.playerEntity.get());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ClientboundRotateHeadPacket craftEntityHeadRotationPacket() {
        final ServerPlayer p = this.playerEntity.get();
        return new ClientboundRotateHeadPacket(p, (byte) ((p.getYHeadRot() * 256f) / 360f));
    }

    /**
     * Small helper class to handle the NMS player wrapped inside an Atomic Boolean.
     *
     * TODO: Make an interface for this
     */
    protected static class ServerPlayerWrapper {

        protected final AtomicReference<ServerPlayer> serverPlayerReferance;

        public ServerPlayerWrapper(
                Location atLocation,
                @Nullable UUID playerUuid,
                @NonNull String playerName
        ) {
            if (playerUuid == null) playerUuid = UUID.randomUUID();
            final ServerPlayer player =
                    new ServerPlayer(MinecraftServer.getServer(),
                            ((CraftWorld) atLocation.getWorld()).getHandle(),
                            new GameProfile(playerUuid, playerName),
                            null);
            this.serverPlayerReferance = new AtomicReference<>(player);
        }

        /**
         * Updates and gets the NMS Entity location, thread-safe, strictly for use in packets!!!!
         * @param x Real world x location
         * @param y Real world y location
         * @param z Real world z location
         * @return The NMS Server Player, value can be ignored, values are read-only
         */
        @SuppressWarnings("UnusedReturnValue")
        public ServerPlayer updatePosition(double x, double y, double z) {
            return this.serverPlayerReferance.getAndUpdate((serverPlayer) -> {
                serverPlayer.setPos(x, y, z);
                return serverPlayer;
            });
        }

        /**
         * Returns the stored ServerPlayer of this wrapper
         * @return The NMS Server Player, value should not be ignored, values are read-only
         */
        public ServerPlayer get() {
            return this.serverPlayerReferance.get();
        }

    }
}

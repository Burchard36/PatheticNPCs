package com.burchard36.main.nms.v1_19_0;

import com.burchard36.main.nms.NMSHelper;
import com.burchard36.main.NPCBuilder;
import com.burchard36.main.nms.v1_19_0.entity.PatheticPlayer;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NMSHelper1_19_0 implements NMSHelper {

    public static Executor THREAD_POOL = Executors.newWorkStealingPool();

    public NMSHelper1_19_0() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ServerPlayer> getOnlineNmsPlayers() {
        List<ServerPlayer> onlinePlayers = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach((p) -> {
            onlinePlayers.add(((CraftPlayer) p).getHandle());
        });
        return onlinePlayers;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PatheticPlayer createPlayerEntity(NPCBuilder builder) {
        return new PatheticPlayer(builder.getSpawnLocation(), builder.getNpcName(), this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ServerPlayer> getNmsPlayersInRange(Location near, double range) {
        List<ServerPlayer> nearbyPlayers = new ArrayList<>();
        Objects.requireNonNull(near.getWorld()).getNearbyEntities(near, range, 256, range)
                .stream()
                .filter(e -> e instanceof Player)
                .forEach((e) -> nearbyPlayers.add(convertToNms((Player) e)));
        return nearbyPlayers;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ServerPlayer convertToNms(Player player) {
        return ((CraftPlayer) player).getHandle();
    }


}

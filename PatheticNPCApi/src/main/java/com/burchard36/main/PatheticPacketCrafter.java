package com.burchard36.main;

public interface PatheticPacketCrafter {

    <T> T craftEntityAddPacket();

    <T> T craftSpawnEntityPacket();

    <T> T craftEntityHeadRotationPacket();
}

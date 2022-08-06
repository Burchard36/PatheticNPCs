package com.burchard36.patheticmain;

public interface PatheticPacketCrafter {

    <T> T craftEntityAddPacket();

    <T> T craftSpawnEntityPacket();

    <T> T craftEntityHeadRotationPacket();
}

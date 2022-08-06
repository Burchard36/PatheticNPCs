package com.burchard36.patheticmain.impl;

public interface PatheticPacketCrafter {

    <T> T craftEntityAddPacket();

    <T> T craftSpawnEntityPacket();

    <T> T craftEntityHeadRotationPacket();
}

package com.burchard36.main.nms;

import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;

public class NMSGrabber {
    /**
     * Gets the Helper for the version that the server is currently running.
     * @return A {@link NMSHelper} abstraction instance. Null if not supported for this version.
     */
    public static NMSHelper get() {
        String version = Bukkit.getBukkitVersion();
        if (version.contains("1.19.0")) {

            try {
                return (NMSHelper) Class.forName("com.burchard36.main.nms.v1_19_0.NMSHelper1_19_0").getDeclaredConstructor().newInstance();
            } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException |
                     InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}

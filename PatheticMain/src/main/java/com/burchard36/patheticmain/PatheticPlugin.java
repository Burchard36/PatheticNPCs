package com.burchard36.patheticmain;

import com.burchard36.patheticmain.nms.NMSHelper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class PatheticPlugin extends JavaPlugin {

    public static Executor THREAD_POOL;
    // If you use this for anything other than Tasks I'll personally fuck your face
    public static PatheticPlugin TASK_INSTANCE;

    @Getter
    private NMSHelper versionSpecificHelper;

    @Override
    public void onEnable() {
        TASK_INSTANCE = this;
        THREAD_POOL = Executors.newWorkStealingPool();

        Bukkit.getLogger().info("Attempting to inject NMS. . .");
        this.versionSpecificHelper = NMSHelper.get();
        if (this.versionSpecificHelper == null) {
            Bukkit.getLogger().warning("Your version of Spigot/Paper/Whatever is not supported! You are running: " + Bukkit.getBukkitVersion());
            Bukkit.getLogger().warning("Plugin will now shutdown. . .");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getLogger().info("Successfully injected into " + Bukkit.getBukkitVersion());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Translates a basic message to color format using & codes
     * @param msg String to format
     * @return formatted string.
     */
    public static String ofStr(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}

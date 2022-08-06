package com.burchard36.patheticmain.util;

import com.burchard36.patheticmain.CallbackVoid;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;


public class TaskRunner {

    protected static Plugin instance = Bukkit.getPluginManager().getPlugin("PatheticNPCs");

    /**
     * Runs a Task back onto the main thread using the BukkitScheduler
     * @param runnable A {@link Runnable} to run
     */
    public static void runTask(Runnable runnable) {
        runTask(runnable, null);
    }

    /**
     * Runs a Task back onto the main thread using the BukkitScheduler
     * and provides a callback function to execute when said runnable has been completed
     *
     * Use {@link TaskRunner#runTask(Runnable)} if you don't want a callback function, or supply a null value
     *
     * @param runnable {@link Runnable} to ru
     * @param whenComplete {@link CallbackVoid} lambda to run
     */
    public static void runTask(Runnable runnable, @Nullable CallbackVoid whenComplete) {
        Bukkit.getScheduler().runTask(instance, () -> {
            runnable.run();
            if (whenComplete != null) whenComplete.onComplete();
        });
    }

}

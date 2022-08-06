package com.burchard36.patheticmain.util;

import com.burchard36.patheticmain.impl.CallbackVoid;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;

import static com.burchard36.patheticmain.PatheticPlugin.TASK_INSTANCE;

public class TaskRunner {

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
        Bukkit.getScheduler().runTask(TASK_INSTANCE, () -> {
            runnable.run();
            if (whenComplete != null) whenComplete.onComplete();
        });
    }

}

package com.solicionio.essential.adapters;

import com.solicionio.essential.Configuration;
import com.solicionio.essential.utils.UtilConsole;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class PacketAdapter {

    private static ConcurrentLinkedQueue<Runnable> requestQueue;

    public static ScheduledExecutorService executorService;

    public static void enable(){
        executorService = Executors.newSingleThreadScheduledExecutor();
        requestQueue = new ConcurrentLinkedQueue<Runnable>();

        executorService.execute(() -> {
            try {
                while(true) {
                    Thread.sleep(1L);
                    if(Configuration.STOP_SYNC) continue;

                    try {

                        tickRequestQueue();

                    }catch(Exception e) {
                        UtilConsole.log("(SYNC) Error => " + e);
                    }
                }
            }catch(Exception e) {
                UtilConsole.log("(SYNC) Error => " + e);
            }
        });
    }

    private static void tickRequestQueue() {
        Iterator<Runnable> iterator = requestQueue.iterator();

        while(iterator.hasNext()) {
            try {
                Runnable runnable = iterator.next();
                runnable.run();
                requestQueue.remove(runnable);
            }catch(Exception e) {
                UtilConsole.log("(SYNC - PacketQueue) Error => " + e);
            }
        }
    }

    public static void addRequestQueue(Runnable runnable) {
        requestQueue.add(runnable);
    }
}

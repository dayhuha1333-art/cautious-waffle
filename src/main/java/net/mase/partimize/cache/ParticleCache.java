package net.mase.partimize.cache;

import net.mase.partimize.ConfigManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParticleCache {
    private static final Map<String, Boolean> cache = new ConcurrentHashMap<>();
    private static long lastUpdate = 0;
    private static final long CACHE_TTL = 30000;

    public static boolean isDisabled(String name) {
        long now = System.currentTimeMillis();
        if (now - lastUpdate > CACHE_TTL) {
            cache.clear();
            lastUpdate = now;
        }

        return cache.computeIfAbsent(name, ConfigManager::isParticleDisabled);
    }

    public static void invalidate() {
        cache.clear();
        lastUpdate = 0;
    }
}
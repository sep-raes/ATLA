package net.banaan.atla.util.glider;

import java.util.HashMap;
import java.util.UUID;

public class GlideData {
    final private static HashMap<UUID, Boolean> glideState = new HashMap<>();
    private static final HashMap<UUID, Integer> boostTicks = new HashMap<>();



    public static boolean isGliding(UUID uuid) {
        return glideState.getOrDefault(uuid, false);
    }

    public static void addUUID(UUID uuid) {
        glideState.putIfAbsent(uuid, false);
    }

    public static void setGliding(UUID uuid, Boolean newState) {
        glideState.compute(uuid, (key, state) -> newState);
    }

    public static void removeUUID(UUID uuid) {
        glideState.remove(uuid);
    }

    public static void startBoost(UUID uuid, int ticks) {
        boostTicks.put(uuid, ticks);
    }

    public static boolean consumeBoostTick(UUID uuid) {
        Integer remaining = boostTicks.get(uuid);
        if (remaining == null || remaining <= 0) {
            return false;
        }
        if (remaining <= 1) {
            boostTicks.remove(uuid);
        } else {
            boostTicks.put(uuid, remaining - 1);
        }
        return true;
    }
}
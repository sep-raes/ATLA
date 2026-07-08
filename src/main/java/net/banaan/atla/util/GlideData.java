package net.banaan.atla.util;

import java.util.HashMap;
import java.util.UUID;

public class GlideData {
    final private static HashMap<UUID, Boolean> glideState = new HashMap<>();


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
}
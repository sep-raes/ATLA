package net.banaan.atla.bending.registry;

import net.banaan.atla.bending.ability.AbilityType;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class AbilityRegistry {
    private static final Map<ResourceLocation, AbilityType> REGISTRY = new LinkedHashMap<>();

    private AbilityRegistry() {}

    public static AbilityType register(AbilityType type) {
        if (REGISTRY.putIfAbsent(type.id(), type) != null) {
            throw new IllegalStateException("Duplicate ability id registered: " + type.id());
        }
        return type;
    }

    public static AbilityType get(ResourceLocation id) {
        return REGISTRY.get(id);
    }

    public static Collection<AbilityType> all() {
        return REGISTRY.values();
    }
}
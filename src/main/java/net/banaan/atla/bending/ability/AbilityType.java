package net.banaan.atla.bending.ability;

import net.banaan.atla.bending.element.Element;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Function;


public record AbilityType(
        ResourceLocation id,
        Element element,
        int cooldownTicks,
        Function<ServerPlayer, Ability> factory
) {
    public Ability create(ServerPlayer caster) {
        return factory.apply(caster);
    }
}
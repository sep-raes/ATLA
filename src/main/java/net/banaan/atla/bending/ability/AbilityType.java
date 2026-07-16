package net.banaan.atla.bending.ability;

import net.banaan.atla.bending.element.Element;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Function;

@SuppressWarnings("removal")
public record AbilityType(
        ResourceLocation id,
        Element element,
        int cooldownTicks,
        ResourceLocation icon, //ONLY ADD IF IMAGE IS SOMEWHERE RANDOM
        int hudInteger,
        ActivationState activationState,
        Function<ServerPlayer, Ability> factory
) {

    public AbilityType(
            ResourceLocation id,
            Element element,
            int cooldownTicks,
            int hudInteger,
            ActivationState activationState,
            Function<ServerPlayer, Ability> factory
    ) {
        this(id, element, cooldownTicks, iconFor(id, element), hudInteger, activationState, factory);
    }

    private static ResourceLocation iconFor(ResourceLocation id, Element element) {
        return new ResourceLocation(id.getNamespace(),
                "textures/gui/bending/hud/" + element.name().toLowerCase() + "/" + id.getPath() + ".png");
    }

    public Ability create(ServerPlayer caster) {
        return factory.apply(caster);
    }

    public enum ActivationState {
        NORMAL,
        SNEAK,
        CLICK,
        SUB_BENDING_1,
        SUB_BENDING_2
    }
}
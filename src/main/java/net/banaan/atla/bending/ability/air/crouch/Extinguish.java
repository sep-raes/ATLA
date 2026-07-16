package net.banaan.atla.bending.ability.air.crouch;

import net.banaan.atla.bending.ability.Ability;
import net.banaan.atla.bending.ability.AbilityType;
import net.banaan.atla.bending.element.Element;
import net.banaan.atla.bending.registry.AbilityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@SuppressWarnings("removal")
public class Extinguish extends Ability {
    public static final AbilityType TYPE = AbilityRegistry.register(new AbilityType(
            new ResourceLocation("atla", "extinguish"), Element.AIR, 200, 2, AbilityType.ActivationState.SNEAK, Extinguish::new));



    public Extinguish(ServerPlayer caster) {
        super(caster, TYPE);
    }

    @Override
    public boolean activate() {
        return false;
    }
}
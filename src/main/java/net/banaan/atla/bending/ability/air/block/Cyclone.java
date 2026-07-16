package net.banaan.atla.bending.ability.air.block;

import net.banaan.atla.bending.ability.Ability;
import net.banaan.atla.bending.ability.AbilityType;
import net.banaan.atla.bending.element.Element;
import net.banaan.atla.bending.registry.AbilityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@SuppressWarnings("removal")
public class Cyclone extends Ability {
    public static final AbilityType TYPE = AbilityRegistry.register(new AbilityType(
            new ResourceLocation("atla", "cyclone"), Element.AIR, 400, 4, AbilityType.ActivationState.CLICK, Cyclone::new));



    public Cyclone(ServerPlayer caster) {
        super(caster, TYPE);
    }

    @Override
    public boolean activate() {
        return false;
    }
}
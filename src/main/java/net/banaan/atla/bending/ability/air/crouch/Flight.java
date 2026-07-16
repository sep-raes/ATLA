package net.banaan.atla.bending.ability.air.crouch;

import net.banaan.atla.bending.ability.Ability;
import net.banaan.atla.bending.ability.AbilityType;
import net.banaan.atla.bending.element.Element;
import net.banaan.atla.bending.registry.AbilityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@SuppressWarnings("removal")
public class Flight extends Ability {
    public static final AbilityType TYPE = AbilityRegistry.register(new AbilityType(
            new ResourceLocation("atla", "flight"), Element.AIR, 20, 4, AbilityType.ActivationState.SNEAK, Flight::new));



    public Flight(ServerPlayer caster) {
        super(caster, TYPE);
    }

    @Override
    public boolean activate() {
        return false;
    }
}
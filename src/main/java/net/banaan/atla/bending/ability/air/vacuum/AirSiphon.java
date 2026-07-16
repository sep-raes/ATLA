package net.banaan.atla.bending.ability.air.vacuum;

import net.banaan.atla.bending.ability.Ability;
import net.banaan.atla.bending.ability.AbilityType;
import net.banaan.atla.bending.element.Element;
import net.banaan.atla.bending.registry.AbilityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@SuppressWarnings("removal")
public class AirSiphon extends Ability {
    public static final AbilityType TYPE = AbilityRegistry.register(new AbilityType(
            new ResourceLocation("atla", "air_siphon"), Element.AIR, 20, 2, AbilityType.ActivationState.SUB_BENDING_1, AirSiphon::new));



    public AirSiphon(ServerPlayer caster) {
        super(caster, TYPE);
    }

    @Override
    public boolean activate() {
        return false;
    }
}
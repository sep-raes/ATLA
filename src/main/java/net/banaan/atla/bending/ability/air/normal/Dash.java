package net.banaan.atla.bending.ability.air.normal;

import net.banaan.atla.bending.ability.Ability;
import net.banaan.atla.bending.ability.AbilityType;
import net.banaan.atla.bending.element.Element;
import net.banaan.atla.bending.registry.AbilityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@SuppressWarnings("removal")
public class Dash extends Ability {
    public static final AbilityType TYPE = AbilityRegistry.register(new AbilityType(
            new ResourceLocation("atla", "dash"), Element.AIR, 120, 4, AbilityType.ActivationState.NORMAL, Dash::new));


    public Dash(ServerPlayer caster) {
        super(caster, TYPE);
    }


    @Override
    public boolean activate() {
        return false;
    }
}

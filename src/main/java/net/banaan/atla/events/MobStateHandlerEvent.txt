package net.banaan.atla.events;

import net.banaan.atla.entity.entities.SkyBisonEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobStateHandlerEvent {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getLevel().isClientSide || InteractionHand.MAIN_HAND != event.getHand()) return;

        if (!(event.getTarget() instanceof SkyBisonEntity mob)) return;
        if (!event.getEntity().isCrouching()) return;
        if (mob.getOwner() != event.getEntity()) return;

        mob.cycleMobStates();
        event.getEntity().sendSystemMessage(Component.literal("State: " + mob.getMobState().name()));
        event.setCanceled(true);
    }

}

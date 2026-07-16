package net.banaan.atla.bending.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("removal")
@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class BendingDataHandler {
    private static final ResourceLocation CAP_ID = new ResourceLocation("atla", "bending_data");

    private BendingDataHandler() {}

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(CAP_ID, new BendingDataProvider());
        }
    }

    @SubscribeEvent
    public static void copyOnRespawn(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(ModCapabilities.BENDING_DATA).ifPresent(oldData ->
                event.getEntity().getCapability(ModCapabilities.BENDING_DATA).ifPresent(newData ->
                        newData.copyFrom(oldData)));
    }
}
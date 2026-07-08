package net.banaan.atla.events;

import net.banaan.atla.util.GlideData;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerConnectionEvent {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        GlideData.addUUID(event.getEntity().getUUID());
        GliderStaffHandler.addUUID(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        GlideData.removeUUID(event.getEntity().getUUID());
        GliderStaffHandler.removeUUID(event.getEntity().getUUID());

    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        GlideData.setGliding(event.getEntity().getUUID(), false);
    }

    @SubscribeEvent
    public static void onPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        GlideData.setGliding(event.getEntity().getUUID(), false);
    }
}

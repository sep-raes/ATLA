package net.banaan.atla.events;

import net.banaan.atla.util.GlideData;
import net.banaan.atla.util.GlidePhysics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerGlideEvent {

    private static final HashMap<UUID, Integer> airTicks = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {



        Player player = event.player;
        UUID uuid = player.getUUID();


        if (!player.onGround()) {
            airTicks.compute(uuid, (key, value) -> (value == null ? 1 : value + 1));
        } else {
            airTicks.remove(uuid);
            return;
        }


        if (GlideData.isGliding(uuid)) {
            GlidePhysics.applyGlideMotion(player, airTicks.getOrDefault(uuid, 0));
        }
    }



}

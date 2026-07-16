package net.banaan.atla.events;

import net.banaan.atla.util.GlideData;
import net.banaan.atla.util.GlidePhysics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerGlideEvent {

    private static final ConcurrentHashMap<UUID, Integer> airTicks = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {



        Player player = event.player;
        UUID uuid = player.getUUID();


        if (!player.onGround() && GlideData.isGliding(uuid)) {
            airTicks.compute(uuid, (key, value) -> (value == null ? 1 : value + 1));
        } else {
            airTicks.remove(uuid);
            return;
        }


        if (GlideData.isGliding(uuid)) {
            if (!GlideData.consumeBoostTick(uuid)) {
                GlidePhysics.applyGlideMotion(player, airTicks.getOrDefault(uuid, 0));
            }
        }
    }



}

package net.banaan.atla.events;

import net.banaan.atla.bending.data.BendingData;
import net.banaan.atla.bending.data.ModCapabilities;
import net.banaan.atla.network.BendingSyncPacket;
import net.banaan.atla.network.ModMessages;
import net.banaan.atla.util.glider.GlideData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerStateEvent {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        GlideData.addUUID(event.getEntity().getUUID());
        GliderStaffHandler.addUUID(event.getEntity().getUUID());

        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(ModCapabilities.BENDING_DATA).ifPresent(data -> {
                ModMessages.sendToPlayer(new BendingSyncPacket((BendingData) data), player);
                System.out.println("Syncing bending data for: " + player.getName().getString());
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        GlideData.removeUUID(event.getEntity().getUUID());
        GliderStaffHandler.removeUUID(event.getEntity().getUUID());

    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        GlideData.setGliding(event.getEntity().getUUID(), false);

        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(ModCapabilities.BENDING_DATA).ifPresent(data -> {
                ModMessages.sendToPlayer(new BendingSyncPacket((BendingData) data), player);
                System.out.println("Syncing bending data for: " + player.getName().getString());
            });
        }
    }






    public static final Map<UUID, CompoundTag> DEATH_CACHE = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(ModCapabilities.BENDING_DATA).ifPresent(cap -> {
                DEATH_CACHE.put(player.getUUID(), ((BendingData) cap).saveNBT());
                System.out.println("DEBUG: Cached bending data for: " + player.getName().getString());
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {

        GlideData.setGliding(event.getEntity().getUUID(), false);

        if (event.getEntity() instanceof ServerPlayer player) {
            if (DEATH_CACHE.containsKey(player.getUUID())) {
                CompoundTag data = DEATH_CACHE.remove(player.getUUID());

                player.getCapability(ModCapabilities.BENDING_DATA).ifPresent(cap -> {
                    ((BendingData) cap).loadNBT(data);
                    System.out.println("DEBUG: Applied cached data to respawned player!");
                    ModMessages.sendToPlayer(new BendingSyncPacket((BendingData) cap), player);
                });
            }
        }
    }
}

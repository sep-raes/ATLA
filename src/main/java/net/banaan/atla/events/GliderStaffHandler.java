package net.banaan.atla.events;

import net.banaan.atla.item.ModItems;
import net.banaan.atla.item.data.ModItemData;
import net.banaan.atla.util.CameraToggle;
import net.banaan.atla.util.GlideData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.UUID;


@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GliderStaffHandler {


    final private static HashMap<UUID, Boolean> toggle = new HashMap<UUID, Boolean>();


    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        ItemStack itemStackLeft = player.getOffhandItem();

        if (itemStack.getItem() != ModItems.GLIDER_STAFF.get() && itemStackLeft.getItem()!= ModItems.GLIDER_STAFF.get()) return;

        int value = ModItemData.getTag(itemStack, "gliderLevel");
        if (value != 2) return;

        changeCamera(event.getLevel(), player);
        player.sendSystemMessage(Component.translatable("chat.atla.glider_level_right_click"));

    }

    public static void changeCamera(Level level, Player player) {
        if (level.isClientSide) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> (Runnable) () -> CameraToggle.toggleCamera(player));
            toggle(player.getUUID());
            GlideData.setGliding(player.getUUID(), getToggle(player.getUUID()));
        }
    }




    @SubscribeEvent
    public static void onSlotChange(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (getToggle(player.getUUID()) && ((player.getMainHandItem().getItem() != ModItems.GLIDER_STAFF.get() || ModItemData.getTag(player.getMainHandItem(), "gliderLevel") != 2) && (player.getOffhandItem().getItem() != ModItems.GLIDER_STAFF.get() || ModItemData.getTag(player.getOffhandItem(), "gliderLevel") != 2))) {
            changeCamera(player.level(), player);
        }
    }




    public static boolean getToggle(UUID uuid) {
        return toggle.getOrDefault(uuid, false);
    }

    public static void addUUID(UUID uuid) {
        toggle.putIfAbsent(uuid, false);
    }

    public static void toggle(UUID uuid) {
        toggle.compute(uuid, (key, state) -> state == null || !state);    }

    public static void removeUUID(UUID uuid) {
        toggle.remove(uuid);
    }
}
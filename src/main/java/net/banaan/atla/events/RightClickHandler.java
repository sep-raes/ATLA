package net.banaan.atla.events;

import net.banaan.atla.item.ModItems;
import net.banaan.atla.item.data.ModItemData;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RightClickHandler {

    private static boolean state = false;
    private static CameraType previousCameraType = null;

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();

        if (itemStack.getItem() != ModItems.GLIDER_STAFF.get()) return;

        int value = ModItemData.getTag(itemStack, "gliderLevel");
        if (value != 2) return;

        // Only touch the camera on the client
        if (event.getLevel().isClientSide) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> RightClickHandler::toggleCamera);
            player.sendSystemMessage(Component.translatable("chat.atla.glider_level_right_click"));
        }
    }

    private static void toggleCamera() {
        Options options = Minecraft.getInstance().options;

        if (previousCameraType == null) {
            previousCameraType = options.getCameraType();
            state = true;
            options.setCameraType(CameraType.THIRD_PERSON_BACK);
        } else {
            options.setCameraType(previousCameraType);
            state = false;
            previousCameraType = null;
        }
    }

    public static boolean getState() { return state; }
}
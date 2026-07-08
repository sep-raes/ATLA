package net.banaan.atla.util;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;


public class CameraToggle {
    public static CameraType previousCameraType = null;

    public static void toggleCamera(Player player) {
        Options options = Minecraft.getInstance().options;


        if (previousCameraType == null) {
            previousCameraType = options.getCameraType();
            options.setCameraType(CameraType.THIRD_PERSON_BACK);
            player.sendSystemMessage(Component.translatable("into third person"));
        } else {
            options.setCameraType(previousCameraType);
            player.sendSystemMessage(Component.translatable("outa third person"));
            previousCameraType = null;
        }
    }
}

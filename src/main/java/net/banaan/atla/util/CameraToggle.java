package net.banaan.atla.util;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.entity.player.Player;


public class CameraToggle {
    public static CameraType previousCameraType = null;

    public static void toggleCamera(Player player) {
        Options options = Minecraft.getInstance().options;


        if (previousCameraType == null) {
            previousCameraType = options.getCameraType();
            options.setCameraType(CameraType.THIRD_PERSON_BACK);
        } else {
            options.setCameraType(previousCameraType);
            previousCameraType = null;
        }
    }
}

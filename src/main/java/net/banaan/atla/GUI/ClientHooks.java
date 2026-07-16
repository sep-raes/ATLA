package net.banaan.atla.GUI;

import net.banaan.atla.GUI.PaiSho.PaiShoMainGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class ClientHooks {
    public static void openPaiShoMain(BlockPos pos) {
        Minecraft.getInstance().setScreen(new PaiShoMainGUI(pos));
    }
}
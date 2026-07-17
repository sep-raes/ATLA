package net.banaan.atla.events;

import net.banaan.atla.network.AbilityOne;
import net.banaan.atla.network.ModMessages;
import net.banaan.atla.util.KeybindHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientInputHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;
            if (KeybindHelper.ABILITY_1.consumeClick()) {
                boolean isMouseRightDown = Minecraft.getInstance().options.keyUse.isDown();
                ModMessages.sendToServer(new AbilityOne(isMouseRightDown));
            }
            if (KeybindHelper.ABILITY_MENU.consumeClick()) {
            }
        }
    }



}

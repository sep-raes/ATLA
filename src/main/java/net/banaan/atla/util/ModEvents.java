package net.banaan.atla.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    private static void onMainHandChanged( ServerPlayer player, ItemStack oldItem, ItemStack newItem) {
        player.sendSystemMessage(net.minecraft.network.chat.Component.literal("Your main hand changed!"));

        // Example: Check if the new item has your custom tag from earlier steps
        // 1.20.5+ : if (newItem.has(CUSTOM_DATA.get())) { ... }
        // Legacy  : if (newItem.hasTag() && newItem.getTag().contains("MyCustomKey")) { ... }
    }
}

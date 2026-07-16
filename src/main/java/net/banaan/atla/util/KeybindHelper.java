package net.banaan.atla.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeybindHelper {
    public static final KeyMapping ABILITY_1 = new KeyMapping(
            "key.atla.ability_1",
            InputConstants.KEY_X,
            "key.category.atla.avatar_controls"
    );
    public static final KeyMapping ABILITY_2 = new KeyMapping(
            "key.atla.ability_2",
            InputConstants.KEY_C,
            "key.category.atla.avatar_controls"
    );
    public static final KeyMapping ABILITY_3 = new KeyMapping(
            "key.atla.ability_3",
            InputConstants.KEY_V,
            "key.category.atla.avatar_controls"
    );
    public static final KeyMapping ABILITY_4 = new KeyMapping(
            "key.atla.ability_4",
            InputConstants.KEY_B,
            "key.category.atla.avatar_controls"
    );
    public static final KeyMapping ABILITY_MENU = new KeyMapping(
            "key.atla.ability_menu",
            InputConstants.KEY_Y,
            "key.category.atla.avatar_controls"
    );
    public static final KeyMapping CYCLE_ELEMENT = new KeyMapping(
            "key.atla.cycle_element",
            InputConstants.KEY_G,
            "key.category.atla.avatar_controls"
    );
    public static final KeyMapping ENABLE_BENDING = new KeyMapping(
            "key.atla.enable_bending",
            InputConstants.KEY_R,
            "key.category.atla.avatar_controls"
    );

    @SubscribeEvent
    public static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(ABILITY_1);
        event.register(ABILITY_2);
        event.register(ABILITY_3);
        event.register(ABILITY_4);
        event.register(ABILITY_MENU);
        event.register(CYCLE_ELEMENT);
        event.register(ENABLE_BENDING);
    }
}

package net.banaan.atla.events;


import net.banaan.atla.Atla;
import net.banaan.atla.GUI.ModMenuTypes;
import net.banaan.atla.GUI.PaiSho.PaiShoTableScreen;
import net.banaan.atla.entity.client.ModModelLayers;
import net.banaan.atla.entity.entities.SkyBison;
import net.banaan.atla.item.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("removal")
@Mod.EventBusSubscriber(modid = Atla.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SKY_BISON_LAYER, SkyBison::createBodyLayer);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.GLIDER_STAFF.get(),
                    new ResourceLocation("atla", "glider_level"),
                    (stack, level, entity, seed) -> {
                        if (stack.hasTag() && stack.getTag().contains("gliderLevel")) {
                            // Cleaner way to read the integer tag directly
                            int levelValue = stack.getTag().getInt("gliderLevel");
                            if (levelValue == 2) {
                                return 2.0F;
                            }
                        }
                        return 1.0F;
                    });
        });
        event.enqueueWork(() -> MenuScreens.register(ModMenuTypes.PAI_SHO_TABLE_MENU.get(), PaiShoTableScreen::new));
    }

}

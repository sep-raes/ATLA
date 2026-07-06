package net.banaan.atla.events;


import net.banaan.atla.Atla;
import net.banaan.atla.entity.client.ModModelLayers;
import net.banaan.atla.entity.entities.SkyBison;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Atla.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SKY_BISON_LAYER, SkyBison::createBodyLayer);
    }
}

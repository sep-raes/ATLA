package net.banaan.atla.events;


import net.banaan.atla.Atla;
import net.banaan.atla.entity.ModEntities;
import net.banaan.atla.entity.client.ModModelLayers;
import net.banaan.atla.entity.entities.SkyBison;
import net.banaan.atla.entity.entities.SkyBisonEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Atla.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SKY_BISON.get(), SkyBisonEntity.createAttributes().build());
    }
}

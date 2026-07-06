package net.banaan.atla.entity;

import net.banaan.atla.Atla;
import net.banaan.atla.entity.entities.SkyBisonEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Atla.MODID);

    public static final RegistryObject<EntityType<SkyBisonEntity>> SKY_BISON = ENTITY_TYPES.register("sky_bison",
            () -> EntityType.Builder
                    .of(SkyBisonEntity::new, MobCategory.CREATURE)
                    .sized(2.5f, 2.5f)
                    .build("sky_bison"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

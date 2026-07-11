package net.banaan.atla.datagen;

import net.banaan.atla.Atla;
import net.banaan.atla.datagen.tag.ModBiomeTagGenerator;
import net.banaan.atla.entity.ModEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModMobSpawner {
    public static final ResourceKey<BiomeModifier> SPAWN_SKY_BISON = ResourceKey.create(
            ForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(Atla.MODID, "spawn_sky_bison")
    );

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);

        MobSpawnSettings.SpawnerData spawnerData = new MobSpawnSettings.SpawnerData(
                ModEntities.SKY_BISON.get(),
                10,
                4,
                5
        );

        context.register(SPAWN_SKY_BISON, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModBiomeTagGenerator.SKY_BISON_SPAWN_BIOMES),
                List.of(spawnerData)
        ));
    }
}
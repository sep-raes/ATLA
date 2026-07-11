package net.banaan.atla.datagen.tag;

import net.banaan.atla.Atla;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBiomeTagGenerator extends BiomeTagsProvider {
    public static final TagKey<Biome> SKY_BISON_SPAWN_BIOMES = TagKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(Atla.MODID, "sky_bison_spawn_biomes")
    );

    public ModBiomeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Atla.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(SKY_BISON_SPAWN_BIOMES)
                .add(Biomes.FROZEN_PEAKS)
                .add(Biomes.JAGGED_PEAKS)
                .add(Biomes.STONY_PEAKS)
                .add(Biomes.SNOWY_SLOPES)
                .add(Biomes.MEADOW);
    }
}

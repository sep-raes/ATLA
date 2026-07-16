package net.banaan.atla.datagen.tag;

import net.banaan.atla.Atla;
import net.banaan.atla.block.ModBlocks;
import net.banaan.atla.util.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Atla.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Blocks.PLANTS)
                .add(ModBlocks.BANANA_PLANT.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.BANANA_LOG.get(),
                        ModBlocks.BANANA_PLANKS.get(),
                        ModBlocks.BANANA_STAIRS.get());

        this.tag(ModTags.Blocks.SKY_BISON_SPAWN_ON)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.SNOW_BLOCK)
                .add(Blocks.STONE)
                .add(Blocks.GRAVEL)
                .add(Blocks.SNOW);
    }
}

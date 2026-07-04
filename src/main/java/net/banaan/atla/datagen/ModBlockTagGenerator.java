package net.banaan.atla.datagen;

import net.banaan.atla.Atla;
import net.banaan.atla.block.ModBlocks;
import net.banaan.atla.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
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




        //this.tag(BlockTags.FENCES)
        //        .add(ModBlocks.BANANA_FENCES.get());
        //this.tag(BlockTags.FENCE_GATES)
        //        .add(ModBlocks.BANANA_FENCE_GATE.get());
        //this.tag(BlockTags.WALLS)
        //        .add(ModBlocks.BANANA_WALL.get());


    }
}
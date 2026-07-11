package net.banaan.atla.datagen.tag;

import net.banaan.atla.Atla;
import net.banaan.atla.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;


@SuppressWarnings("removal")
public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, tagLookupCompletableFuture, Atla.MODID, existingFileHelper);
    }

    public static final TagKey<Item> BISON_SADDLES = create("bison_saddles");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(Atla.MODID, name));
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModItemTagGenerator.BISON_SADDLES)
                .add(ModItems.OAK_BISON_SADDLE.get())
                .add(ModItems.SPRUCE_BISON_SADDLE.get())
                .add(ModItems.BIRCH_BISON_SADDLE.get())
                .add(ModItems.JUNGLE_BISON_SADDLE.get())
                .add(ModItems.ACACIA_BISON_SADDLE.get())
                .add(ModItems.DARK_OAK_BISON_SADDLE.get())
                .add(ModItems.MANGROVE_BISON_SADDLE.get())
                .add(ModItems.CHERRY_BISON_SADDLE.get())
                .add(ModItems.CRIMSON_BISON_SADDLE.get())
                .add(ModItems.WARPED_BISON_SADDLE.get())
                .add(ModItems.BANANA_BISON_SADDLE.get())
                .add(ModItems.BANYAN_BISON_SADDLE.get());
    }
}
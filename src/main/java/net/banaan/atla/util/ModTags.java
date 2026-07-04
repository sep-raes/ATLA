package net.banaan.atla.util;

import net.banaan.atla.Atla;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("removal")
public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> PLANTS = tag("plants");

        private  static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Atla.MODID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> FRUITS = tag("fruits");

        private  static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Atla.MODID, name));
        }
    }
}

package net.banaan.atla.datagen;

import net.banaan.atla.Atla;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

@SuppressWarnings("removal")
public class ModItemTags {

    public static final TagKey<Item> BISON_SADDLES = create("bison_saddles");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(Atla.MODID, name));
    }
}
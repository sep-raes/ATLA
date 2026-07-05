package net.banaan.atla.item.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ModItemData {
    public static void addTag(ItemStack stack, String key, int value) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(key, value);
        stack.setTag(tag);
    }

    public static int getTag(ItemStack stack, String key) {
        if (stack.hasTag() && stack.getTag().contains(key)) {
            net.minecraft.nbt.Tag tag = stack.getTag().get(key);
            if (tag instanceof net.minecraft.nbt.NumericTag numericTag) {
                return numericTag.getAsInt(); // Handles Bytes, Shorts, and Ints perfectly
            }
        }
        return 0;
    }
}

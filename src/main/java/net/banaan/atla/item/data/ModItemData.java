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
        if (stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if (tag.contains(key)) {
                return tag.getInt(key);
            }
        }
        return -1;
    }
}

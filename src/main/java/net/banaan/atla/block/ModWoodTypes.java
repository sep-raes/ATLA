package net.banaan.atla.block;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final BlockSetType BANANA_SET = BlockSetType.register(
            new BlockSetType("atla:banana")
    );

    public static final WoodType BANANA_WOOD_TYPE = WoodType.register(
            new WoodType("atla:banana", BANANA_SET)
    );

    public static void register() {
        // called once during mod init to force class-load / registration
    }
}
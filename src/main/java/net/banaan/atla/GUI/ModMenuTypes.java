package net.banaan.atla.GUI;

import net.banaan.atla.block.menu.PaiShoTableMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, "atla");

    public static final RegistryObject<MenuType<PaiShoTableMenu>> PAI_SHO_TABLE_MENU =
            MENU_TYPES.register("pai_sho_table_menu", () -> IForgeMenuType.create(PaiShoTableMenu::new));
}
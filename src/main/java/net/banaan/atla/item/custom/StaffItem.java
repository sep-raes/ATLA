package net.banaan.atla.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StaffItem extends Item {

    public StaffItem(Properties properties) { super(properties); }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
        componentList.add(Component.translatable("tooltip.atla.glider_staff_1"));
        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
    }


}

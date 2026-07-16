package net.banaan.atla.bending.element;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;


public enum Element {
    FIRE("fire", ChatFormatting.RED),
    WATER("water", ChatFormatting.AQUA),
    EARTH("earth", ChatFormatting.DARK_GREEN),
    AIR("air", ChatFormatting.GRAY);

    private final String id;
    private final ChatFormatting color;

    Element(String id, ChatFormatting color) {
        this.id = id;
        this.color = color;
    }

    public String id() {
        return id;
    }

    public ChatFormatting color() {
        return color;
    }

    public Component displayName() {
        return Component.translatable("element.atla." + id).withStyle(color);
    }
}
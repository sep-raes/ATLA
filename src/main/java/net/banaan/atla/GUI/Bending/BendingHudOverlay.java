package net.banaan.atla.GUI.Bending;

import net.banaan.atla.bending.ability.AbilitySlotRegistry;
import net.banaan.atla.bending.ability.AbilityType;
import net.banaan.atla.bending.data.ModCapabilities;
import net.banaan.atla.bending.element.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

@SuppressWarnings("removal")
public class BendingHudOverlay {

    private static final ResourceLocation AIR = new ResourceLocation("atla", "textures/gui/bending/hud/move_square_air.png");
    private static final ResourceLocation WATER = new ResourceLocation("atla", "textures/gui/bending/hud/move_square_water.png");
    private static final ResourceLocation FIRE = new ResourceLocation("atla", "textures/gui/bending/hud/move_square_fire.png");
    private static final ResourceLocation EARTH = new ResourceLocation("atla", "textures/gui/bending/hud/move_square_earth.png");
    private static final ResourceLocation EMPTY = new ResourceLocation("atla", "textures/gui/bending/hud/move_square.png");
    private static final ResourceLocation OVERLAY = new ResourceLocation("atla", "textures/gui/bending/hud/move_overlay.png");

    public static final IGuiOverlay BENDING_HUD = (gui, guiGraphics, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        int iconSize = 30;
        int spacing = 2;
        int totalIcons = 4;
        int totalHeight = (totalIcons * iconSize) + ((totalIcons - 1) * spacing);
        int startX = 3;
        int startY = ((height / 2) - (totalHeight / 2));
        int drawSize = 18;
        int offset = (iconSize - drawSize - 2) / 2;

        boolean sneaking = mc.player.isCrouching();
        boolean clicking = mc.options.keyUse.isDown();


        mc.player.getCapability(ModCapabilities.BENDING_DATA).ifPresent(data -> {
            Element element = data.getSelectedElement();
            ResourceLocation background = getBackgroundTexture(element);
            Font font = Minecraft.getInstance().font;

            int subBendingIndex = data.getSubBendingIndex();
            AbilityType.ActivationState currentState = stateFor(sneaking, clicking, subBendingIndex);

            for (int i = 0; i < totalIcons; i++) {
                int y = startY + (i * (iconSize + spacing));

                guiGraphics.blit(background, startX, y, 0, 0, iconSize, iconSize, iconSize, iconSize);

                if (element == null) {
                    continue;
                }

                AbilityType type = AbilitySlotRegistry.get(element, i, currentState);
                if (type != null) {
                    guiGraphics.blit(type.icon(), startX + offset, y + offset, 0, 0, drawSize + 2, drawSize + 2, drawSize, drawSize);

                    long currentTick = mc.player.level().getGameTime();
                    long remainingTicks = data.getCooldownRemaining(type.id(), currentTick);

                    if (remainingTicks > 0) {
                        guiGraphics.blit(OVERLAY, startX, y, 0, 0, iconSize, iconSize, iconSize, iconSize);

                        String cooldown = String.valueOf((long) Math.ceil(remainingTicks / 20.0));

                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().translate(0, 0, 200);
                        guiGraphics.pose().scale(1.8f, 1.8f, 1);

                        int textX = (int) ((startX + (iconSize / 1.8f) - 6 - (cooldown.length() - 1) * 6) / 1.8f);
                        int textY = (int) ((y + (iconSize / 1.8f) - 7) / 1.8f);
                        guiGraphics.drawString(font, cooldown, textX, textY, 0xB5B3AC, true);

                        guiGraphics.pose().popPose();
                    }
                }
            }
        });
    };

    private static AbilityType.ActivationState stateFor(boolean sneaking, boolean clicking, int subBendingIndex) {
        if (subBendingIndex == 1) return AbilityType.ActivationState.SUB_BENDING_1;
        if (subBendingIndex == 2) return AbilityType.ActivationState.SUB_BENDING_2;

        if (clicking) return AbilityType.ActivationState.CLICK;
        if (sneaking) return AbilityType.ActivationState.SNEAK;
        return AbilityType.ActivationState.NORMAL;
    }

    public static ResourceLocation getBackgroundTexture(Element element) {
        if (element == null) return EMPTY;

        return switch (element) {
            case AIR -> AIR;
            case WATER -> WATER;
            case FIRE -> FIRE;
            case EARTH -> EARTH;
        };
    }
}
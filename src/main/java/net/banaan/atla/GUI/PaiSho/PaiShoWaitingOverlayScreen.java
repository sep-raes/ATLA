package net.banaan.atla.GUI.PaiSho;

import net.banaan.atla.network.LeavePaiShoTablePacket;
import net.banaan.atla.network.ModMessages;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class PaiShoWaitingOverlayScreen extends Screen {

    private final BlockPos tablePos;

    public PaiShoWaitingOverlayScreen(BlockPos tablePos) {
        super(Component.literal("Waiting for opponent"));
        this.tablePos = tablePos;
    }

    @Override
    protected void init() {
        super.init();
        int w = 100, h = 20;
        this.addRenderableWidget(Button.builder(Component.literal("Cancel"), b -> this.onClose())
                .bounds(this.width / 2 - w / 2, this.height / 2 + 30, w, h)
                .build());
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g); // dims/blocks the world behind it
        g.drawCenteredString(this.font, Component.literal("Waiting for an opponent..."),
                this.width / 2, this.height / 2 - 20, 0xFFFFFF);
        super.render(g, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // don't pause the game (esp. relevant in multiplayer)
    }

    @Override
    public void onClose() {
        // fires on Cancel button AND on pressing Escape — both should free the slot
        ModMessages.sendToServer(new LeavePaiShoTablePacket(tablePos));
        super.onClose();
    }
}
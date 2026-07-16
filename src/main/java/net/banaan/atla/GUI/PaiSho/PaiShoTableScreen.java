package net.banaan.atla.GUI.PaiSho;

import net.banaan.atla.block.menu.PaiShoTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@SuppressWarnings("removal")
public class PaiShoTableScreen extends AbstractContainerScreen<PaiShoTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("atla", "textures/gui/pai_sho_inventory.png");

    // These match your large texture dimensions
    private static final int TEXTURE_WIDTH = 492;
    private static final int TEXTURE_HEIGHT = 489;

    private int originalGuiScale;

    public PaiShoTableScreen(PaiShoTableMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = TEXTURE_WIDTH;
        this.imageHeight = TEXTURE_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();

        if (this.minecraft != null && this.minecraft.options != null) {
            this.originalGuiScale = this.minecraft.options.guiScale().get();

            if (this.originalGuiScale != 2) {
                this.minecraft.options.guiScale().set(2);
                this.minecraft.resizeDisplay();
            }
        }
    }

    @Override
    public void removed() {
        super.removed();

        if (this.minecraft != null) {
            this.minecraft.options.guiScale().set(this.originalGuiScale);
            this.minecraft.resizeDisplay();
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(
                TEXTURE,
                x, y,
                0, 0,
                this.imageWidth, this.imageHeight,
                this.imageWidth, this.imageHeight
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
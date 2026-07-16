package net.banaan.atla.GUI.PaiSho;

import net.banaan.atla.network.JoinPaiShoTablePacket;
import net.banaan.atla.network.ModMessages;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("removal")
public class PaiShoMainGUI extends Screen {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("atla", "textures/gui/pai_sho_start.png");

    private static final int BASE_IMAGE_WIDTH = 175;
    private static final int BASE_IMAGE_HEIGHT = 165;

    // Define the maximum scale factor (e.g., 2.0 = 200% of original texture size)
    private static final float MAX_SCALE = 2.0f;

    private final BlockPos tablePos;
    private float scale;
    private int imageX, imageY, imageWidth, imageHeight;

    public PaiShoMainGUI(BlockPos tablePos) {
        super(Component.literal("Pai Sho Table"));
        this.tablePos = tablePos;
    }

    private void computeLayout() {
        float aspect = (float) BASE_IMAGE_WIDTH / BASE_IMAGE_HEIGHT;

        // Calculate based on window size
        int maxWidthByWidth = Math.round(this.width * 0.75f);
        int maxHeightByHeight = Math.round(this.height * 0.85f);

        // Determine raw width/height
        int targetWidth, targetHeight;
        if (maxWidthByWidth / aspect <= maxHeightByHeight) {
            targetWidth = maxWidthByWidth;
            targetHeight = Math.round(maxWidthByWidth / aspect);
        } else {
            targetHeight = maxHeightByHeight;
            targetWidth = Math.round(maxHeightByHeight * aspect);
        }

        // Calculate potential scale
        float potentialScale = (float) targetWidth / BASE_IMAGE_WIDTH;

        // APPLY THE CAP: Use Math.min to ensure we never exceed MAX_SCALE
        this.scale = Math.min(potentialScale, MAX_SCALE);

        // Set dimensions based on capped scale
        this.imageWidth = Math.round(BASE_IMAGE_WIDTH * this.scale);
        this.imageHeight = Math.round(BASE_IMAGE_HEIGHT * this.scale);

        // Center
        this.imageX = (this.width - imageWidth) / 2;
        this.imageY = (this.height - imageHeight) / 2;
    }

    @Override
    protected void init() {
        super.init();
        computeLayout();

        int buttonWidth = Math.round(200 * scale * 0.5f);
        int buttonHeight = Math.round(20 * scale * 0.5f);
        int gap = Math.round(6 * scale);

        int buttonX = this.width / 2 - buttonWidth / 2;
        int startY = this.height / 2 - buttonHeight - gap / 2;
        int settingsY = this.height / 2 + gap / 2;

        this.addRenderableWidget(Button.builder(Component.literal("Start Game"), b -> {
                    ModMessages.sendToServer(new JoinPaiShoTablePacket(tablePos));
                    this.onClose();
                })
                .bounds(buttonX, startY, buttonWidth, buttonHeight)
                .build());

        this.addRenderableWidget(Button.builder(Component.literal("Change Settings"), b -> {
                    this.onClose();
                })
                .bounds(buttonX, settingsY, buttonWidth, buttonHeight)
                .build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);

        // Re-compute layout here only if the window was resized
        computeLayout();

        guiGraphics.blit(GUI_TEXTURE, imageX, imageY, imageWidth, imageHeight,
                0, 0, BASE_IMAGE_WIDTH, BASE_IMAGE_HEIGHT,
                BASE_IMAGE_WIDTH, BASE_IMAGE_HEIGHT);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
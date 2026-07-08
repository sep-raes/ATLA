package net.banaan.atla.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.banaan.atla.Atla;
import net.banaan.atla.entity.entities.SkyBison;
import net.banaan.atla.entity.entities.SkyBisonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("removal")
public class SkyBisonRenderer extends MobRenderer<SkyBisonEntity, SkyBison<SkyBisonEntity>> {
    public SkyBisonRenderer(EntityRendererProvider.Context context) {
        super(context, new SkyBison<>(context.bakeLayer(ModModelLayers.SKY_BISON_LAYER)), 2f);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(SkyBisonEntity bisonEntity) {
        return bisonEntity.isSaddled()
                ? new ResourceLocation(Atla.MODID, "textures/entity/sky_bison_banana_saddle.png")
                : new ResourceLocation(Atla.MODID, "textures/entity/sky_bison_no_sadle.png");
    }

    @Override
    public void render(SkyBisonEntity bisonEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        if (bisonEntity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else if (bisonEntity.isBisonSitting()) {
            poseStack.translate(0, -1, 0);
        }

        super.render(bisonEntity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}

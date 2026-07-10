package net.banaan.atla.entity.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.banaan.atla.entity.animations.ModAnimationDefinitions;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class SkyBison<T extends Entity> extends HierarchicalModel<T> {

	private final ModelPart Base;
	private final ModelPart Body;
	private final ModelPart Leg1;
	private final ModelPart UpperPart;
	private final ModelPart LowerPart;
	private final ModelPart FEET;
	private final ModelPart Leg4;
	private final ModelPart UpperPart4;
	private final ModelPart LowerPart4;
	private final ModelPart FEET4;
	private final ModelPart Leg5;
	private final ModelPart UpperPart5;
	private final ModelPart LowerPart5;
	private final ModelPart FEET5;
	private final ModelPart Leg6;
	private final ModelPart UpperPart6;
	private final ModelPart LowerPart6;
	private final ModelPart FEET6;
	private final ModelPart Leg2;
	private final ModelPart UpperPart2;
	private final ModelPart LowerPart2;
	private final ModelPart FEET2;
	private final ModelPart Leg3;
	private final ModelPart UpperPart3;
	private final ModelPart LowerPart3;
	private final ModelPart FEET3;
	private final ModelPart MainBody;
	private final ModelPart UpperBody;
	private final ModelPart lowerBody;
	private final ModelPart Tail;
	private final ModelPart TailSegment1;
	private final ModelPart TailSegment2;
	private final ModelPart TailSegment3;
	private final ModelPart head;

	public SkyBison(ModelPart root) {
		this.Base = root.getChild("Base");
		this.Body = this.Base.getChild("Body");
		this.Leg1 = this.Body.getChild("Leg1");
		this.UpperPart = this.Leg1.getChild("UpperPart");
		this.LowerPart = this.UpperPart.getChild("LowerPart");
		this.FEET = this.LowerPart.getChild("FEET");
		this.Leg4 = this.Body.getChild("Leg4");
		this.UpperPart4 = this.Leg4.getChild("UpperPart4");
		this.LowerPart4 = this.UpperPart4.getChild("LowerPart4");
		this.FEET4 = this.LowerPart4.getChild("FEET4");
		this.Leg5 = this.Body.getChild("Leg5");
		this.UpperPart5 = this.Leg5.getChild("UpperPart5");
		this.LowerPart5 = this.UpperPart5.getChild("LowerPart5");
		this.FEET5 = this.LowerPart5.getChild("FEET5");
		this.Leg6 = this.Body.getChild("Leg6");
		this.UpperPart6 = this.Leg6.getChild("UpperPart6");
		this.LowerPart6 = this.UpperPart6.getChild("LowerPart6");
		this.FEET6 = this.LowerPart6.getChild("FEET6");
		this.Leg2 = this.Body.getChild("Leg2");
		this.UpperPart2 = this.Leg2.getChild("UpperPart2");
		this.LowerPart2 = this.UpperPart2.getChild("LowerPart2");
		this.FEET2 = this.LowerPart2.getChild("FEET2");
		this.Leg3 = this.Body.getChild("Leg3");
		this.UpperPart3 = this.Leg3.getChild("UpperPart3");
		this.LowerPart3 = this.UpperPart3.getChild("LowerPart3");
		this.FEET3 = this.LowerPart3.getChild("FEET3");
		this.MainBody = this.Base.getChild("MainBody");
		this.UpperBody = this.MainBody.getChild("UpperBody");
		this.lowerBody = this.MainBody.getChild("lowerBody");
		this.Tail = this.MainBody.getChild("Tail");
		this.TailSegment1 = this.Tail.getChild("TailSegment1");
		this.TailSegment2 = this.TailSegment1.getChild("TailSegment2");
		this.TailSegment3 = this.TailSegment2.getChild("TailSegment3");
		this.head = this.MainBody.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Base = partdefinition.addOrReplaceChild("Base", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, 31.0F));

		PartDefinition Body = Base.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, -8.0F));

		PartDefinition Leg1 = Body.addOrReplaceChild("Leg1", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -32.0F));

		PartDefinition UpperPart = Leg1.addOrReplaceChild("UpperPart", CubeListBuilder.create().texOffs(196, 104).addBox(-6.0F, -7.0F, -8.0F, 12.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, -2.0F, -14.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition LowerPart = UpperPart.addOrReplaceChild("LowerPart", CubeListBuilder.create().texOffs(52, 239).addBox(-5.0F, 0.0F, -7.0F, 10.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(52, 267).addBox(5.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 268).addBox(-5.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(160, 55).addBox(-5.0F, 14.0F, -7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(76, 177).addBox(-5.0F, 14.0F, 7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -1.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition FEET = LowerPart.addOrReplaceChild("FEET", CubeListBuilder.create().texOffs(248, 133).addBox(-6.0F, 0.0F, -10.0F, 12.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(28, 268).addBox(-3.0F, -1.0F, -15.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition cube_r1 = FEET.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(76, 166).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 5.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = FEET.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(76, 155).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 5.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Leg4 = Body.addOrReplaceChild("Leg4", CubeListBuilder.create(), PartPose.offset(-31.0F, 0.0F, -32.0F));

		PartDefinition UpperPart4 = Leg4.addOrReplaceChild("UpperPart4", CubeListBuilder.create().texOffs(60, 208).addBox(-7.0F, -9.0F, -7.0F, 12.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 0.0F, -15.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition LowerPart4 = UpperPart4.addOrReplaceChild("LowerPart4", CubeListBuilder.create().texOffs(100, 244).addBox(-6.0F, 0.0F, -7.0F, 10.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(80, 272).addBox(4.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(108, 272).addBox(-6.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(206, 98).addBox(-6.0F, 14.0F, -7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(206, 100).addBox(-6.0F, 14.0F, 7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition FEET4 = LowerPart4.addOrReplaceChild("FEET4", CubeListBuilder.create().texOffs(256, 193).addBox(-7.0F, 0.0F, -10.0F, 12.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(224, 292).addBox(-4.0F, -1.0F, -15.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition cube_r3 = FEET4.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(250, 291).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 5.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r4 = FEET4.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(290, 62).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 5.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Leg5 = Body.addOrReplaceChild("Leg5", CubeListBuilder.create(), PartPose.offset(-30.0F, 0.0F, -8.0F));

		PartDefinition UpperPart5 = Leg5.addOrReplaceChild("UpperPart5", CubeListBuilder.create().texOffs(204, 209).addBox(-7.0F, -9.0F, -7.0F, 12.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 0.0F, -15.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition LowerPart5 = UpperPart5.addOrReplaceChild("LowerPart5", CubeListBuilder.create().texOffs(248, 77).addBox(-6.0F, 0.0F, -7.0F, 10.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(260, 275).addBox(4.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(28, 283).addBox(-6.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(206, 102).addBox(-6.0F, 14.0F, -7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(112, 208).addBox(-6.0F, 14.0F, 7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition FEET5 = LowerPart5.addOrReplaceChild("FEET5", CubeListBuilder.create().texOffs(256, 213).addBox(-7.0F, 0.0F, -10.0F, 12.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(292, 259).addBox(-4.0F, -1.0F, -15.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition cube_r5 = FEET5.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(292, 248).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 5.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r6 = FEET5.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(292, 237).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 5.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Leg6 = Body.addOrReplaceChild("Leg6", CubeListBuilder.create(), PartPose.offset(-27.0F, 0.0F, 15.0F));

		PartDefinition UpperPart6 = Leg6.addOrReplaceChild("UpperPart6", CubeListBuilder.create().texOffs(212, 0).addBox(-6.0F, -9.0F, -7.0F, 12.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, 0.0F, -15.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition LowerPart6 = UpperPart6.addOrReplaceChild("LowerPart6", CubeListBuilder.create().texOffs(248, 105).addBox(-5.0F, 0.0F, -7.0F, 10.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 284).addBox(5.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(136, 284).addBox(-5.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(112, 210).addBox(-5.0F, 14.0F, -7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(212, 44).addBox(-5.0F, 14.0F, 7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition FEET6 = LowerPart6.addOrReplaceChild("FEET6", CubeListBuilder.create().texOffs(258, 31).addBox(-6.0F, 0.0F, -10.0F, 12.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(186, 294).addBox(-3.0F, -1.0F, -15.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition cube_r7 = FEET6.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(164, 294).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 5.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r8 = FEET6.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(56, 294).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 5.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Leg2 = Body.addOrReplaceChild("Leg2", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, -8.0F));

		PartDefinition UpperPart2 = Leg2.addOrReplaceChild("UpperPart2", CubeListBuilder.create().texOffs(204, 178).addBox(-5.0F, -9.0F, -7.0F, 12.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 0.0F, -15.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition LowerPart2 = UpperPart2.addOrReplaceChild("LowerPart2", CubeListBuilder.create().texOffs(164, 240).addBox(-4.0F, 0.0F, -7.0F, 10.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(148, 268).addBox(6.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(176, 268).addBox(-4.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(180, 55).addBox(-4.0F, 14.0F, -7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(184, 51).addBox(-4.0F, 14.0F, 7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition FEET2 = LowerPart2.addOrReplaceChild("FEET2", CubeListBuilder.create().texOffs(256, 153).addBox(-5.0F, 0.0F, -10.0F, 12.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(100, 288).addBox(-2.0F, -1.0F, -15.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition cube_r9 = FEET2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(78, 288).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 5.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r10 = FEET2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(56, 283).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 5.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Leg3 = Body.addOrReplaceChild("Leg3", CubeListBuilder.create(), PartPose.offset(10.0F, 0.0F, 0.0F));

		PartDefinition UpperPart3 = Leg3.addOrReplaceChild("UpperPart3", CubeListBuilder.create().texOffs(206, 46).addBox(-6.0F, -9.0F, -7.0F, 12.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition LowerPart3 = UpperPart3.addOrReplaceChild("LowerPart3", CubeListBuilder.create().texOffs(212, 240).addBox(-5.0F, 0.0F, -7.0F, 10.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(204, 268).addBox(5.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(232, 268).addBox(-5.0F, 14.0F, -7.0F, 0.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(184, 53).addBox(-5.0F, 14.0F, -7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(196, 152).addBox(-5.0F, 14.0F, 7.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.1F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition FEET3 = LowerPart3.addOrReplaceChild("FEET3", CubeListBuilder.create().texOffs(256, 173).addBox(-6.0F, 0.0F, -10.0F, 12.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(290, 51).addBox(-3.0F, -1.0F, -15.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition cube_r11 = FEET3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(288, 286).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 5.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r12 = FEET3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(288, 275).addBox(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 5.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition MainBody = Base.addOrReplaceChild("MainBody", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, -37.0F));

		PartDefinition UpperBody = MainBody.addOrReplaceChild("UpperBody", CubeListBuilder.create().texOffs(0, 0).addBox(-17.0F, -15.0F, -29.0F, 34.0F, 28.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(144, 179).addBox(17.0F, 13.0F, -29.0F, 0.0F, 5.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(0, 191).addBox(-17.0F, 13.0F, -29.0F, 0.0F, 5.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(128, 46).addBox(-17.0F, 13.0F, -29.0F, 34.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 58).addBox(-11.0F, -17.0F, -23.0F, 22.0F, 8.0F, 39.0F, new CubeDeformation(0.0F))
		.texOffs(0, 256).addBox(-10.0F, -27.0F, -32.0F, 20.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(122, 58).addBox(-11.0F, -24.0F, -23.0F, 3.0F, 7.0F, 39.0F, new CubeDeformation(0.0F))
		.texOffs(128, 0).addBox(7.0F, -24.0F, -23.0F, 3.0F, 7.0F, 39.0F, new CubeDeformation(0.0F))
		.texOffs(264, 19).addBox(-8.0F, -24.0F, 13.0F, 16.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		PartDefinition cube_r13 = UpperBody.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(212, 31).addBox(-9.0F, -10.0F, -2.0F, 18.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.0F, -21.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition lowerBody = MainBody.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(0, 105).addBox(-12.0F, -12.0F, 0.0F, 24.0F, 24.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(256, 233).addBox(-12.0F, 12.0F, 26.0F, 24.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(112, 214).addBox(12.0F, 12.0F, 0.0F, 0.0F, 4.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(0, 226).addBox(-12.0F, 12.0F, 0.0F, 0.0F, 4.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 1.0F));

		PartDefinition Tail = MainBody.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -5.0F));

		PartDefinition TailSegment1 = Tail.addOrReplaceChild("TailSegment1", CubeListBuilder.create().texOffs(0, 155).addBox(-10.0F, -9.0F, 0.0F, 20.0F, 18.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(206, 77).addBox(-10.0F, 9.0F, 0.0F, 0.0F, 3.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(164, 214).addBox(10.0F, 9.0F, 0.0F, 0.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 32.0F));

		PartDefinition TailSegment2 = TailSegment1.addOrReplaceChild("TailSegment2", CubeListBuilder.create().texOffs(76, 179).addBox(-9.0F, -7.0F, 0.0F, 18.0F, 13.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(258, 51).addBox(-9.0F, 6.0F, 0.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(260, 237).addBox(9.0F, 6.0F, 0.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 18.0F));

		PartDefinition TailSegment3 = TailSegment2.addOrReplaceChild("TailSegment3", CubeListBuilder.create().texOffs(192, 154).addBox(-8.0F, -6.0F, 0.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(260, 256).addBox(8.0F, 2.0F, 0.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(264, 0).addBox(-8.0F, 2.0F, 0.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(128, 55).addBox(-8.0F, 2.0F, 16.0F, 16.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 16.0F));

		PartDefinition head = MainBody.addOrReplaceChild("head", CubeListBuilder.create().texOffs(100, 105).addBox(-13.0F, -15.0F, -19.0F, 26.0F, 27.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(100, 154).addBox(-14.0F, 4.0F, -21.0F, 28.0F, 7.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(224, 284).addBox(-5.0F, 2.0F, -23.0F, 10.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(296, 0).addBox(-20.0F, -22.0F, -12.0F, 3.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(296, 73).addBox(17.0F, -22.0F, -12.0F, 3.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(128, 51).addBox(-13.0F, -15.0F, -21.0F, 26.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(200, 214).addBox(-13.0F, -13.0F, -21.0F, 0.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(242, 77).addBox(13.0F, -13.0F, -21.0F, 0.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(196, 135).addBox(-13.0F, -13.0F, -21.0F, 26.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -28.0F));

		PartDefinition cube_r14 = head.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(194, 284).addBox(0.0F, -2.0F, -3.0F, 9.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -8.0F, -9.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r15 = head.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(164, 284).addBox(-9.0F, -2.0F, -3.0F, 9.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -8.0F, -9.0F, 0.0F, 0.0F, 0.7854F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		SkyBisonEntity bison = (SkyBisonEntity) entity;
		this.animate(bison.walkingAnimationState, ModAnimationDefinitions.WALK, ageInTicks, 1f);
		this.animate(bison.idleAnimationState, ModAnimationDefinitions.IDLE, ageInTicks, 1f);
		this.animate(bison.sittingAnimationState, ModAnimationDefinitions.SIT, ageInTicks, 1f);
		this.animate(bison.flyingAnimationState, ModAnimationDefinitions.FLY, ageInTicks, 1f);
		this.animate(bison.flyingIdleAnimationState, ModAnimationDefinitions.FLY_IDLE, ageInTicks, 1f);
	}

	private void applyHeadRotation(float netHeadYaw, float headPitch, float agaInTicks) {
		netHeadYaw = Mth.clamp(netHeadYaw, -30F, 30F);
		headPitch = Mth.clamp(headPitch, -25F, 45F);

		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F );
		this.head.xRot = headPitch * ((float)Math.PI / 180F );
	}
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return Base;
	}
}
// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.eeeguba.tapiocamod.entity.client;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class TapiocaModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION =  new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("tapiocaplushie","tapioca"),"main");
	private final ModelPart body;
	private final ModelPart LArm;
	private final ModelPart LLeg;
	private final ModelPart RArm;
	private final ModelPart RLeg;
	private final ModelPart root;

	public TapiocaModel(ModelPart root) {
		this.body = root.getChild("body");
		this.root = root;
		this.LArm = this.body.getChild("LArm");
		this.LLeg = this.body.getChild("LLeg");
		this.RArm = this.body.getChild("RArm");
		this.RLeg = this.body.getChild("RLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -3.0F, 8.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

		PartDefinition LArm = body.addOrReplaceChild("LArm", CubeListBuilder.create(), PartPose.offset(2.0F, 2.0F, -3.0F));

		PartDefinition cube_r1 = LArm.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 15).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition LLeg = body.addOrReplaceChild("LLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, 4.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition cube_r2 = LLeg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(8, 15).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition RArm = body.addOrReplaceChild("RArm", CubeListBuilder.create(), PartPose.offset(-2.0F, 2.0F, -3.0F));

		PartDefinition cube_r3 = RArm.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(17, 19).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition RLeg = body.addOrReplaceChild("RLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, 4.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition cube_r4 = RLeg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root.getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(TapiocaAnimations.Walk,limbSwing,limbSwingAmount,4f,10f);//was 2f,2.5f
	}
	@Override 
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	public ModelPart getBody() {
		return this.body;
	}
}
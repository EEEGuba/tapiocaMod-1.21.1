package com.eeeguba.tapiocamod.entity.client;

import com.eeeguba.tapiocamod.entity.custom.TapiocaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;

public class TapiocaCarriedBlockLayer extends RenderLayer<TapiocaEntity, TapiocaModel<TapiocaEntity>> {
    private final BlockRenderDispatcher blockRenderer;

    public TapiocaCarriedBlockLayer(RenderLayerParent<TapiocaEntity, TapiocaModel<TapiocaEntity>> parent,
                                    BlockRenderDispatcher blockRenderer) {
        super(parent);
        this.blockRenderer = blockRenderer;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, TapiocaEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw,
                       float headPitch) {
        BlockState state = entity.getCarriedBlock();
        if (state != null) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.6875F, -0.5F);
            poseStack.mulPose(Axis.XP.rotationDegrees(20.0F));
            poseStack.scale(1.0F, -1.0F, 1.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
            poseStack.translate(-0.5F, -0.5F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            this.blockRenderer.renderSingleBlock(state, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }
}
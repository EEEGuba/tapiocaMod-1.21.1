package com.eeeguba.tapiocamod.entity.client;

import com.eeeguba.tapiocamod.entity.custom.TapiocaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class TapiocaHelmetLayer
        extends RenderLayer<TapiocaEntity, TapiocaModel<TapiocaEntity>> {

    private static final ResourceLocation DUMMY_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/entity/zombie/zombie.png");

    private final HumanoidModel<TapiocaEntity> driverModel;

    private final HumanoidArmorLayer<
            TapiocaEntity,
            HumanoidModel<TapiocaEntity>,
            HumanoidModel<TapiocaEntity>
            > armorLayer;

    public TapiocaHelmetLayer(
            RenderLayerParent<TapiocaEntity, TapiocaModel<TapiocaEntity>> parent,
            EntityRendererProvider.Context context) {
        super(parent);

        // Used as the humanoid parent model for the delegated armor layer.
        this.driverModel = new HumanoidModel<>(
                context.bakeLayer(ModelLayers.PLAYER)
        );

        RenderLayerParent<TapiocaEntity, HumanoidModel<TapiocaEntity>> armorParent =
                new RenderLayerParent<>() {
                    @Override
                    public HumanoidModel<TapiocaEntity> getModel() {
                        return driverModel;
                    }

                    @Override
                    public ResourceLocation getTextureLocation(TapiocaEntity entity) {
                        return DUMMY_TEXTURE;
                    }
                };

        this.armorLayer = new HumanoidArmorLayer<>(
                armorParent,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()
        );
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            TapiocaEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {

        driverModel.setupAnim(
                entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch
        );

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.35F, 0.0F);
        poseStack.scale(1.2F, 1F, 0.9F);

        armorLayer.render(
                poseStack,
                buffer,
                packedLight,
                entity,
                limbSwing,
                limbSwingAmount,
                partialTick,
                ageInTicks,
                netHeadYaw,
                headPitch
        );

        poseStack.popPose();
    }
}
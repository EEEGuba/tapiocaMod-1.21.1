package com.eeeguba.tapiocamod.armor;


import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.LivingEntity;

public class HeadBowArmorModel extends HumanoidModel<LivingEntity> {
    public HeadBowArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        // This is just a compilable placeholder: it still has the standard shape.
        // Replace/add its geometry with the custom helmet geometry.
        return LayerDefinition.create(
                HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F),
                64,
                32
        );
    }
}

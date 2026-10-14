package com.eeeguba.tapiocamod.entity.client;

import javax.annotation.Nonnull;

import com.eeeguba.tapiocamod.Tapiocaplushie;
import com.eeeguba.tapiocamod.entity.custom.TapiocaEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import static com.eeeguba.tapiocamod.entity.custom.TapiocaEntity.getVariant;

public class TapiocaRenderer extends MobRenderer<TapiocaEntity, TapiocaModel<TapiocaEntity>>{
    public TapiocaRenderer(EntityRendererProvider.Context context){
        super(context, new TapiocaModel<>(context.bakeLayer(TapiocaModel.LAYER_LOCATION)),0.25f);
        this.addLayer(new TapiocaCarriedBlockLayer(
                this,
                context.getBlockRenderDispatcher()
        ));
        //this.addLayer(new TapiocaHelmetLayer(this));
        this.addLayer(new TapiocaHelmetLayer(this, context));
    }


    @Override
    public ResourceLocation getTextureLocation(TapiocaEntity entity){
        var variant = getVariant(entity);
        return ResourceLocation.fromNamespaceAndPath(Tapiocaplushie.MODID,"textures/entity/tapioca/tapioca"+variant+".png");
    }

    @Override
    public void render(@Nonnull TapiocaEntity entity, float entityYaw, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight){
        if(entity.isBaby()){
            poseStack.scale(0.45f,0.45f,0.45f);
            }
        else{
            poseStack.scale(1f,1f,1f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack,buffer,packedLight);
    }



}

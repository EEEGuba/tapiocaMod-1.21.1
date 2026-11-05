package com.eeeguba.tapiocamod.armor;

import com.eeeguba.tapiocamod.Tapioca;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(
        modid = Tapioca.MODID,
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ModModelLayers {
    public static final ModelLayerLocation HEAD_BOW =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(Tapioca.MODID, "head_bow"),
                    "main"
            );

    @SubscribeEvent
    public static void registerLayerDefinitions(
            EntityRenderersEvent.RegisterLayerDefinitions event
    ) {
        event.registerLayerDefinition(
                HEAD_BOW,
                HeadBowArmorModel::createBodyLayer
        );
    }
}

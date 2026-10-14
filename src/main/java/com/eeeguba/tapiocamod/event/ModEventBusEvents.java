package com.eeeguba.tapiocamod.event;

import com.eeeguba.tapiocamod.Tapiocaplushie;
import com.eeeguba.tapiocamod.entity.client.TapiocaModel;
import com.eeeguba.tapiocamod.entity.custom.TapiocaEntity;
import com.eeeguba.tapiocamod.entity.ModEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber (modid = Tapiocaplushie.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent 
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(TapiocaModel.LAYER_LOCATION, TapiocaModel::createBodyLayer);
    }
@SubscribeEvent 
public static void registerAttributes(EntityAttributeCreationEvent event){
    event.put(ModEntities.TAPIOCA.get(), TapiocaEntity.createAttributes().build());
}
}

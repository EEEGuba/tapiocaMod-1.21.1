package com.eeeguba.tapiocamod.event;

import com.eeeguba.tapiocamod.Tapioca;
import com.eeeguba.tapiocamod.datagen.ModBlockTags;
import com.eeeguba.tapiocamod.entity.client.TapiocaModel;
import com.eeeguba.tapiocamod.entity.custom.MobEffectHandler;
import com.eeeguba.tapiocamod.entity.custom.TapiocaEntity;
import com.eeeguba.tapiocamod.entity.ModEntities;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber (modid = Tapioca.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent 
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(TapiocaModel.LAYER_LOCATION, TapiocaModel::createBodyLayer);
    }
@SubscribeEvent 
public static void registerAttributes(EntityAttributeCreationEvent event){
    event.put(ModEntities.TAPIOCA.get(), TapiocaEntity.createAttributes().build());
}

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {

        event.register(
                ModEntities.TAPIOCA.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,

                (entityType, level, spawnType, pos, random) -> {

                    return Mob.checkMobSpawnRules(
                            entityType,
                            level,
                            spawnType,
                            pos,
                            random
                    ) && level.getBlockState(pos.below())
                            .is(ModBlockTags.TAPIOCA_SPAWNABLE_ON);
                },

                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }
}

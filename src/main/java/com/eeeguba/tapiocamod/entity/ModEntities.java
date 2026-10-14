package com.eeeguba.tapiocamod.entity;


import java.util.function.Supplier;

import com.eeeguba.tapiocamod.Tapiocaplushie;
import com.eeeguba.tapiocamod.entity.custom.TapiocaEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Tapiocaplushie.MODID);

    public static final Supplier<EntityType<TapiocaEntity>> TAPIOCA = ENTITY_TYPES.register(
        "tapioca",
        () -> EntityType.Builder.<TapiocaEntity>of(
            (type, level) -> new TapiocaEntity(type, level),
            MobCategory.CREATURE
        )
            .sized(0.5F, 0.5F)
            .build("tapioca")
    );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}   

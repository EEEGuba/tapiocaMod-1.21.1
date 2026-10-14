package com.eeeguba.tapiocamod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class TapiocaEntity extends PathfinderMob {
    protected TapiocaEntity(
            EntityType<? extends TapiocaEntity> entityType,
            Level level
    ) {
        super(entityType, level);
    }
}
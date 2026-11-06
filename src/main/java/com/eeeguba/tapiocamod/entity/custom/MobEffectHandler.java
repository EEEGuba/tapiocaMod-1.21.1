package com.eeeguba.tapiocamod.entity.custom;

import com.eeeguba.tapiocamod.Tapioca;
import com.eeeguba.tapiocamod.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class MobEffectHandler {

    public static void onEffectExpired(MobEffectEvent.Expired event) {

        if (!(event.getEntity() instanceof TapiocaEntity tapioca)) {
            return;
        }

        if (!event.getEffectInstance().is(MobEffects.MOVEMENT_SPEED)) {
            return;
        }

        if (!(tapioca.level() instanceof ServerLevel level)) {
            return;
        }

        if (tapioca.isBaby()||tapioca.SPLIT_STOPPER){return;}
        tapioca.SPLIT_STOPPER=true;
        splitTapioca(tapioca, level);
    }

    private static void splitTapioca(TapiocaEntity parent, ServerLevel level) {

        for (int i = 0; i < 2; i++) {

            TapiocaEntity baby = ModEntities.TAPIOCA.get().create(level);
            int babyVariant = TapiocaEntity.getVariant(baby);

            if (baby != null) {
                level.sendParticles(
                        ParticleTypes.POOF,
                        parent.getX(),
                        parent.getY() + parent.getBbHeight() / 2.0,
                        parent.getZ(),
                        30,
                        parent.getBbWidth() / 2.0,
                        parent.getBbHeight() / 2.0,
                        parent.getBbWidth() / 2.0,
                        0.05
                );

                baby.setBaby(true);
                TapiocaEntity.setVariant(baby,babyVariant);
                baby.moveTo(
                        parent.getX() + (i == 0 ? -0.5 : 0.5),
                        parent.getY(),
                        parent.getZ(),
                        parent.getYRot(),
                        0
                );

                level.addFreshEntity(baby);
            }
        }

        parent.discard();
    }
}
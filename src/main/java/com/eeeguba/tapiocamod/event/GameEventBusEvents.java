package com.eeeguba.tapiocamod.event;

import com.eeeguba.tapiocamod.Tapioca;
import com.eeeguba.tapiocamod.entity.custom.MobEffectHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(
        modid = Tapioca.MODID,
        bus = EventBusSubscriber.Bus.GAME
)
public class GameEventBusEvents {

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {

        NeoForge.EVENT_BUS.addListener(MobEffectHandler::onEffectExpired);
    }
}
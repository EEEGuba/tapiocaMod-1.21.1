package com.eeeguba.tapiocamod.item;

import com.eeeguba.tapiocamod.Tapioca;
import com.eeeguba.tapiocamod.entity.ModEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Tapioca.MODID);

       public static final DeferredItem<Item> TAPIOCA_SPAWN_EGG = ITEMS.register("tapioca_spawn_egg",
               () -> new DeferredSpawnEggItem(ModEntities.TAPIOCA,0xFBC1E3,0x000000,
                       new Item.Properties()));

    public static final DeferredItem<Item> HEAD_BOW = ITEMS.register(
            "head_bow",
            () -> new HeadBowItem(new Item.Properties().stacksTo(1))
    );
//    public static final DeferredItem<ArmorItem> HEAD_BOW = ITEMS.register(
//            "head_bow",
//            () -> new HeadBowArmorItem(
//                    ModArmorMaterials.TUNGSTEN_ARMOR_MATERIAL,
//                    ArmorItem.Type.HELMET,
//                    new Item.Properties().durability(
//                            ArmorItem.Type.HELMET.getDurability(19)
//                    )
//            )
//    );
//    public static final DeferredItem<ArmorItem> HEAD_BOW = ITEMS.register("head_bow",
//            () -> new ArmorItem(ModArmorMaterials.TUNGSTEN_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
//                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(19))));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);

    }
}

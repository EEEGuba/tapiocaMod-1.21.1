package com.eeeguba.tapiocamod.item;

import com.eeeguba.tapiocamod.Tapiocaplushie;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Tapiocaplushie.MODID);

    public static final DeferredItem<Item> TUNGSTEN = ITEMS.register("tungsten",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_TUNGSTEN = ITEMS.register("raw_tungsten",
            () -> new Item(new Item.Properties()));

//    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel",
//            () -> new ChiselItem(new Item.Properties().durability(32)));
//
//    public static final DeferredItem<Item> RADISH = ITEMS.register("radish",
//            () -> new Item(new Item.Properties().food(ModFoodProperties.RADISH)) {
//                @Override
//                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//                    tooltipComponents.add(Component.translatable("tooltip.tutorialmod.radish.tooltip"));
//                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//                }
//            });
//
//    public static final DeferredItem<Item> FROSTFIRE_ICE = ITEMS.register("frostfire_ice",
//            () -> new FuelItem(new Item.Properties(), 800));
//    public static final DeferredItem<Item> STARLIGHT_ASHES = ITEMS.register("starlight_ashes",
//            () -> new Item(new Item.Properties()));
//
//    public static final DeferredItem<SwordItem> TUNGSTEN_SWORD = ITEMS.register("tungsten_sword",
//            () -> new SwordItem(ModToolTiers.TUNGSTEN, new Item.Properties()
//                    .attributes(SwordItem.createAttributes(ModToolTiers.TUNGSTEN, 5, -2.4f))));
//    public static final DeferredItem<PickaxeItem> TUNGSTEN_PICKAXE = ITEMS.register("tungsten_pickaxe",
//            () -> new PickaxeItem(ModToolTiers.TUNGSTEN, new Item.Properties()
//                    .attributes(PickaxeItem.createAttributes(ModToolTiers.TUNGSTEN, 1.0F, -2.8f))));
//    public static final DeferredItem<ShovelItem> TUNGSTEN_SHOVEL = ITEMS.register("tungsten_shovel",
//            () -> new ShovelItem(ModToolTiers.TUNGSTEN, new Item.Properties()
//                    .attributes(ShovelItem.createAttributes(ModToolTiers.TUNGSTEN, 1.5F, -3.0f))));
//    public static final DeferredItem<AxeItem> TUNGSTEN_AXE = ITEMS.register("tungsten_axe",
//            () -> new AxeItem(ModToolTiers.TUNGSTEN, new Item.Properties()
//                    .attributes(AxeItem.createAttributes(ModToolTiers.TUNGSTEN, 6.0F, -3.2f))));
//    public static final DeferredItem<HoeItem> TUNGSTEN_HOE = ITEMS.register("tungsten_hoe",
//            () -> new HoeItem(ModToolTiers.TUNGSTEN, new Item.Properties()
//                    .attributes(HoeItem.createAttributes(ModToolTiers.TUNGSTEN, 0F, -3.0f))));
//
//    public static final DeferredItem<HammerItem> TUNGSTEN_HAMMER = ITEMS.register("tungsten_hammer",
//            () -> new HammerItem(ModToolTiers.TUNGSTEN, new Item.Properties()
//                    .attributes(PickaxeItem.createAttributes(ModToolTiers.TUNGSTEN, 7F, -3.5f))));

    public static final DeferredItem<ArmorItem> TUNGSTEN_HELMET = ITEMS.register("tungsten_helmet",
            () -> new ArmorItem(ModArmorMaterials.TUNGSTEN_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(19))));
    public static final DeferredItem<ArmorItem> TUNGSTEN_CHESTPLATE = ITEMS.register("tungsten_chestplate",
            () -> new ArmorItem(ModArmorMaterials.TUNGSTEN_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(19))));
    public static final DeferredItem<ArmorItem> TUNGSTEN_LEGGINGS = ITEMS.register("tungsten_leggings",
            () -> new ArmorItem(ModArmorMaterials.TUNGSTEN_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(19))));
    public static final DeferredItem<ArmorItem> TUNGSTEN_BOOTS = ITEMS.register("tungsten_boots",
            () -> new ArmorItem(ModArmorMaterials.TUNGSTEN_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(19))));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);

    }
}

package com.eeeguba.tapiocamod.datagen;

import com.eeeguba.tapiocamod.Tapiocaplushie;
import com.eeeguba.tapiocamod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Tapiocaplushie.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.TUNGSTEN.get());
        basicItem(ModItems.RAW_TUNGSTEN.get());

        //basicItem(ModItems.RADISH.get());
        //basicItem(ModItems.STARLIGHT_ASHES.get());
        //basicItem(ModItems.FROSTFIRE_ICE.get());
        //basicItem(ModItems.CHISEL.get());
//
        //buttonItem(ModBlocks.TUNGSTEN_BUTTON, ModBlocks.TUNGSTEN_BLOCK);
        //fenceItem(ModBlocks.TUNGSTEN_FENCE, ModBlocks.TUNGSTEN_BLOCK);
        //wallItem(ModBlocks.TUNGSTEN_WALL, ModBlocks.TUNGSTEN_BLOCK);
//
        //basicItem(ModBlocks.TUNGSTEN_DOOR.asItem());
//
        //handheldItem(ModItems.TUNGSTEN_SWORD);
        //handheldItem(ModItems.TUNGSTEN_PICKAXE);
        //handheldItem(ModItems.TUNGSTEN_SHOVEL);
        //handheldItem(ModItems.TUNGSTEN_AXE);
        //handheldItem(ModItems.TUNGSTEN_HOE);
        //handheldItem(ModItems.TUNGSTEN_HAMMER);
//
        trimmedArmorItem(ModItems.TUNGSTEN_HELMET);
        trimmedArmorItem(ModItems.TUNGSTEN_CHESTPLATE);
        trimmedArmorItem(ModItems.TUNGSTEN_LEGGINGS);
        trimmedArmorItem(ModItems.TUNGSTEN_BOOTS);
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MODID = Tapiocaplushie.MODID; // Change this to your mod id

        if(itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MODID,
                                        "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }

//    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
//        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
//                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Tapiocaplushie.MODID,
//                        "block/" + baseBlock.getId().getPath()));
//    }
//
//    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
//        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
//                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Tapiocaplushie.MODID,
//                        "block/" + baseBlock.getId().getPath()));
//    }
//
//    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
//        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
//                .texture("wall",  ResourceLocation.fromNamespaceAndPath(Tapiocaplushie.MODID,
//                        "block/" + baseBlock.getId().getPath()));
//    }
//
//    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
//        return withExistingParent(item.getId().getPath(),
//                ResourceLocation.parse("item/handheld")).texture("layer0",
//                ResourceLocation.fromNamespaceAndPath(Tapiocaplushie.MODID,"item/" + item.getId().getPath()));
//    }
}
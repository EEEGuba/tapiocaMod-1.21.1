package com.eeeguba.tapiocamod.datagen;

import com.eeeguba.tapiocamod.Tapioca;
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

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Tapioca.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        withExistingParent(
//                ModItems.HEAD_BOW.getId().getPath(),
//                modLoc("models/item/head_bow.json")
//
//        );

        withExistingParent(
                ModItems.TAPIOCA_SPAWN_EGG.getId().getPath(),
                mcLoc("item/template_spawn_egg")
        );
    }
}
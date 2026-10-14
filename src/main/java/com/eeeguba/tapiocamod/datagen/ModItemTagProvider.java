package com.eeeguba.tapiocamod.datagen;

import com.eeeguba.tapiocamod.Tapiocaplushie;
import com.eeeguba.tapiocamod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Tapiocaplushie.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
//                .add(ModItems.TUNGSTEN.get())
//                .add(ModItems.RAW_TUNGSTEN.get())
//                .add(Items.COAL)
//                .add(Items.STICK)
//                .add(Items.COMPASS);

//        tag(ItemTags.SWORDS)
//                .add(ModItems.TUNGSTEN_SWORD.get());
//        tag(ItemTags.PICKAXES)
//                .add(ModItems.TUNGSTEN_PICKAXE.get());
//        tag(ItemTags.SHOVELS)
//                .add(ModItems.TUNGSTEN_SHOVEL.get());
//        tag(ItemTags.AXES)
//                .add(ModItems.TUNGSTEN_AXE.get());
//        tag(ItemTags.HOES)
//                .add(ModItems.TUNGSTEN_HOE.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.TUNGSTEN_HELMET.get())
                .add(ModItems.TUNGSTEN_CHESTPLATE.get())
                .add(ModItems.TUNGSTEN_LEGGINGS.get())
                .add(ModItems.TUNGSTEN_BOOTS.get());

    }
}
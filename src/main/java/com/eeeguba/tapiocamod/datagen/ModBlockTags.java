package com.eeeguba.tapiocamod.datagen;

import com.eeeguba.tapiocamod.Tapioca;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> TAPIOCA_SPAWNABLE_ON =
            TagKey.create(
                    Registries.BLOCK,
                    ResourceLocation.fromNamespaceAndPath(
                            Tapioca.MODID,
                            "tapioca_spawnable_on"
                    )
            );
}

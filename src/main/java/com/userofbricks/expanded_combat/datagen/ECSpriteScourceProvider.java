package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.client.sprites.PalettedFolderPermutations;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ECSpriteScourceProvider extends SpriteSourceProvider {
    public ECSpriteScourceProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper, ExpandedCombat.MODID);
    }

    @Override
    protected void addSources() {
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new DirectoryLister("item_large", "item_large/"));
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new DirectoryLister("slot", "slot/"));


        Map<String, ResourceLocation> gauntletTrimPermutations = new HashMap<>();
        gauntletTrimPermutations.put("netherite", new ResourceLocation("trims/color_palettes/netherite"));
        gauntletTrimPermutations.put("quartz", new ResourceLocation("trims/color_palettes/quartz"));
        gauntletTrimPermutations.put("iron", new ResourceLocation("trims/color_palettes/iron"));
        gauntletTrimPermutations.put("gold", new ResourceLocation("trims/color_palettes/gold"));
        gauntletTrimPermutations.put("diamond", new ResourceLocation("trims/color_palettes/diamond"));
        gauntletTrimPermutations.put("redstone", new ResourceLocation("trims/color_palettes/redstone"));
        gauntletTrimPermutations.put("copper", new ResourceLocation("trims/color_palettes/copper"));
        gauntletTrimPermutations.put("emerald", new ResourceLocation("trims/color_palettes/emerald"));
        gauntletTrimPermutations.put("lapis", new ResourceLocation("trims/color_palettes/lapis"));
        gauntletTrimPermutations.put("amethyst", new ResourceLocation("trims/color_palettes/amethyst"));
        gauntletTrimPermutations.put("iron_darker", new ResourceLocation("trims/color_palettes/iron_darker"));
        gauntletTrimPermutations.put("gold_darker", new ResourceLocation("trims/color_palettes/gold_darker"));
        gauntletTrimPermutations.put("diamond_darker", new ResourceLocation("trims/color_palettes/diamond_darker"));
        gauntletTrimPermutations.put("netherite_darker", new ResourceLocation("trims/color_palettes/netherite_darker"));

        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new PalettedFolderPermutations(
                List.of(new ResourceLocation(ExpandedCombat.MODID, "trims/items/gauntlet_trim")),
                new ResourceLocation("item/color_palettes/trim_template"),
                false,
                gauntletTrimPermutations));


        Map<String, ResourceLocation> weaponPermutations = new HashMap<>();
        weaponPermutations.put("netherite", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/netherite"));
        weaponPermutations.put("stone", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/stone"));
        weaponPermutations.put("iron", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/iron"));
        weaponPermutations.put("gold", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/gold"));
        weaponPermutations.put("diamond", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/diamond"));
        weaponPermutations.put("acacia_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/acacia_plank"));
        weaponPermutations.put("bamboo_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/bamboo_plank"));
        weaponPermutations.put("birch_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/birch_plank"));
        weaponPermutations.put("cherry_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/cherry_plank"));
        weaponPermutations.put("crimson_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/crimson_plank"));
        weaponPermutations.put("dark_oak_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/dark_oak_plank"));
        weaponPermutations.put("jungle_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/jungle_plank"));
        weaponPermutations.put("mangrove_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/mangrove_plank"));
        weaponPermutations.put("oak_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/oak_plank"));
        weaponPermutations.put("spruce_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/spruce_plank"));
        weaponPermutations.put("warped_plank", new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/warped_plank"));

        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new PalettedFolderPermutations(
                Arrays.asList(new ResourceLocation(ExpandedCombat.MODID, "item/battle_staff"),
                        new ResourceLocation(ExpandedCombat.MODID, "item/broad_sword"),
                        new ResourceLocation(ExpandedCombat.MODID, "item/claymore"),
                        new ResourceLocation(ExpandedCombat.MODID, "item/cutlass"),
                        new ResourceLocation(ExpandedCombat.MODID, "item/dagger"),
                        new ResourceLocation(ExpandedCombat.MODID, "item/dancer_s_sword"),
                        new ResourceLocation(ExpandedCombat.MODID, "item/glaive"),
                        new ResourceLocation(ExpandedCombat.MODID, "item/katana"),
                        new ResourceLocation(ExpandedCombat.MODID, "item/scythe"),
                        new ResourceLocation(ExpandedCombat.MODID, "item/sickle"),
                        new ResourceLocation(ExpandedCombat.MODID, "item/spear"),
                        new ResourceLocation(ExpandedCombat.MODID, "item_large/battle_staff"),
                        new ResourceLocation(ExpandedCombat.MODID, "item_large/broad_sword"),
                        new ResourceLocation(ExpandedCombat.MODID, "item_large/claymore"),
                        new ResourceLocation(ExpandedCombat.MODID, "item_large/dancer_s_sword"),
                        new ResourceLocation(ExpandedCombat.MODID, "item_large/glaive"),
                        new ResourceLocation(ExpandedCombat.MODID, "item_large/katana"),
                        new ResourceLocation(ExpandedCombat.MODID, "item_large/scythe"),
                        new ResourceLocation(ExpandedCombat.MODID, "item_large/spear")),
                new ResourceLocation(ExpandedCombat.MODID, "item/color_palettes/template"),
                true,
                weaponPermutations));
    }
}

package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.client.sprites.AlphaMaskFolderPermutations;
import com.userofbricks.expanded_combat.client.sprites.PalettedFolderPermutations;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

import java.util.*;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@OnlyIn(Dist.CLIENT)
public class ECSpriteScourceProvider extends SpriteSourceProvider {
    public ECSpriteScourceProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper, ExpandedCombat.MODID);
    }

    @Override
    protected void addSources() {
        atlas(BLOCKS_ATLAS).addSource(new DirectoryLister("item_large", "item_large/"));
        atlas(BLOCKS_ATLAS).addSource(new DirectoryLister("slot", "slot/"));


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

        atlas(BLOCKS_ATLAS).addSource(new PalettedFolderPermutations(
                List.of(modLoc( "trims/items/gauntlet_trim")),
                new ResourceLocation("trims/color_palettes/trim_palette"),
                false,
                gauntletTrimPermutations));


        Map<String, ResourceLocation> weaponPermutations = new HashMap<>();
        weaponPermutations.put("netherite", modLoc( "item/color_palettes/netherite"));
        weaponPermutations.put("stone", modLoc( "item/color_palettes/stone"));
        weaponPermutations.put("iron", modLoc( "item/color_palettes/iron"));
        weaponPermutations.put("gold", modLoc( "item/color_palettes/gold"));
        weaponPermutations.put("diamond", modLoc( "item/color_palettes/diamond"));
        weaponPermutations.put("acacia_plank", modLoc( "item/color_palettes/acacia_plank"));
        weaponPermutations.put("bamboo_plank", modLoc( "item/color_palettes/bamboo_plank"));
        weaponPermutations.put("birch_plank", modLoc( "item/color_palettes/birch_plank"));
        weaponPermutations.put("cherry_plank", modLoc( "item/color_palettes/cherry_plank"));
        weaponPermutations.put("crimson_plank", modLoc( "item/color_palettes/crimson_plank"));
        weaponPermutations.put("dark_oak_plank", modLoc( "item/color_palettes/dark_oak_plank"));
        weaponPermutations.put("jungle_plank", modLoc( "item/color_palettes/jungle_plank"));
        weaponPermutations.put("mangrove_plank", modLoc( "item/color_palettes/mangrove_plank"));
        weaponPermutations.put("oak_plank", modLoc( "item/color_palettes/oak_plank"));
        weaponPermutations.put("spruce_plank", modLoc( "item/color_palettes/spruce_plank"));
        weaponPermutations.put("warped_plank", modLoc( "item/color_palettes/warped_plank"));

        atlas(BLOCKS_ATLAS).addSource(new PalettedFolderPermutations(
                Arrays.asList(modLoc( "item/battle_staff"),
                        modLoc( "item/broad_sword"),
                        modLoc( "item/claymore"),
                        modLoc( "item/cutlass"),
                        modLoc( "item/dagger"),
                        modLoc( "item/dancer_s_sword"),
                        modLoc( "item/glaive"),
                        modLoc( "item/katana"),
                        modLoc( "item/scythe"),
                        modLoc( "item/sickle"),
                        modLoc( "item/spear"),
                        modLoc( "item_large/battle_staff"),
                        modLoc( "item_large/broad_sword"),
                        modLoc( "item_large/claymore"),
                        modLoc( "item_large/dancer_s_sword"),
                        modLoc( "item_large/glaive"),
                        modLoc( "item_large/katana"),
                        modLoc( "item_large/scythe"),
                        modLoc( "item_large/spear")),
                modLoc( "item/color_palettes/template"),
                true,
                weaponPermutations));

        atlas(SHIELD_PATTERNS_ATLAS).addSource(new SingleFile(modLoc( "model/shields/shield_base"), Optional.empty()));

        atlas(SHIELD_PATTERNS_ATLAS).addSource(new DirectoryLister("model/shields", "model/shields/"));


        atlas(SHIELD_PATTERNS_ATLAS).addSource(new AlphaMaskFolderPermutations(
                List.of(modLoc( "model/shields/empty/dl"),
                        modLoc( "model/shields/empty/dr"),
                        modLoc( "model/shields/empty/ul"),
                        modLoc( "model/shields/empty/ur"),
                        modLoc( "model/shields/m")), true, "model/shields/bases"));
    }
}

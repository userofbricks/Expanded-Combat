package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.client.sprites.AlphaMaskFolderPermutations;
import com.userofbricks.expanded_combat.client.sprites.PalettedFolderPermutations;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterSpriteSourceTypesEvent;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class SpriteSourceTypes {
    public static SpriteSourceType PALETTED_FOLDER_PERMUTATIONS;
    public static SpriteSourceType ALPHA_MASK_FOLDER_PERMUTATIONS;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerSpriteSourceTypes(RegisterSpriteSourceTypesEvent event) {
        ALPHA_MASK_FOLDER_PERMUTATIONS = event.register(modLoc("alpha_mask_folder_permutations"), AlphaMaskFolderPermutations.CODEC);
        PALETTED_FOLDER_PERMUTATIONS = event.register(modLoc("paletted_folder_permutations"), PalettedFolderPermutations.CODEC);
    }
}

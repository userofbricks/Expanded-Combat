package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.client.sprites.AlphaMaskFolderPermutations;
import com.userofbricks.expanded_combat.client.sprites.PalettedFolderPermutations;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpriteSourceTypes {
    public static final SpriteSourceType PALETTED_FOLDER_PERMUTATIONS = SpriteSources.register("ec_paletted_folder_permutations", PalettedFolderPermutations.CODEC);
    public static final SpriteSourceType ALPHA_MASK_FOLDER_PERMUTATIONS = SpriteSources.register("ec_alpha_mask_folder_permutations", AlphaMaskFolderPermutations.CODEC);

    public static void load(){}
}

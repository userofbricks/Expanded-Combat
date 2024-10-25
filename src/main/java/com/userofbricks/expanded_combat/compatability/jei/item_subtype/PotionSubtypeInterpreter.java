package com.userofbricks.expanded_combat.compatability.jei.item_subtype;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PotionSubtypeInterpreter  implements ISubtypeInterpreter<ItemStack> {
    public static final PotionSubtypeInterpreter INSTANCE = new PotionSubtypeInterpreter();

    private PotionSubtypeInterpreter() {

    }

    @Override
    public @Nullable Object getSubtypeData(ItemStack ingredient, @NotNull UidContext context) {
        PotionContents contents = ingredient.get(DataComponents.POTION_CONTENTS);
        if (contents == null) {
            return null;
        }
        return contents.potion()
                .orElse(null);
    }

    @Override
    public @NotNull String getLegacyStringSubtypeInfo(@NotNull ItemStack ingredient, @NotNull UidContext context) {
        if (ingredient.getComponentsPatch().isEmpty()) {
            return "";
        }
        PotionContents contents = ingredient.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        String itemDescriptionId = ingredient.getItem().getDescriptionId();
        String potionEffectId = contents.potion().map(Holder::getRegisteredName).orElse("none");
        return itemDescriptionId + ".effect_id." + potionEffectId;
    }
}
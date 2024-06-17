package com.userofbricks.expanded_combat.compatability.jei.item_subtype;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.NotNull;

public class PotionSubtypeInterpreter  implements IIngredientSubtypeInterpreter<ItemStack> {
    public static final PotionSubtypeInterpreter INSTANCE = new PotionSubtypeInterpreter();

    private PotionSubtypeInterpreter() {

    }

    @Override
    public @NotNull String apply(ItemStack itemStack, @NotNull UidContext context) {
        if (itemStack.getComponentsPatch().isEmpty()) {
            return IIngredientSubtypeInterpreter.NONE;
        }
        PotionContents contents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        String itemDescriptionId = itemStack.getItem().getDescriptionId();
        String potionEffectId = contents.potion().map(Holder::getRegisteredName).orElse("none");
        return itemDescriptionId + ".effect_id." + potionEffectId;
    }
}
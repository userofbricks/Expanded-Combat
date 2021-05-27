package com.userofbricks.expandedcombat.jei;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;

import java.util.List;

public class PotionSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
    public static final PotionSubtypeInterpreter INSTANCE = new PotionSubtypeInterpreter();

    private PotionSubtypeInterpreter() {

    }

    @Override
    public String apply(ItemStack itemStack, UidContext context) {
        if (!itemStack.hasTag()) {
            return IIngredientSubtypeInterpreter.NONE;
        }
        Potion potionType = PotionUtils.getPotion(itemStack);
        String potionTypeString = potionType.getName(""); // idk if this is the right method
        StringBuilder stringBuilder = new StringBuilder(potionTypeString);
        List<EffectInstance> effects = PotionUtils.getCustomEffects(itemStack);
        for (EffectInstance effect : effects) {
            stringBuilder.append(";").append(effect);
        }

        return stringBuilder.toString();
    }
}

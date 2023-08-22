package com.userofbricks.expanded_combat.compatability.jei.item_subtype;

import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.item.materials.Material;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShieldSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
    public static final ShieldSubtypeInterpreter INSTANCE = new ShieldSubtypeInterpreter();

    private ShieldSubtypeInterpreter() {}

    @Override
    public @NotNull String apply(ItemStack itemStack, @NotNull UidContext context) {
        if (!itemStack.hasTag()) {
            return IIngredientSubtypeInterpreter.NONE;
        }
        String ul_material = ECShieldItem.getUpperLeftMaterial(itemStack);
        String ur_material = ECShieldItem.getUpperRightMaterial(itemStack);
        String dl_material = ECShieldItem.getDownLeftMaterial(itemStack);
        String dr_material = ECShieldItem.getDownRightMaterial(itemStack);
        String m_material = ECShieldItem.getMiddleMaterial(itemStack);

        return ul_material + ";" + ur_material + ";" + dl_material + ";" + dr_material + ";" + m_material;
    }
}

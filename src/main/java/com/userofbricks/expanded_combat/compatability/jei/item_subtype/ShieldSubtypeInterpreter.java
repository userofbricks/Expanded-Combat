package com.userofbricks.expanded_combat.compatability.jei.item_subtype;

import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShieldSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
    public static final ShieldSubtypeInterpreter INSTANCE = new ShieldSubtypeInterpreter();

    private ShieldSubtypeInterpreter() {}

    @Override
    public @NotNull String apply(ItemStack itemStack, @NotNull UidContext context) {
        ShieldMaterials materials = itemStack.get(ItemDataComponents.SHIELD_MATERIALS);
        if (materials != null) {
            String ul_material = materials.ULMaterial().id().getPath();
            String ur_material = materials.URMaterial().id().getPath();
            String dl_material = materials.DLMaterial().id().getPath();
            String dr_material = materials.DRMaterial().id().getPath();
            String m_material = materials.MMaterial().id().getPath();

            return ul_material + ";" + ur_material + ";" + dl_material + ";" + dr_material + ";" + m_material;
        }
        return IIngredientSubtypeInterpreter.NONE;
    }
}

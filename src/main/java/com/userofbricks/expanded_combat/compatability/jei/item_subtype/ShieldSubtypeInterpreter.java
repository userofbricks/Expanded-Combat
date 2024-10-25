package com.userofbricks.expanded_combat.compatability.jei.item_subtype;

import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShieldSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    public static final ShieldSubtypeInterpreter INSTANCE = new ShieldSubtypeInterpreter();

    private ShieldSubtypeInterpreter() {}

    @Override
    public @Nullable Object getSubtypeData(ItemStack ingredient, @NotNull UidContext context) {
        return ingredient.get(ItemDataComponents.SHIELD_MATERIALS);
    }

    @Override
    public @NotNull String getLegacyStringSubtypeInfo(@NotNull ItemStack ingredient, @NotNull UidContext context) {
        ShieldMaterials materials = ingredient.get(ItemDataComponents.SHIELD_MATERIALS);
        if (materials != null) {
            String ul_material = materials.ULMaterial().id().getPath();
            String ur_material = materials.URMaterial().id().getPath();
            String dl_material = materials.DLMaterial().id().getPath();
            String dr_material = materials.DRMaterial().id().getPath();
            String m_material = materials.MMaterial().id().getPath();

            return ul_material + ";" + ur_material + ";" + dl_material + ";" + dr_material + ";" + m_material;
        }
        return "";
    }
}

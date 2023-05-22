package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.world.item.Item;

public class ShieldBuilder extends MaterialBuilder{
    public static RegistryEntry<Item> createModelItem(String locationName, String suffix) {
        return ExpandedCombat.REGISTRATE.get().item("shield_model/" + locationName + "_" + suffix, Item::new)
                .model((ctx, prov) -> prov.withExistingParent("item/" + ctx.getName(), prov.modLoc("item/bases/shield_" + suffix))
                        .texture("base", prov.modLoc("item/shields/" + locationName)))
                .register();
    }
}

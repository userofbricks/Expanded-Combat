package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.tags.ECItemTags;
import net.minecraft.resources.ResourceLocation;

public class ECItems
{

    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_1 = ExpandedCombat.REGISTRATE.get().item("shield_1", (properties -> new ECShieldItem(false, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(ExpandedCombat.MODID, "item/bases/shield")))
            .register();
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_2 = ExpandedCombat.REGISTRATE.get().item("shield_2", (properties -> new ECShieldItem(false, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(ExpandedCombat.MODID, "item/bases/shield")))
            .register();
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_3 = ExpandedCombat.REGISTRATE.get().item("shield_3", (properties -> new ECShieldItem(true, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(ExpandedCombat.MODID, "item/bases/shield")))
            .register();
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_4 = ExpandedCombat.REGISTRATE.get().item("shield_4", (properties -> new ECShieldItem(true, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(ExpandedCombat.MODID, "item/bases/shield")))
            .register();

    public static void loadClass() {}

}

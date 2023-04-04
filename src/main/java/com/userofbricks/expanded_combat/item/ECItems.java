package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.tags.ECItemTags;
import com.userofbricks.expanded_combat.values.ECConfig;
import com.userofbricks.expanded_combat.values.GauntletMaterial;
import net.minecraft.resources.ResourceLocation;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;

public class ECItems
{

    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_1 = REGISTRATE.get().item("shield_1", (properties -> new ECShieldItem(false, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(MODID, "item/bases/shield"))
                    .override().predicate(new ResourceLocation("blocking"), 1.0f).model(prov.getExistingFile(new ResourceLocation(MODID, "item/bases/shield_blocking"))))
            .register();
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_2 = REGISTRATE.get().item("shield_2", (properties -> new ECShieldItem(false, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(MODID, "item/bases/shield"))
                    .override().predicate(new ResourceLocation("blocking"), 1.0f).model(prov.getExistingFile(new ResourceLocation(MODID, "item/bases/shield_blocking"))))
            .register();
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_3 = REGISTRATE.get().item("shield_3", (properties -> new ECShieldItem(true, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(MODID, "item/bases/shield"))
                    .override().predicate(new ResourceLocation("blocking"), 1.0f).model(prov.getExistingFile(new ResourceLocation(MODID, "item/bases/shield_blocking"))))
            .register();
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_4 = REGISTRATE.get().item("shield_4", (properties -> new ECShieldItem(true, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(MODID, "item/bases/shield"))
                    .override().predicate(new ResourceLocation("blocking"), 1.0f).model(prov.getExistingFile(new ResourceLocation(MODID, "item/bases/shield_blocking"))))
            .register();

    public static void loadClass() {
        for (GauntletMaterial gm : ECConfig.SERVER.gauntletMaterials) { gm.registerElements(); }
    }

}

package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.values.ECConfig;
import com.userofbricks.expanded_combat.values.GauntletMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Arrays;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;

public class ECItems
{
    public static ArrayList<RegistryEntry<? extends Item>> ITEMS = new ArrayList<>();

    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_1 = registerShield("shield_1", false);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_2 = registerShield("shield_2", false);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_3 = registerShield("shield_3", true);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_4 = registerShield("shield_4", true);

    public static void loadClass() {
        for (GauntletMaterial gm : ECConfig.SERVER.gauntletMaterials) { gm.registerElements(); }
    }

    private static RegistryEntry<ECShieldItem> registerShield(String name, boolean fireresistant) {
        RegistryEntry<ECShieldItem> shieldRegistryEntry = REGISTRATE.get().item(name, (properties -> new ECShieldItem(fireresistant, properties)))
                .lang("Shield")
                .tag(ECItemTags.SHIELDS)
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(MODID, "item/bases/shield"))
                        .override().predicate(new ResourceLocation("blocking"), 1.0f).model(prov.getExistingFile(new ResourceLocation(MODID, "item/bases/shield_blocking"))))
                .register();
        ITEMS.add(shieldRegistryEntry);
        return shieldRegistryEntry;
    }
}

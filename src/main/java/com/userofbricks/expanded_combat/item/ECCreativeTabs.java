package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.item.materials.GauntletMaterial;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.ShieldMaterial;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;
import static com.userofbricks.expanded_combat.item.ECItems.SHIELD_TIER_1;
import static com.userofbricks.expanded_combat.item.ECItems.SHIELD_TIER_3;

public class ECCreativeTabs {
    public static final Supplier<CreativeModeTab> EC_GROUP = REGISTRATE.get().buildCreativeModeTab("ec_group",
            builder -> builder.icon(() -> new ItemStack(getIcon()))
                    .displayItems((displayParameters, output) -> addItems(output))
                    .build(),
            "Expanded Combat");

    public static void loadClass() {
        REGISTRATE.get().modifyCreativeModeTab(()-> CreativeModeTabs.COMBAT, ECCreativeTabs::addItems);
    }

    private static void addItems(CreativeModeTab.Output output) {
        if (CONFIG.enableGauntlets) {
            for (GauntletMaterial material : MaterialInit.gauntletMaterials) {
                output.accept(material.getGauntletEntry().get());
            }
        }
        if (CONFIG.enableShields) {
            for (ShieldMaterial material : MaterialInit.shieldMaterials) {
                ItemStack stack;
                if (!material.getFireResistant()) {
                    stack = SHIELD_TIER_1.get().getDefaultInstance();
                } else {
                    stack = SHIELD_TIER_3.get().getDefaultInstance();
                }
                stack.getOrCreateTag().putString(ECShieldItem.ULMaterialTagName, material.getName());
                stack.getOrCreateTag().putString(ECShieldItem.URMaterialTagName, material.getName());
                stack.getOrCreateTag().putString(ECShieldItem.DLMaterialTagName, material.getName());
                stack.getOrCreateTag().putString(ECShieldItem.DRMaterialTagName, material.getName());
                stack.getOrCreateTag().putString(ECShieldItem.MMaterialTagName, material.getName());
                output.accept(stack);
            }
        }

    }

    private static Item getIcon() {
        if(CONFIG.enableGauntlets) return MaterialInit.DIAMOND_GAUNTLET.getGauntletEntry().get();
        return Items.ARROW;
    }
}

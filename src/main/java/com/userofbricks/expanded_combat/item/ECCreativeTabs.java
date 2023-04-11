package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.values.ECConfig;
import com.userofbricks.expanded_combat.values.GauntletMaterial;
import com.userofbricks.expanded_combat.values.ShieldMaterial;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;
import static com.userofbricks.expanded_combat.item.ECItems.SHIELD_TIER_1;
import static com.userofbricks.expanded_combat.item.ECItems.SHIELD_TIER_3;

public class ECCreativeTabs {
    public static final Supplier<CreativeModeTab> EC_GROUP = REGISTRATE.get().buildCreativeModeTab("ec_group",
            builder -> builder.icon(() -> new ItemStack(getIcon()))
                    .displayItems((featureFlagSet, output, isOped) -> addItems(output))
                    .build(),
            "Expanded Combat");

    public static void loadClass() {
        REGISTRATE.get().modifyCreativeModeTab(()-> CreativeModeTabs.COMBAT, ECCreativeTabs::addItems);
    }

    private static void addItems(CreativeModeTab.Output output) {
        if (ECConfig.SERVER.enableGauntlets.get()) {
            for (GauntletMaterial material : ECConfig.SERVER.gauntletMaterials) {
                output.accept(material.getGauntletEntry().get());
            }
        }
        for (ShieldMaterial material : ECConfig.SERVER.shieldMaterials) {
            ItemStack stack;
            if (!material.getFireResistant()){
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

    private static Item getIcon() {
        if(ECConfig.SERVER.enableGauntlets.get()) return ECConfig.SERVER.diamondGauntlet.getGauntletEntry().get();
        return Items.ARROW;
    }
}

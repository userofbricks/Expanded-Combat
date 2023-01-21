package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.tags.ECItemTags;
import com.userofbricks.expanded_combat.values.ECConfig;
import com.userofbricks.expanded_combat.values.GauntletMaterial;
import com.userofbricks.expanded_combat.values.ShieldMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.item.ECCreativeTabs.EC_GROUP;

public class ECItems
{

    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_1 = ExpandedCombat.REGISTRATE.get().item("shield_1", (properties -> new ECShieldItem(false, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("item/shield")))
            .register();
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_2 = ExpandedCombat.REGISTRATE.get().item("shield_2", (properties -> new ECShieldItem(false, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("item/shield")))
            .register();
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_3 = ExpandedCombat.REGISTRATE.get().item("shield_3", (properties -> new ECShieldItem(true, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("item/shield")))
            .register();
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_4 = ExpandedCombat.REGISTRATE.get().item("shield_4", (properties -> new ECShieldItem(true, properties)))
            .lang("Shield")
            .tag(ECItemTags.SHIELDS)
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("item/shield")))
            .register();

    public static void loadClass() {}
}

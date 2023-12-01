package com.userofbricks.expanded_combat.init;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.loot.AddItemFromGauntletModifier;
import com.userofbricks.expanded_combat.loot.AddItemModifier;
import com.userofbricks.expanded_combat.loot.AddItemWithoutGauntletModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM_FROM_GAUNTLET = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_item_from_gauntlet", AddItemFromGauntletModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM_WITHOUT_GAUNTLET = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_item_without_gauntlet", AddItemWithoutGauntletModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_item", AddItemModifier.CODEC);
}

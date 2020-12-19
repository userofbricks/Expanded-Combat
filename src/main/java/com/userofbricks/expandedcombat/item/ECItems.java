package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ECItems {
    private static final ItemGroup EC_GROUP = ExpandedCombat.EC_GROUP;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExpandedCombat.MODID);

    public static final RegistryObject<Item> QUIVER = ITEMS.register("quiver", () -> new QuiverItem(new Item.Properties().group(EC_GROUP).maxStackSize(1)));

    public static final RegistryObject<Item> IRON_ARROW = ITEMS.register("iron_arrow", () -> new ECArrowItem(3, ArrowType.IRON, new Item.Properties().group(EC_GROUP)));
    public static final RegistryObject<Item> IRON_TIPPED_ARROW = ITEMS.register("iron_tipped_arrow", () -> new ECTippedArrowItem(3, ArrowType.IRON, new Item.Properties().group(EC_GROUP)));
    public static final RegistryObject<Item> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new ECArrowItem(4, ArrowType.DIAMOND, new Item.Properties().group(EC_GROUP)));
    public static final RegistryObject<Item> DIAMOND_TIPPED_ARROW = ITEMS.register("diamond_tipped_arrow", () -> new ECTippedArrowItem(4, ArrowType.DIAMOND, new Item.Properties().group(EC_GROUP)));
    public static final RegistryObject<Item> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new ECArrowItem(5, ArrowType.NETHERITE, new Item.Properties().group(EC_GROUP).isImmuneToFire()));
    public static final RegistryObject<Item> NETHERITE_TIPPED_ARROW = ITEMS.register("netherite_tipped_arrow", () -> new ECTippedArrowItem(5, ArrowType.NETHERITE, new Item.Properties().group(EC_GROUP).isImmuneToFire()));

    public static final RegistryObject<Item> NETHERITE_GAUNTLET = ITEMS.register("netherite_gauntlet", () -> new GauntletItem(GauntletMaterials.netherite, new Item.Properties().group(EC_GROUP).isImmuneToFire()));
    public static final RegistryObject<Item> DIAMOND_GAUNTLET = ITEMS.register("diamond_gauntlet", () -> new GauntletItem(GauntletMaterials.diamond, new Item.Properties().group(EC_GROUP)));
    public static final RegistryObject<Item> GOLD_GAUNTLET = ITEMS.register("gold_gauntlet", () -> new GauntletItem(GauntletMaterials.gold, new Item.Properties().group(EC_GROUP)));
    public static final RegistryObject<Item> IRON_GAUNTLET = ITEMS.register("iron_gauntlet", () -> new GauntletItem(GauntletMaterials.iron, new Item.Properties().group(EC_GROUP)));
    public static final RegistryObject<Item> LEATHER_GAUNTLET = ITEMS.register("leather_gauntlet", () -> new GauntletItem(GauntletMaterials.leather, new Item.Properties().group(EC_GROUP)));


    public static final RegistryObject<Item> IRON_BOW_HALF = ITEMS.register("iron_bow_half", () -> new ECBowItem(2f, (new Item.Properties()).maxDamage(414).group(EC_GROUP)));
    public static final RegistryObject<Item> IRON_BOW = ITEMS.register("iron_bow", () -> new ECBowItem(3.5f, 1, (new Item.Properties()).maxDamage(480).group(EC_GROUP)));
    public static final RegistryObject<Item> DIAMOND_BOW_HALF = ITEMS.register("diamond_bow_half", () -> new ECBowItem(2.5f, 1, (new Item.Properties()).maxDamage(576).group(EC_GROUP)));
    public static final RegistryObject<Item> DIAMOND_BOW = ITEMS.register("diamond_bow", () -> new ECBowItem(4f, 2, (new Item.Properties()).maxDamage(672).group(EC_GROUP)));
    public static final RegistryObject<Item> NETHERITE_BOW = ITEMS.register("netherite_bow", () -> new ECBowItem(4.5f, 3, (new Item.Properties()).maxDamage(768).group(EC_GROUP).isImmuneToFire()));

    public static final RegistryObject<Item> IRON_SHIELD = ITEMS.register("shield_i4", () -> new ECShieldItem(Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(736).group(EC_GROUP)/*.setISTER(() -> ShieldTileEntityRenderer::new)*/));
    public static final RegistryObject<Item> GOLD_SHIELD = ITEMS.register("shield_g2", () -> new ECShieldItem(2f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).maxDamage(386).group(EC_GROUP)/*.setISTER(() -> ShieldTileEntityRenderer::new)*/));
    public static final RegistryObject<Item> DIAMOND_SHIELD = ITEMS.register("shield_d4", () -> new ECShieldItem(-1f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1936).group(EC_GROUP)/*.setISTER(() -> ShieldTileEntityRenderer::new)*/));
    public static final RegistryObject<Item> NETHERITE_SHIELD = ITEMS.register("shield_n", () -> new ECShieldItem(Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).maxDamage(3336).group(EC_GROUP)/*.setISTER(() -> ShieldTileEntityRenderer::new)*/.isImmuneToFire()));
    public static final RegistryObject<Item> SHIELD_1 = ITEMS.register("shield_g1_d1_i1", () -> new ECShieldItem(0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(861).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_2 = ITEMS.register("shield_g1_d1", () -> new ECShieldItem(0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(761).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_3 = ITEMS.register("shield_g1_d2", () -> new ECShieldItem(0.5f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1161).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_4 = ITEMS.register("shield_g1_d3_n", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).maxDamage(2749).group(EC_GROUP)/**/.isImmuneToFire()));
    public static final RegistryObject<Item> SHIELD_5 = ITEMS.register("shield_g1_d3", () -> new ECShieldItem(-0.25f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1549).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_6 = ITEMS.register("shield_g1_i1_d1", () -> new ECShieldItem(0.75f, Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(861).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_7 = ITEMS.register("shield_g1_i1", () -> new ECShieldItem(1f, Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(461).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_8 = ITEMS.register("shield_g1_i2_d1", () -> new ECShieldItem(0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(861).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_9 = ITEMS.register("shield_g1_i2", () -> new ECShieldItem(1f, Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(561).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_10 = ITEMS.register("shield_g1", () -> new ECShieldItem(1f, ItemTags.PLANKS, (new Item.Properties()).maxDamage(361).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_11 = ITEMS.register("shield_g2_d1", () -> new ECShieldItem(1.25f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).maxDamage(774).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_12 = ITEMS.register("shield_g2_d2", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).maxDamage(1161).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_13 = ITEMS.register("shield_g2_d3_n", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).maxDamage(2749).group(EC_GROUP)/**/.isImmuneToFire()));
    public static final RegistryObject<Item> SHIELD_14 = ITEMS.register("shield_g2_d3", () -> new ECShieldItem(-0.25f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).maxDamage(1549).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_15 = ITEMS.register("shield_i1_d1_g1", () -> new ECShieldItem(0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(861).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_16 = ITEMS.register("shield_i1_d1_i1_d1", () -> new ECShieldItem(-0.5f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1336).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_17 = ITEMS.register("shield_i1_d1_i1", () -> new ECShieldItem(-0.25f, ItemTags.PLANKS, (new Item.Properties()).maxDamage(936).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_18 = ITEMS.register("shield_i1_d1_i2", () -> new ECShieldItem(-0.25f, Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(1036).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_19 = ITEMS.register("shield_i1_d1", () -> new ECShieldItem(-0.25f, ItemTags.PLANKS, (new Item.Properties()).maxDamage(836).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_20 = ITEMS.register("shield_i1_d2", () -> new ECShieldItem(-0.5f, ItemTags.PLANKS, (new Item.Properties()).maxDamage(1236).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_21 = ITEMS.register("shield_i1_d3_n", () -> new ECShieldItem(Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).maxDamage(2836).group(EC_GROUP)/**/.isImmuneToFire()));
    public static final RegistryObject<Item> SHIELD_22 = ITEMS.register("shield_i1_d3", () -> new ECShieldItem(-0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1636).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_23 = ITEMS.register("shield_i1_g1_d1", () -> new ECShieldItem(0.75f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).maxDamage(861).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_24 = ITEMS.register("shield_i1_g1_d2", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).maxDamage(1161).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_25 = ITEMS.register("shield_i1_g1_d3_n", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).maxDamage(336).group(EC_GROUP)/**/.isImmuneToFire()));
    public static final RegistryObject<Item> SHIELD_26 = ITEMS.register("shield_i1_g1_d3", () -> new ECShieldItem(-0.25f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1549).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_27 = ITEMS.register("shield_i1_g1_i1", () -> new ECShieldItem(1f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).maxDamage(561).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_28 = ITEMS.register("shield_i1_g1", () -> new ECShieldItem(1f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).maxDamage(461).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_29 = ITEMS.register("shield_i1", () -> new ECShieldItem(ItemTags.PLANKS, (new Item.Properties()).maxDamage(436).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_30 = ITEMS.register("shield_i2_d1", () -> new ECShieldItem(-0.25f, ItemTags.PLANKS, (new Item.Properties()).maxDamage(936).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_31 = ITEMS.register("shield_i2_d2", () -> new ECShieldItem(-0.5f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1336).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_32 = ITEMS.register("shield_i2_d3_n", () -> new ECShieldItem(Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).maxDamage(2836).group(EC_GROUP)/**/.isImmuneToFire()));
    public static final RegistryObject<Item> SHIELD_33 = ITEMS.register("shield_i2_d3", () -> new ECShieldItem(-0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1636).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_34 = ITEMS.register("shield_i2_g1_d1", () -> new ECShieldItem(0.75f, Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(861).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_35 = ITEMS.register("shield_i2_g1_d2", () -> new ECShieldItem(0.5f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1161).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_36 = ITEMS.register("shield_i2_g1_d3_n", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).maxDamage(2749).group(EC_GROUP)/**/.isImmuneToFire()));
    public static final RegistryObject<Item> SHIELD_37 = ITEMS.register("shield_i2_g1_d3", () -> new ECShieldItem(-0.25f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1549).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_38 = ITEMS.register("shield_i2_g1", () -> new ECShieldItem(1f, Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(561).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_39 = ITEMS.register("shield_i2", () -> new ECShieldItem(ItemTags.PLANKS, (new Item.Properties()).maxDamage(536).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_40 = ITEMS.register("shield_i3_d1", () -> new ECShieldItem(-0.25f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1036).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_41 = ITEMS.register("shield_i3_d2", () -> new ECShieldItem(-0.5f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1336).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_42 = ITEMS.register("shield_i3_d3_n", () -> new ECShieldItem(Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).maxDamage(2836).group(EC_GROUP)/**/.isImmuneToFire()));
    public static final RegistryObject<Item> SHIELD_43 = ITEMS.register("shield_i3_d3", () -> new ECShieldItem(-0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).maxDamage(1636).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_44 = ITEMS.register("shield_i3", () -> new ECShieldItem(ItemTags.PLANKS, (new Item.Properties()).maxDamage(636).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_45 = ITEMS.register("shield_i4_d1", () -> new ECShieldItem(-0.25f, Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(1036).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_46 = ITEMS.register("shield_i4_d2", () -> new ECShieldItem(-0.5f, Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(1336).group(EC_GROUP)/**/));
    public static final RegistryObject<Item> SHIELD_47 = ITEMS.register("shield_i4_d3_n", () -> new ECShieldItem(Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(2836).group(EC_GROUP)/**/.isImmuneToFire()));
    public static final RegistryObject<Item> SHIELD_48 = ITEMS.register("shield_i4_d3", () -> new ECShieldItem(-0.75f, Tags.Items.INGOTS_IRON, (new Item.Properties()).maxDamage(1636).group(EC_GROUP)/**/));
}

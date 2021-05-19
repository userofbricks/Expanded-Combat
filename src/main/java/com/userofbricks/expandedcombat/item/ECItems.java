//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mapping-1.16.5-mapping"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat.item;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.item.ItemGroup;

public class ECItems
{
    public static final ITag<Item> steel_tag = ItemTags.bind(new ResourceLocation("forge", "ingots/steel").toString());
    public static final ITag<Item> bronze_tag = ItemTags.bind(new ResourceLocation("forge", "ingots/bronze").toString());
    public static final ITag<Item> silver_tag = ItemTags.bind(new ResourceLocation("forge", "ingots/silver").toString());
    public static final ITag<Item> lead_tag = ItemTags.bind(new ResourceLocation("forge", "ingots/lead").toString());
    private static final ItemGroup EC_GROUP = ExpandedCombat.EC_GROUP;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExpandedCombat.MODID);

    public static final RegistryObject<Item> LEATHER_STICK = ITEMS.register("leather_stick", () -> new Item(new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> GOLD_STICK = ITEMS.register("gold_stick", () -> new Item(new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> IRON_STICK = ITEMS.register("iron_stick", () -> new Item(new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> QUIVER = ITEMS.register("quiver", () -> new QuiverItem(new Item.Properties().tab(EC_GROUP).stacksTo(1)));


    public static final RegistryObject<Item> IRON_BOW_HALF = ITEMS.register("iron_bow_half", () -> new ECBowItem(2f, (new Item.Properties()).durability(414).tab(EC_GROUP)));
    public static final RegistryObject<Item> IRON_BOW = ITEMS.register("iron_bow", () -> new ECBowItem(3.5f, 1, (new Item.Properties()).durability(480).tab(EC_GROUP)));
    public static final RegistryObject<Item> GOLD_BOW_HALF = ITEMS.register("gold_bow_half", () -> new ECBowItem(1f, 2f, (new Item.Properties()).durability(390).tab(EC_GROUP)));
    public static final RegistryObject<Item> GOLD_BOW = ITEMS.register("gold_bow", () -> new ECBowItem(2f, 3f, 1, (new Item.Properties()).durability(395).tab(EC_GROUP)));
    public static final RegistryObject<Item> DIAMOND_BOW_HALF = ITEMS.register("diamond_bow_half", () -> new ECBowItem(2.5f, 1, (new Item.Properties()).durability(576).tab(EC_GROUP)));
    public static final RegistryObject<Item> DIAMOND_BOW = ITEMS.register("diamond_bow", () -> new ECBowItem(4f, 2, (new Item.Properties()).durability(672).tab(EC_GROUP)));
    public static final RegistryObject<Item> NETHERITE_BOW = ITEMS.register("netherite_bow", () -> new ECBowItem(4.5f, 3, (new Item.Properties()).durability(768).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> IRON_CROSSBOW = ITEMS.register("crossbow_iron", () -> new ECCrossBowItem(3.5f, 1, (new Item.Properties()).durability(480)));
    public static final RegistryObject<Item> GOLD_CROSSBOW = ITEMS.register("crossbow_gold", () -> new ECCrossBowItem(2f,3f,1, (new Item.Properties()).durability(395)));
    public static final RegistryObject<Item> DIAMOND_CROSSBOW = ITEMS.register("crossbow_diamond", () -> new ECCrossBowItem(4f,2, (new Item.Properties()).durability(672)));
    public static final RegistryObject<Item> NETHERITE_CROSSBOW = ITEMS.register("crossbow_netherite", () -> new ECCrossBowItem(4.5f,3, (new Item.Properties()).durability(768)));

    public static final RegistryObject<Item> IRON_ARROW = ITEMS.register("iron_arrow", () -> new ECArrowItem(3, ArrowType.IRON, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> IRON_TIPPED_ARROW = ITEMS.register("iron_tipped_arrow", () -> new ECTippedArrowItem(3, ArrowType.IRON, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new ECArrowItem(4, ArrowType.DIAMOND, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> DIAMOND_TIPPED_ARROW = ITEMS.register("diamond_tipped_arrow", () -> new ECTippedArrowItem(4, ArrowType.DIAMOND, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new ECArrowItem(5, ArrowType.NETHERITE, new Item.Properties().tab(EC_GROUP).fireResistant()));
    public static final RegistryObject<Item> NETHERITE_TIPPED_ARROW = ITEMS.register("netherite_tipped_arrow", () -> new ECTippedArrowItem(5, ArrowType.NETHERITE, new Item.Properties().tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> NETHERITE_GAUNTLET = ITEMS.register("netherite_gauntlet", () -> new GauntletItem(GauntletMaterials.netherite, new Item.Properties().tab(EC_GROUP).fireResistant()));
    public static final RegistryObject<Item> DIAMOND_GAUNTLET = ITEMS.register("diamond_gauntlet", () -> new GauntletItem(GauntletMaterials.diamond, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> GOLD_GAUNTLET = ITEMS.register("gold_gauntlet", () -> new GauntletItem(GauntletMaterials.gold, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> IRON_GAUNTLET = ITEMS.register("iron_gauntlet", () -> new GauntletItem(GauntletMaterials.iron, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> LEATHER_GAUNTLET = ITEMS.register("leather_gauntlet", () -> new GauntletItem(GauntletMaterials.leather, new Item.Properties().tab(EC_GROUP)));

    public static final RegistryObject<Item> STEEL_GAUNTLET = ITEMS.register("steel_gauntlet", () -> new GauntletItem(GauntletMaterials.steel, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> BRONZE_GAUNTLET = ITEMS.register("bronze_gauntlet", () -> new GauntletItem(GauntletMaterials.bronze, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> SILVER_GAUNTLET = ITEMS.register("silver_gauntlet", () -> new GauntletItem(GauntletMaterials.silver, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> LEAD_GAUNTLET = ITEMS.register("lead_gauntlet", () -> new GauntletItem(GauntletMaterials.lead, new Item.Properties().tab(EC_GROUP)));
    //Twilight Forest
    public static final RegistryObject<Item> NAGA_GAUNTLET = ITEMS.register("naga_gauntlet", () -> new GauntletItem(GauntletMaterials.naga, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> IRONWOOD_GAUNTLET = ITEMS.register("ironwood_gauntlet", () -> new GauntletItem(GauntletMaterials.ironwood, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> FIERY_GAUNTLET = ITEMS.register("fiery_gauntlet", () -> new GauntletItem(GauntletMaterials.fiery, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> STEELEAF_GAUNTLET = ITEMS.register("steeleaf_gauntlet", () -> new GauntletItem(GauntletMaterials.steeleaf, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> KNIGHTLY_GAUNTLET = ITEMS.register("knightly_gauntlet", () -> new GauntletItem(GauntletMaterials.knightly, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> YETI_GAUNTLET = ITEMS.register("yeti_gauntlet", () -> new GauntletItem(GauntletMaterials.yeti, new Item.Properties().tab(EC_GROUP)));
    public static final RegistryObject<Item> ARTIC_GAUNTLET = ITEMS.register("artic_gauntlet", () -> new GauntletItem(GauntletMaterials.artic, new Item.Properties().tab(EC_GROUP)));



    public static final RegistryObject<Item> IRON_SHIELD = ITEMS.register("shield_i4", () -> new ECShieldItem(Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(736).tab(EC_GROUP)/*.setISTER(() -> ShieldTileEntityRenderer::new)*/));
    public static final RegistryObject<Item> GOLD_SHIELD = ITEMS.register("shield_g2", () -> new ECShieldItem(2f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).durability(386).tab(EC_GROUP)/*.setISTER(() -> ShieldTileEntityRenderer::new)*/));
    public static final RegistryObject<Item> DIAMOND_SHIELD = ITEMS.register("shield_d4", () -> new ECShieldItem(-1f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1936).tab(EC_GROUP)/*.setISTER(() -> ShieldTileEntityRenderer::new)*/));
    public static final RegistryObject<Item> NETHERITE_SHIELD = ITEMS.register("shield_n", () -> new ECShieldItem(Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).durability(3336).tab(EC_GROUP)/*.setISTER(() -> ShieldTileEntityRenderer::new)*/.fireResistant()));
    public static final RegistryObject<Item> SHIELD_1 = ITEMS.register("shield_g1_d1_i1", () -> new ECShieldItem(0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(861).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_2 = ITEMS.register("shield_g1_d1", () -> new ECShieldItem(0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(761).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_3 = ITEMS.register("shield_g1_d2", () -> new ECShieldItem(0.5f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1161).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_4 = ITEMS.register("shield_g1_d3_n", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).durability(2749).tab(EC_GROUP).fireResistant()));
    public static final RegistryObject<Item> SHIELD_5 = ITEMS.register("shield_g1_d3", () -> new ECShieldItem(-0.25f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1549).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_6 = ITEMS.register("shield_g1_i1_d1", () -> new ECShieldItem(0.75f, Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(861).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_7 = ITEMS.register("shield_g1_i1", () -> new ECShieldItem(1f, Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(461).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_8 = ITEMS.register("shield_g1_i2_d1", () -> new ECShieldItem(0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(861).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_9 = ITEMS.register("shield_g1_i2", () -> new ECShieldItem(1f, Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(561).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_10 = ITEMS.register("shield_g1", () -> new ECShieldItem(1f, ItemTags.PLANKS, (new Item.Properties()).durability(361).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_11 = ITEMS.register("shield_g2_d1", () -> new ECShieldItem(1.25f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).durability(774).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_12 = ITEMS.register("shield_g2_d2", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).durability(1161).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_13 = ITEMS.register("shield_g2_d3_n", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).durability(2749).tab(EC_GROUP).fireResistant()));
    public static final RegistryObject<Item> SHIELD_14 = ITEMS.register("shield_g2_d3", () -> new ECShieldItem(-0.25f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).durability(1549).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_15 = ITEMS.register("shield_i1_d1_g1", () -> new ECShieldItem(0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(861).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_16 = ITEMS.register("shield_i1_d1_i1_d1", () -> new ECShieldItem(-0.5f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1336).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_17 = ITEMS.register("shield_i1_d1_i1", () -> new ECShieldItem(-0.25f, ItemTags.PLANKS, (new Item.Properties()).durability(936).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_18 = ITEMS.register("shield_i1_d1_i2", () -> new ECShieldItem(-0.25f, Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(1036).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_19 = ITEMS.register("shield_i1_d1", () -> new ECShieldItem(-0.25f, ItemTags.PLANKS, (new Item.Properties()).durability(836).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_20 = ITEMS.register("shield_i1_d2", () -> new ECShieldItem(-0.5f, ItemTags.PLANKS, (new Item.Properties()).durability(1236).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_21 = ITEMS.register("shield_i1_d3_n", () -> new ECShieldItem(Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).durability(2836).tab(EC_GROUP).fireResistant()));
    public static final RegistryObject<Item> SHIELD_22 = ITEMS.register("shield_i1_d3", () -> new ECShieldItem(-0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1636).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_23 = ITEMS.register("shield_i1_g1_d1", () -> new ECShieldItem(0.75f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).durability(861).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_24 = ITEMS.register("shield_i1_g1_d2", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).durability(1161).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_25 = ITEMS.register("shield_i1_g1_d3_n", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).durability(336).tab(EC_GROUP).fireResistant()));
    public static final RegistryObject<Item> SHIELD_26 = ITEMS.register("shield_i1_g1_d3", () -> new ECShieldItem(-0.25f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1549).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_27 = ITEMS.register("shield_i1_g1_i1", () -> new ECShieldItem(1f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).durability(561).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_28 = ITEMS.register("shield_i1_g1", () -> new ECShieldItem(1f, Tags.Items.INGOTS_GOLD, (new Item.Properties()).durability(461).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_29 = ITEMS.register("shield_i1", () -> new ECShieldItem(ItemTags.PLANKS, (new Item.Properties()).durability(436).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_30 = ITEMS.register("shield_i2_d1", () -> new ECShieldItem(-0.25f, ItemTags.PLANKS, (new Item.Properties()).durability(936).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_31 = ITEMS.register("shield_i2_d2", () -> new ECShieldItem(-0.5f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1336).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_32 = ITEMS.register("shield_i2_d3_n", () -> new ECShieldItem(Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).durability(2836).tab(EC_GROUP).fireResistant()));
    public static final RegistryObject<Item> SHIELD_33 = ITEMS.register("shield_i2_d3", () -> new ECShieldItem(-0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1636).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_34 = ITEMS.register("shield_i2_g1_d1", () -> new ECShieldItem(0.75f, Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(861).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_35 = ITEMS.register("shield_i2_g1_d2", () -> new ECShieldItem(0.5f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1161).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_36 = ITEMS.register("shield_i2_g1_d3_n", () -> new ECShieldItem(0.5f, Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).durability(2749).tab(EC_GROUP).fireResistant()));
    public static final RegistryObject<Item> SHIELD_37 = ITEMS.register("shield_i2_g1_d3", () -> new ECShieldItem(-0.25f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1549).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_38 = ITEMS.register("shield_i2_g1", () -> new ECShieldItem(1f, Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(561).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_39 = ITEMS.register("shield_i2", () -> new ECShieldItem(ItemTags.PLANKS, (new Item.Properties()).durability(536).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_40 = ITEMS.register("shield_i3_d1", () -> new ECShieldItem(-0.25f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1036).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_41 = ITEMS.register("shield_i3_d2", () -> new ECShieldItem(-0.5f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1336).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_42 = ITEMS.register("shield_i3_d3_n", () -> new ECShieldItem(Tags.Items.INGOTS_NETHERITE, (new Item.Properties()).durability(2836).tab(EC_GROUP).fireResistant()));
    public static final RegistryObject<Item> SHIELD_43 = ITEMS.register("shield_i3_d3", () -> new ECShieldItem(-0.75f, Tags.Items.GEMS_DIAMOND, (new Item.Properties()).durability(1636).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_44 = ITEMS.register("shield_i3", () -> new ECShieldItem(ItemTags.PLANKS, (new Item.Properties()).durability(636).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_45 = ITEMS.register("shield_i4_d1", () -> new ECShieldItem(-0.25f, Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(1036).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_46 = ITEMS.register("shield_i4_d2", () -> new ECShieldItem(-0.5f, Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(1336).tab(EC_GROUP)));
    public static final RegistryObject<Item> SHIELD_47 = ITEMS.register("shield_i4_d3_n", () -> new ECShieldItem(Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(2836).tab(EC_GROUP).fireResistant()));
    public static final RegistryObject<Item> SHIELD_48 = ITEMS.register("shield_i4_d3", () -> new ECShieldItem(-0.75f, Tags.Items.INGOTS_IRON, (new Item.Properties()).durability(1636).tab(EC_GROUP)));


    public static final RegistryObject<Item> BATTLESTAFF_WOOD = ITEMS.register("battlestaff_wood", () -> new ECWeaponItem.Dyeable(WeaponTier.WOOD, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BATTLESTAFF_STONE = ITEMS.register("battlestaff_stone", () -> new ECWeaponItem.Dyeable(WeaponTier.STONE, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BATTLESTAFF_IRON = ITEMS.register("battlestaff_iron", () -> new ECWeaponItem.Dyeable(WeaponTier.IRON, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BATTLESTAFF_GOLD = ITEMS.register("battlestaff_gold", () -> new ECWeaponItem.Dyeable(WeaponTier.GOLD, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BATTLESTAFF_DIAMOND = ITEMS.register("battlestaff_diamond", () -> new ECWeaponItem.Dyeable(WeaponTier.DIAMOND, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BATTLESTAFF_NETHERITE = ITEMS.register("battlestaff_netherite", () -> new ECWeaponItem.Dyeable(WeaponTier.NETHERITE, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> BROADSWORD_WOOD = ITEMS.register("broadsword_wood", () -> new ECWeaponItem.Dyeable(WeaponTier.WOOD, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BROADSWORD_STONE = ITEMS.register("broadsword_stone", () -> new ECWeaponItem.Dyeable(WeaponTier.STONE, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BROADSWORD_IRON = ITEMS.register("broadsword_iron", () -> new ECWeaponItem.Dyeable(WeaponTier.IRON, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BROADSWORD_GOLD = ITEMS.register("broadsword_gold", () -> new ECWeaponItem.Dyeable(WeaponTier.GOLD, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BROADSWORD_DIAMOND = ITEMS.register("broadsword_diamond", () -> new ECWeaponItem.Dyeable(WeaponTier.DIAMOND, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BROADSWORD_NETHERITE = ITEMS.register("broadsword_netherite", () -> new ECWeaponItem.Dyeable(WeaponTier.NETHERITE, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> CLAYMORE_WOOD = ITEMS.register("claymore_wood", () -> new ECWeaponItem.Dyeable(WeaponTier.WOOD, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CLAYMORE_STONE = ITEMS.register("claymore_stone", () -> new ECWeaponItem.Dyeable(WeaponTier.STONE, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CLAYMORE_IRON = ITEMS.register("claymore_iron", () -> new ECWeaponItem.Dyeable(WeaponTier.IRON, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CLAYMORE_GOLD = ITEMS.register("claymore_gold", () -> new ECWeaponItem.Dyeable(WeaponTier.GOLD, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CLAYMORE_DIAMOND = ITEMS.register("claymore_diamond", () -> new ECWeaponItem.Dyeable(WeaponTier.DIAMOND, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CLAYMORE_NETHERITE = ITEMS.register("claymore_netherite", () -> new ECWeaponItem.Dyeable(WeaponTier.NETHERITE, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> CUTLASS_WOOD = ITEMS.register("cutlass_wood", () -> new ECWeaponItem(WeaponTier.WOOD, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CUTLASS_STONE = ITEMS.register("cutlass_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CUTLASS_IRON = ITEMS.register("cutlass_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CUTLASS_GOLD = ITEMS.register("cutlass_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CUTLASS_DIAMOND = ITEMS.register("cutlass_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CUTLASS_NETHERITE = ITEMS.register("cutlass_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> DAGGER_WOOD = ITEMS.register("dagger_wood", () -> new ECWeaponItem(WeaponTier.WOOD, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DAGGER_STONE = ITEMS.register("dagger_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DAGGER_IRON = ITEMS.register("dagger_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DAGGER_GOLD = ITEMS.register("dagger_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DAGGER_DIAMOND = ITEMS.register("dagger_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DAGGER_NETHERITE = ITEMS.register("dagger_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> DANCERS_SWORD_WOOD = ITEMS.register("dancers_sword_wood", () -> new ECWeaponItem.Dyeable(WeaponTier.WOOD, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DANCERS_SWORD_STONE = ITEMS.register("dancers_sword_stone", () -> new ECWeaponItem.Dyeable(WeaponTier.STONE, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DANCERS_SWORD_IRON = ITEMS.register("dancers_sword_iron", () -> new ECWeaponItem.Dyeable(WeaponTier.IRON, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DANCERS_SWORD_GOLD = ITEMS.register("dancers_sword_gold", () -> new ECWeaponItem.Dyeable(WeaponTier.GOLD, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DANCERS_SWORD_DIAMOND = ITEMS.register("dancers_sword_diamond", () -> new ECWeaponItem.Dyeable(WeaponTier.DIAMOND, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DANCERS_SWORD_NETHERITE = ITEMS.register("dancers_sword_netherite", () -> new ECWeaponItem.Dyeable(WeaponTier.NETHERITE, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> GLAIVE_WOOD = ITEMS.register("glaive_wood", () -> new ECWeaponItem.Dyeable(WeaponTier.WOOD, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GLAIVE_STONE = ITEMS.register("glaive_stone", () -> new ECWeaponItem.Dyeable(WeaponTier.STONE, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GLAIVE_IRON = ITEMS.register("glaive_iron", () -> new ECWeaponItem.Dyeable(WeaponTier.IRON, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GLAIVE_GOLD = ITEMS.register("glaive_gold", () -> new ECWeaponItem.Dyeable(WeaponTier.GOLD, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GLAIVE_DIAMOND = ITEMS.register("glaive_diamond", () -> new ECWeaponItem.Dyeable(WeaponTier.DIAMOND, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GLAIVE_NETHERITE = ITEMS.register("glaive_netherite", () -> new ECWeaponItem.Dyeable(WeaponTier.NETHERITE, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> KATANA_WOOD = ITEMS.register("katana_wood", () -> new ECWeaponItem(WeaponTier.WOOD, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> KATANA_STONE = ITEMS.register("katana_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> KATANA_IRON = ITEMS.register("katana_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> KATANA_GOLD = ITEMS.register("katana_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> KATANA_DIAMOND = ITEMS.register("katana_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> KATANA_NETHERITE = ITEMS.register("katana_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> SCYTHE_WOOD = ITEMS.register("scythe_wood", () -> new ECWeaponItem.HasPotion(WeaponTier.WOOD, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SCYTHE_STONE = ITEMS.register("scythe_stone", () -> new ECWeaponItem.HasPotion(WeaponTier.STONE, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SCYTHE_IRON = ITEMS.register("scythe_iron", () -> new ECWeaponItem.HasPotion(WeaponTier.IRON, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SCYTHE_GOLD = ITEMS.register("scythe_gold", () -> new ECWeaponItem.HasPotion(WeaponTier.GOLD, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SCYTHE_DIAMOND = ITEMS.register("scythe_diamond", () -> new ECWeaponItem.HasPotion(WeaponTier.DIAMOND, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SCYTHE_NETHERITE = ITEMS.register("scythe_netherite", () -> new ECWeaponItem.HasPotion(WeaponTier.NETHERITE, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> SICKLE_WOOD = ITEMS.register("sickle_wood", () -> new ECWeaponItem(WeaponTier.WOOD, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SICKLE_STONE = ITEMS.register("sickle_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SICKLE_IRON = ITEMS.register("sickle_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SICKLE_GOLD = ITEMS.register("sickle_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SICKLE_DIAMOND = ITEMS.register("sickle_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SICKLE_NETHERITE = ITEMS.register("sickle_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> SPEAR_WOOD = ITEMS.register("spear_wood", () -> new ECWeaponItem(WeaponTier.WOOD, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SPEAR_STONE = ITEMS.register("spear_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SPEAR_IRON = ITEMS.register("spear_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SPEAR_GOLD = ITEMS.register("spear_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SPEAR_DIAMOND = ITEMS.register("spear_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SPEAR_NETHERITE = ITEMS.register("spear_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP).fireResistant()));



    public static final RegistryObject<Item> FLAIL_OAK_WOOD = ITEMS.register("flail_oak_wood", () -> new ECWeaponItem(WeaponTier.OAK_WOOD, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_OAK_PLANKS = ITEMS.register("flail_oak_planks", () -> new ECWeaponItem(WeaponTier.OAK_PLANK, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_STONE = ITEMS.register("flail_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_IRON = ITEMS.register("flail_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_GOLD = ITEMS.register("flail_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_EMERALD = ITEMS.register("flail_emerald", () -> new ECWeaponItem(WeaponTier.EMERALD, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_DIAMOND = ITEMS.register("flail_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_NETHERITE = ITEMS.register("flail_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> GREAT_HAMMER_OAK_WOOD = ITEMS.register("great_hammer_oak_wood", () -> new ECWeaponItem(WeaponTier.OAK_WOOD, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_OAK_PLANKS = ITEMS.register("great_hammer_oak_planks", () -> new ECWeaponItem(WeaponTier.OAK_PLANK, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_STONE = ITEMS.register("great_hammer_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_IRON = ITEMS.register("great_hammer_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_GOLD = ITEMS.register("great_hammer_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_SWORD_EMERALD = ITEMS.register("great_hammer_emerald", () -> new ECWeaponItem(WeaponTier.EMERALD, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_DIAMOND = ITEMS.register("great_hammer_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_NETHERITE = ITEMS.register("great_hammer_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistryObject<Item> MACE_OAK_WOOD = ITEMS.register("mace_oak_wood", () -> new ECWeaponItem(WeaponTier.OAK_WOOD, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_OAK_PLANKS = ITEMS.register("mace_oak_planks", () -> new ECWeaponItem(WeaponTier.OAK_PLANK, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_STONE = ITEMS.register("mace_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_IRON = ITEMS.register("mace_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_GOLD = ITEMS.register("mace_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_SWORD_EMERALD = ITEMS.register("mace_emerald", () -> new ECWeaponItem(WeaponTier.EMERALD, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_DIAMOND = ITEMS.register("mace_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_NETHERITE = ITEMS.register("mace_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP).fireResistant()));


    public static final RegistryObject<Item> BATTLESTAFF_STEEL = ITEMS.register("battlestaff_steel", () -> new ECWeaponItem.Dyeable(WeaponTier.STEEL, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BROADSWORD_STEEL = ITEMS.register("broadsword_steel", () -> new ECWeaponItem.Dyeable(WeaponTier.STEEL, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CLAYMORE_STEEL = ITEMS.register("claymore_steel", () -> new ECWeaponItem.Dyeable(WeaponTier.STEEL, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CUTLASS_STEEL = ITEMS.register("cutlass_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DAGGER_STEEL = ITEMS.register("dagger_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DANCERS_SWORD_STEEL = ITEMS.register("dancers_sword_steel", () -> new ECWeaponItem.Dyeable(WeaponTier.STEEL, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GLAIVE_STEEL = ITEMS.register("glaive_steel", () -> new ECWeaponItem.Dyeable(WeaponTier.STEEL, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> KATANA_STEEL = ITEMS.register("katana_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SCYTHE_STEEL = ITEMS.register("scythe_steel", () -> new ECWeaponItem.HasPotion(WeaponTier.STEEL, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SICKLE_STEEL = ITEMS.register("sickle_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SPEAR_STEEL = ITEMS.register("spear_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_STEEL = ITEMS.register("flail_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_STEEL = ITEMS.register("great_hammer_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_STEEL = ITEMS.register("mace_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));

    public static final RegistryObject<Item> BATTLESTAFF_BRONZE = ITEMS.register("battlestaff_bronze", () -> new ECWeaponItem.Dyeable(WeaponTier.BRONZE, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BROADSWORD_BRONZE = ITEMS.register("broadsword_bronze", () -> new ECWeaponItem.Dyeable(WeaponTier.BRONZE, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CLAYMORE_BRONZE = ITEMS.register("claymore_bronze", () -> new ECWeaponItem.Dyeable(WeaponTier.BRONZE, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CUTLASS_BRONZE = ITEMS.register("cutlass_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DAGGER_BRONZE = ITEMS.register("dagger_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DANCERS_SWORD_BRONZE = ITEMS.register("dancers_sword_bronze", () -> new ECWeaponItem.Dyeable(WeaponTier.BRONZE, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GLAIVE_BRONZE = ITEMS.register("glaive_bronze", () -> new ECWeaponItem.Dyeable(WeaponTier.BRONZE, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> KATANA_BRONZE = ITEMS.register("katana_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SCYTHE_BRONZE = ITEMS.register("scythe_bronze", () -> new ECWeaponItem.HasPotion(WeaponTier.BRONZE, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SICKLE_BRONZE = ITEMS.register("sickle_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SPEAR_BRONZE = ITEMS.register("spear_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_BRONZE = ITEMS.register("flail_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_BRONZE = ITEMS.register("great_hammer_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_BRONZE = ITEMS.register("mace_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));

    public static final RegistryObject<Item> BATTLESTAFF_SILVER = ITEMS.register("battlestaff_silver", () -> new ECWeaponItem.Dyeable(WeaponTier.SILVER, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BROADSWORD_SILVER = ITEMS.register("broadsword_silver", () -> new ECWeaponItem.Dyeable(WeaponTier.SILVER, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CLAYMORE_SILVER = ITEMS.register("claymore_silver", () -> new ECWeaponItem.Dyeable(WeaponTier.SILVER, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CUTLASS_SILVER = ITEMS.register("cutlass_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DAGGER_SILVER = ITEMS.register("dagger_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DANCERS_SWORD_SILVER = ITEMS.register("dancers_sword_silver", () -> new ECWeaponItem.Dyeable(WeaponTier.SILVER, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GLAIVE_SILVER = ITEMS.register("glaive_silver", () -> new ECWeaponItem.Dyeable(WeaponTier.SILVER, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> KATANA_SILVER = ITEMS.register("katana_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SCYTHE_SILVER = ITEMS.register("scythe_silver", () -> new ECWeaponItem.HasPotion(WeaponTier.SILVER, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SICKLE_SILVER = ITEMS.register("sickle_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SPEAR_SILVER = ITEMS.register("spear_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_SILVER = ITEMS.register("flail_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_SILVER = ITEMS.register("great_hammer_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_SILVER = ITEMS.register("mace_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));

    public static final RegistryObject<Item> BATTLESTAFF_LEAD = ITEMS.register("battlestaff_lead", () -> new ECWeaponItem.Dyeable(WeaponTier.LEAD, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> BROADSWORD_LEAD = ITEMS.register("broadsword_lead", () -> new ECWeaponItem.Dyeable(WeaponTier.LEAD, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CLAYMORE_LEAD = ITEMS.register("claymore_lead", () -> new ECWeaponItem.Dyeable(WeaponTier.LEAD, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> CUTLASS_LEAD = ITEMS.register("cutlass_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DAGGER_LEAD = ITEMS.register("dagger_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> DANCERS_SWORD_LEAD = ITEMS.register("dancers_sword_lead", () -> new ECWeaponItem.Dyeable(WeaponTier.LEAD, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GLAIVE_LEAD = ITEMS.register("glaive_lead", () -> new ECWeaponItem.Dyeable(WeaponTier.LEAD, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> KATANA_LEAD = ITEMS.register("katana_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SCYTHE_LEAD = ITEMS.register("scythe_lead", () -> new ECWeaponItem.HasPotion(WeaponTier.LEAD, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SICKLE_LEAD = ITEMS.register("sickle_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> SPEAR_LEAD = ITEMS.register("spear_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> FLAIL_LEAD = ITEMS.register("flail_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> GREAT_HAMMER_LEAD = ITEMS.register("great_hammer_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistryObject<Item> MACE_LEAD = ITEMS.register("mace_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));


    public static void setAtributeModifiers(){
        for ( RegistryObject<Item> ro : ECItems.ITEMS.getEntries()) {
            Item item = ro.get();
            if (item instanceof ECWeaponItem) {
                ECWeaponItem.setAtributeModifierMultimap((ECWeaponItem) item);
            }
        }
    }
}

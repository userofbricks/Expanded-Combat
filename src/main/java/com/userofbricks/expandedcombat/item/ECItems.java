package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
    public static final RegistryObject<Item> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new ECArrowItem(5, ArrowType.NETHERITE, new Item.Properties().group(EC_GROUP).func_234689_a_()));
    public static final RegistryObject<Item> NETHERITE_TIPPED_ARROW = ITEMS.register("netherite_tipped_arrow", () -> new ECTippedArrowItem(5, ArrowType.NETHERITE, new Item.Properties().group(EC_GROUP).func_234689_a_()));

    public static final RegistryObject<Item> NETHERITE_GAUNTLET = ITEMS.register("netherite_gauntlet", () -> new GauntletItem(GauntletMaterials.netherite, new Item.Properties().group(EC_GROUP).func_234689_a_()));
    public static final RegistryObject<Item> DIAMOND_GAUNTLET = ITEMS.register("diamond_gauntlet", () -> new GauntletItem(GauntletMaterials.diamond, new Item.Properties().group(EC_GROUP)));
    public static final RegistryObject<Item> GOLD_GAUNTLET = ITEMS.register("gold_gauntlet", () -> new GauntletItem(GauntletMaterials.gold, new Item.Properties().group(EC_GROUP)));
    public static final RegistryObject<Item> IRON_GAUNTLET = ITEMS.register("iron_gauntlet", () -> new GauntletItem(GauntletMaterials.iron, new Item.Properties().group(EC_GROUP)));
    public static final RegistryObject<Item> LEATHER_GAUNTLET = ITEMS.register("leather_gauntlet", () -> new GauntletItem(GauntletMaterials.leather, new Item.Properties().group(EC_GROUP)));


    public static final RegistryObject<Item> IRON_BOW = ITEMS.register("iron_bow", () -> new ECBowItem(4f, (new Item.Properties()).maxDamage(480).group(EC_GROUP)));
    public static final RegistryObject<Item> IRON_BOW_HALF = ITEMS.register("iron_bow_half", () -> new ECBowItem(1f, (new Item.Properties()).maxDamage(414).group(EC_GROUP)));
    public static final RegistryObject<Item> DIAMOND_BOW = ITEMS.register("diamond_bow", () -> new ECBowItem(5f, (new Item.Properties()).maxDamage(672).group(EC_GROUP)));
    public static final RegistryObject<Item> DIAMOND_BOW_HALF = ITEMS.register("diamond_bow_half", () -> new ECBowItem(2f, (new Item.Properties()).maxDamage(576).group(EC_GROUP)));
    public static final RegistryObject<Item> NETHERITE_BOW = ITEMS.register("netherite_bow", () -> new ECBowItem(6f, (new Item.Properties()).maxDamage(768).group(EC_GROUP).func_234689_a_()));
}

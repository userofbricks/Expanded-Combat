package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.util.ModIDs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public final class ECTags {

    public static final TagKey<Item> GAUNTLETS = bindCurios("hands");
    public static final TagKey<Item> NON_EC_MENDABLE_GOLD = bind("non_ec_mendable_gold");
    public static final TagKey<Item> QUIVERS = bindCurios("quiver_ec");
    public static final TagKey<Item> POTION_WEAPONS = bind("potion_weapons");

    //Enchantment Tags
    public static final TagKey<Item> GAUNTLET_ENCHANTABLE = bind("enchantable/gauntlet");
    public static final TagKey<Item> BLOCKING_ENCHANTABLE = bind("enchantable/blocking");
    public static final TagKey<Item> AGILITY_ENCHANTABLE = bind("enchantable/agility");
    public static final TagKey<Item> GROUND_SLAM = bind("enchantable/ground_slam");

    public static final TagKey<WeaponType> BLUNT_WEAPON = TagKey.create(Registries.WEAPON_TYPE_REGISTRY_KEY, modLoc("blunt_weapon_types"));

    private static TagKey<Item> bind(String name) {
        return ItemTags.create(new ResourceLocation(ExpandedCombat.MODID, name));
    }
    public static TagKey<Item> bindCurios(String name) {
        return ItemTags.create(new ResourceLocation(ModIDs.Curios, name));
    }
}

package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.api.TriFunction;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.init.ECItemTags;
import com.userofbricks.expanded_combat.api.material.WeaponMaterial;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;

public class WeaponItemBuilder {
    public final WeaponMaterial weapon;
    public final ItemBuilder<? extends Item, Registrate> itemBuilder;
    public final MaterialBuilder materialBuilder;

    public WeaponItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, WeaponMaterial weapon, Material material, Material craftedFrom, TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> constructor, boolean shaped) {
        String locationName = material.getLocationName().getPath() + "_" + weapon.getLocationName();
        ItemBuilder<? extends Item, Registrate> itemBuilder = registrate.item(locationName, (p) -> constructor.apply(material, weapon, p));

        if (weapon.potionDippable()) itemBuilder.tag(ECItemTags.POTION_WEAPONS);
        this.weapon = weapon;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
    }

    public MaterialBuilder build() {
        materialBuilder.weapon(weapon, (m) -> itemBuilder.register());
        return materialBuilder;
    }

}

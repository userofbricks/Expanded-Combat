package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.userofbricks.expanded_combat.api.material.Material;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;

public class CrossBowItemBuilder {
    public final MaterialBuilder materialBuilder;
    public final Material material;
    public final ItemBuilder<? extends CrossbowItem, Registrate> itemBuilder;

    public CrossBowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends CrossbowItem> constructor) {
        ItemBuilder<? extends CrossbowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_crossbow", (p) -> constructor.apply(p, material));

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
    }

    public MaterialBuilder build() {

        materialBuilder.crossBow(m -> itemBuilder.register());
        return materialBuilder;
    }
}

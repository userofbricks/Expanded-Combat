package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.init.ECItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;

public class ArrowItemBuilder {
    public final MaterialBuilder materialBuilder;
    public final Material material, craftedFrom;
    public final ItemBuilder<? extends ArrowItem, Registrate> itemBuilder, tippedBuilder;

    public ArrowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> constructor, NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> tippedConstructor) {
        ItemBuilder<? extends ArrowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_arrow", (p) -> constructor.apply(p, material));
        if (tippedConstructor != null) {
            ItemBuilder<? extends ArrowItem, Registrate> tippedBuilder = registrate.item("tipped_" + material.getLocationName().getPath() + "_arrow", (p) -> tippedConstructor.apply(p, material));
            tippedBuilder.tag(ECItemTags.ARROWS, ItemTags.ARROWS);
            this.tippedBuilder = tippedBuilder;
        }
        else this.tippedBuilder = null;

        itemBuilder.tag(ECItemTags.ARROWS, ItemTags.ARROWS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
    }

    public MaterialBuilder build() {
        materialBuilder.arrow(m -> itemBuilder.register());
        if (tippedBuilder != null) materialBuilder.tippedArrow(m -> tippedBuilder.register());
        return materialBuilder;
    }
}

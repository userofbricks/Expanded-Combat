package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.userofbricks.expanded_combat.api.TriFunction;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.init.ECItemTags;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public class BowItemBuilder {
    public final MaterialBuilder materialBuilder;
    public final Material material;
    public final ItemBuilder<? extends BowItem, Registrate> itemBuilder;

    public BowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, TriFunction<Item.Properties, Material, Material, ? extends BowItem> constructor) {
        ItemBuilder<? extends BowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_bow", (p) -> constructor.apply(p, material, craftedFrom));

        itemBuilder.tag(ECItemTags.BOWS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
    }

    public MaterialBuilder build() {
        materialBuilder.bow(m -> itemBuilder.register());
        return materialBuilder;
    }
}

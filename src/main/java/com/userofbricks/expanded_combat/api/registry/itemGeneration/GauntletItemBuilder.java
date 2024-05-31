package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.init.ECItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class GauntletItemBuilder {

    public final MaterialBuilder materialBuilder;
    public final Material material;
    public final ItemBuilder<? extends Item, Registrate> itemBuilder;

    public GauntletItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends Item> constructor) {
        ItemBuilder<? extends Item, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_gauntlet", (p) -> constructor.apply(p, material));

        itemBuilder.tag(ECItemTags.GAUNTLETS, ItemTags.TRIMMABLE_ARMOR);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
    }

    public MaterialBuilder build() {
        materialBuilder.gauntlet(m -> itemBuilder.register());
        return materialBuilder;
    }

}

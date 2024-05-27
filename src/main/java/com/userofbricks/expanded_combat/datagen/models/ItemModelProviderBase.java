package com.userofbricks.expanded_combat.datagen.models;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Materials;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public abstract class ItemModelProviderBase extends ItemModelProvider {
    public static final List<TrimModelData> GENERATED_TRIM_MODELS = List.of(
            new TrimModelData("quartz", 0.1F, Map.of()),
            new TrimModelData("iron", 0.2F, Map.of(Materials.IRON, "iron_darker")),
            new TrimModelData("netherite", 0.3F, Map.of(Materials.NETHERITE, "netherite_darker")),
            new TrimModelData("redstone", 0.4F, Map.of()),
            new TrimModelData("copper", 0.5F, Map.of()),
            new TrimModelData("gold", 0.6F, Map.of(Materials.GOLD, "gold_darker")),
            new TrimModelData("emerald", 0.7F, Map.of()),
            new TrimModelData("diamond", 0.8F, Map.of(Materials.DIAMOND, "diamond_darker")),
            new TrimModelData("lapis", 0.9F, Map.of()),
            new TrimModelData("amethyst", 1.0F, Map.of()));

    public ItemModelProviderBase(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public void generateTippedArrowModel(ResourceLocation item, Holder.Reference<Material> material) {
        generated(item, material.key().location().withPrefix("item/arrow/"), new ResourceLocation(MODID, "item/arrow/tipped_head"));
    }

    public void generateArrowModel(ResourceLocation item, Holder.Reference<Material> material) {
        generated(item, material.key().location().withPrefix("item/arrow/"));
    }

    public void generateBowModel(ResourceLocation item, Holder.Reference<Material> material) {
        ResourceLocation materialLocation = material.key().location();
        ItemModelBuilder itemModelBuilder = generated(item, materialLocation.withPath("item/bow/" + materialLocation.getPath()));

        itemModelBuilder.transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-80, 260, -40).translation(-1, -2, 2.5f).scale(0.9f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-80, -280, 40).translation(-1, -2, 2.5f).scale(0.9f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 90, -25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).model(
                        withExistingParent(item + "_pulling_0", new ResourceLocation("item/bow"))
                                .texture("layer0", materialLocation.withPath("item/bow/" + materialLocation.getPath() + "_pulling_0"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.65f).model(
                        withExistingParent(item + "_pulling_1", new ResourceLocation("item/bow"))
                                .texture("layer0", materialLocation.withPath("item/bow/" + materialLocation.getPath() + "_pulling_1"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.9f).model(
                        withExistingParent(item + "_pulling_2", new ResourceLocation("item/bow"))
                                .texture("layer0", materialLocation.withPath("item/bow/" + materialLocation.getPath() + "_pulling_2"))
                ).end();
    }

    public void generateCrossBowModel(ResourceLocation item, Holder.Reference<Material> material) {
        ResourceLocation materialLocation = material.key().location();
        generated(item, materialLocation.withPath("item/crossbow/" + materialLocation.getPath()))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-90, 0, -60).translation(2, 0.1f, -3f).scale(0.9f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-90, 0, 30).translation(2, 0.1f, -3f).scale(0.9f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90, 0, -55).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90, 0, 35).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).model(
                        withExistingParent(item + "_pulling_0", new ResourceLocation("item/crossbow")).texture("layer0", materialLocation.withPath("item/crossbow/" + materialLocation.getPath() + "_pulling_0"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.58f).model(
                        withExistingParent(item + "_pulling_1", new ResourceLocation("item/crossbow")).texture("layer0", materialLocation.withPath("item/crossbow/" + materialLocation.getPath() + "_pulling_1"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 1f).model(
                        withExistingParent(item + "_pulling_2", new ResourceLocation("item/crossbow")).texture("layer0", materialLocation.withPath("item/crossbow/" + materialLocation.getPath() + "_pulling_2"))
                ).end()
                .override().predicate(new ResourceLocation("charged"), 1).model(
                        withExistingParent(item + "_arrow", new ResourceLocation("item/crossbow")).texture("layer0", materialLocation.withPath("item/crossbow/" + materialLocation.getPath() + "_arrow"))
                ).end()
                .override().predicate(new ResourceLocation("charged"), 1).predicate(new ResourceLocation("firework"), 1).model(
                        withExistingParent(item + "_firework", new ResourceLocation("item/crossbow")).texture("layer0", materialLocation.withPath("item/crossbow/" + materialLocation.getPath() + "_firework"))
                ).end();
    }
    public void generateGauntletModel(ResourceLocation item, Holder.Reference<Material> material, boolean dyeable) {
        ResourceLocation materialLocation = material.key().location();
        ResourceLocation main_texture = materialLocation.withPath("item/gauntlet/" + materialLocation.getPath());
        ResourceLocation overlay_texture = materialLocation.withPath("item/gauntlet/" + materialLocation.getPath() + "_overlay");
        ItemModelBuilder mainModel;
        if (!dyeable) mainModel = generated(item, main_texture);
        else mainModel = generated(item, main_texture, overlay_texture);

        for (TrimModelData trimModelData : GENERATED_TRIM_MODELS) {
            ResourceLocation trim_texture = new ResourceLocation(MODID, "trims/items/gauntlet_trim_" + trimModelData.name(material));

            ItemModelBuilder trimModel = getBuilder(item + "_" + trimModelData.name(material) + "_trim").parent(new ModelFile.UncheckedModelFile("item/generated"));
            trimModel.texture("layer0", main_texture);
            if (!dyeable) {
                trimModel.texture("layer1", trim_texture);
            }
            else {
                trimModel.texture("layer1", overlay_texture);
                trimModel.texture("layer2", trim_texture);
            }

            mainModel.override().predicate(ItemModelGenerators.TRIM_TYPE_PREDICATE_ID, trimModelData.itemModelIndex())
                    .model(trimModel);
        }
    }
    public void generateQuiverModel(ResourceLocation item, Holder.Reference<Material> material, boolean dyeable) {
        ResourceLocation materialLocation = material.key().location();
        ResourceLocation main_texture = materialLocation.withPath("item/quiver/" + materialLocation.getPath());
        ResourceLocation overlay_texture = materialLocation.withPath("item/quiver/" + materialLocation.getPath() + "_overlay");
        if (!dyeable) generated(item, main_texture);
        else generated(item, main_texture, overlay_texture);
    }
    
    public ItemModelBuilder generated(ResourceLocation item, ResourceLocation... layers) {
        ItemModelBuilder modelBuilder = getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile("item/generated"));
        for (int layer = 0; layer < layers.length; layer++) {
            ResourceLocation layerResLoc = layers[layer];
            modelBuilder.texture("layer" + layer, layerResLoc);
        }
        return modelBuilder;
    }

    public record TrimModelData(String name, float itemModelIndex, Map<Holder.Reference<Material>, String> overrideMaterials) {
        public String name(@Nullable Holder.Reference<Material> p_268105_) {
            if (p_268105_ != null) {
                return this.overrideMaterials.getOrDefault(p_268105_, this.name);
            }
            return this.name;
        }
    }
}

package com.userofbricks.expanded_combat.datagen.models;

import com.userofbricks.expanded_combat.api.TriFunction;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.item.ArrowBlockWeaponItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class WeaponItemModelBuilder {
    public static final Function<Holder.Reference<WeaponType>, ResourceLocation> DEFAULT_HANDLE_LOC =
            (weaponReference) -> new ResourceLocation(MODID, weaponReference.key().location().getPath() + "_handle");
    public static final Function<Holder.Reference<WeaponType>, ResourceLocation> DEFAULT_DYE_LOC =
            (weaponReference) -> new ResourceLocation(MODID, weaponReference.key().location().getPath() + "_dye");

    public final DeferredItem<?> item;
    public final Holder.Reference<Material> material;
    public final Holder.Reference<WeaponType> weapon;
    public final ItemModelProvider modelProvider;

    private boolean dyeableOrPotionDippable = false;
    private boolean singleTexture = false;
    private boolean hasCustomTransformsOrModel = false;
    private boolean hasLargeModel = false;
    private TriFunction<DeferredItem<?>, Holder.Reference<Material>, Holder.Reference<WeaponType>, ResourceLocation> mainTextureFunction =
            (deferredItem, materialReference, weaponReference) ->
                    new ResourceLocation(deferredItem.getId().getNamespace(), weaponReference.key().location().getPath() + "/" + materialReference.key().location().getPath());
    private BiFunction<DeferredItem<?>, Holder.Reference<WeaponType>, ResourceLocation> handleTextureFunction =
            (deferredItem, weaponReference) -> DEFAULT_HANDLE_LOC.apply(weaponReference);
    private BiFunction<DeferredItem<?>, Holder.Reference<WeaponType>, ResourceLocation> dyeTextureFunction =
            (deferredItem, weaponReference) -> DEFAULT_DYE_LOC.apply(weaponReference);


    public WeaponItemModelBuilder(DeferredItem<?> item, Holder.Reference<Material> material, Holder.Reference<WeaponType> weapon, ItemModelProvider modelProvider) {
        this.item = item;
        this.material = material;
        this.weapon = weapon;
        this.modelProvider = modelProvider;
    }
    public WeaponItemModelBuilder setMainTextureFunction(TriFunction<DeferredItem<?>, Holder.Reference<Material>, Holder.Reference<WeaponType>, ResourceLocation> mainTextureFunction) {
        this.mainTextureFunction = mainTextureFunction;
        return this;
    }

    public WeaponItemModelBuilder setHandleTextureFunction(BiFunction<DeferredItem<?>, Holder.Reference<WeaponType>, ResourceLocation> handleTextureFunction) {
        this.handleTextureFunction = handleTextureFunction;
        return this;
    }

    public WeaponItemModelBuilder setHandleTextureFunction(String handleTexture) {
        this.handleTextureFunction = (deferredItem, weaponReference) ->
                new ResourceLocation(deferredItem.getId().getNamespace(), weaponReference.key().location().getPath() + "/" + handleTexture);
        return this;
    }

    public WeaponItemModelBuilder setDyeTextureFunction(BiFunction<DeferredItem<?>, Holder.Reference<WeaponType>, ResourceLocation> dyeTextureFunction) {
        this.dyeTextureFunction = dyeTextureFunction;
        return this;
    }

    public WeaponItemModelBuilder setDyeTextureFunction(String dyeTexture) {
        this.dyeTextureFunction = (deferredItem, weaponReference) ->
                new ResourceLocation(deferredItem.getId().getNamespace(), weaponReference.key().location().getPath() + "/" + dyeTexture);
        return this;
    }

    public WeaponItemModelBuilder setDyeableOrPotionDippable() {
        this.dyeableOrPotionDippable = true;
        return this;
    }

    public WeaponItemModelBuilder setSingleTexture() {
        this.singleTexture = true;
        return this;
    }

    public WeaponItemModelBuilder setHasCustomTransformsOrModel() {
        this.hasCustomTransformsOrModel = true;
        return this;
    }

    public WeaponItemModelBuilder setHasLargeModel() {
        this.hasLargeModel = true;
        return this;
    }


    public ItemModelBuilder generateWeaponModelAndStandardOverrides() {
        ItemModelBuilder mainModelBuilder = generateWeaponModel("");
        if (item.get() instanceof ArrowBlockWeaponItem) {
            addArrowBlockingWeaponOverrides(mainModelBuilder);
        }
        return mainModelBuilder;
    }

    public void addArrowBlockingWeaponOverrides(ItemModelBuilder mainModelBuilder) {
        mainModelBuilder.override()
                .predicate(new ResourceLocation("blocking"), 1f)
                .predicate(new ResourceLocation("blocked_recently"), 1f)
                .predicate(new ResourceLocation("block_pos"), 0.1f)
                .model(generateWeaponModel("block_1"))
                .end();
        mainModelBuilder.override()
                .predicate(new ResourceLocation("blocking"), 1f)
                .predicate(new ResourceLocation("blocked_recently"), 1f)
                .predicate(new ResourceLocation("block_pos"), 0.2f)
                .model(generateWeaponModel("block_2"))
                .end();
        mainModelBuilder.override()
                .predicate(new ResourceLocation("blocking"), 1f)
                .predicate(new ResourceLocation("blocked_recently"), 1f)
                .predicate(new ResourceLocation("block_pos"), 0.3f)
                .model(generateWeaponModel("block_3"))
                .end();
        mainModelBuilder.override()
                .predicate(new ResourceLocation("blocking"), 1f)
                .predicate(new ResourceLocation("blocked_recently"), 1f)
                .predicate(new ResourceLocation("block_pos"), 0.4f)
                .model(generateWeaponModel("none"))
                .end();
    }

    public ItemModelBuilder generateWeaponModel(String baseModelSuffix) {

        String returningModelFolder = !baseModelSuffix.isBlank() ? (baseModelSuffix + "/") : "";
        String parentSuffix = !baseModelSuffix.isBlank() ? baseModelSuffix : "";

        if (hasLargeModel) {
            SeparateTransformsModelBuilder<ItemModelBuilder> modelFileBuilder =
                    modelProvider.getBuilder(!baseModelSuffix.isBlank() ?
                                    ("item/" + item.getId().getPath() + "_" + baseModelSuffix) :
                                    ("item/" + item.getId().getPath()))
                            .customLoader(SeparateTransformsModelBuilder::begin);

            modelFileBuilder.base(generateModel("item_large/", "base/" + returningModelFolder, parentSuffix));
            ItemModelBuilder guiModel = generateModel("item/", "gui/", "");
            modelFileBuilder.perspective(ItemDisplayContext.GUI, guiModel);
            modelFileBuilder.perspective(ItemDisplayContext.GROUND, guiModel);
            modelFileBuilder.perspective(ItemDisplayContext.FIXED, guiModel);
            return modelFileBuilder.end();
        } else {
            return generateModel("item/", returningModelFolder, parentSuffix);
        }
    }
    public ItemModelBuilder generateModel(String directory, String returningModelFolder, String parentSuffix) {
        ItemModelBuilder itemModelBuilder = modelProvider.getBuilder("item/" + returningModelFolder + item.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated"));
        if (hasCustomTransformsOrModel || (hasLargeModel && directory.equals("item_large/"))) {
            itemModelBuilder = getModelWithWeaponParent(returningModelFolder, parentSuffix);
        }
        if (dyeableOrPotionDippable && !singleTexture) {
            itemModelBuilder.texture("layer0", dyeTextureFunction.apply(item, weapon).withPrefix(directory));
            itemModelBuilder.texture("layer1", handleTextureFunction.apply(item, weapon).withPrefix(directory));
            itemModelBuilder.texture("layer2", mainTextureFunction.apply(item, material, weapon).withPrefix(directory));
        } else if (dyeableOrPotionDippable) {
            itemModelBuilder.texture("layer0", dyeTextureFunction.apply(item, weapon).withPrefix(directory));
            itemModelBuilder.texture("layer1", mainTextureFunction.apply(item, material, weapon).withPrefix(directory));
        } else if (singleTexture) {
            itemModelBuilder.texture("layer0", mainTextureFunction.apply(item, material, weapon).withPrefix(directory));
        } else {
            itemModelBuilder.texture("layer0", handleTextureFunction.apply(item, weapon).withPrefix(directory));
            itemModelBuilder.texture("layer1", mainTextureFunction.apply(item, material, weapon).withPrefix(directory));
        }

        return itemModelBuilder;
    }

    public ItemModelBuilder getModelWithWeaponParent(String returningModelfolder, String parentSuffix) {
        String modelName = "item/" + returningModelfolder + item.getId().getPath();
        String parentName = "item/bases/" + weapon.key().location().getPath() + (!parentSuffix.isBlank() ? "_" + parentSuffix : "");

        return modelProvider.withExistingParent(modelName, new ResourceLocation(MODID, parentName));
    }
}

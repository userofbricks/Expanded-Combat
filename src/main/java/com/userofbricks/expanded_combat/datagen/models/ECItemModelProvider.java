package com.userofbricks.expanded_combat.datagen.models;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.Materials;
import com.userofbricks.expanded_combat.init.WeaponTypes;
import com.userofbricks.expanded_combat.item.*;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECItemModelProvider extends ItemModelProviderBase {

    public ECItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }
    @Override
    protected void registerModels() {
        for (DeferredHolder<Item, ? extends Item> item : ECItems.ITEMS.getEntries()) {
            dynamicallyGenerateModels(item);
        }
    }

    private void dynamicallyGenerateModels(DeferredHolder<Item, ? extends Item> item) {
        if (item.get() instanceof GauntletItem gauntletItem) {
            boolean dyeable = false;
            for (GauntletItem.Layer layer : gauntletItem.GAUNTLET_TEXTURE_LAYERS) {
                if (layer.dyeable()) {
                    dyeable = true;
                    break;
                }
            }
            generateGauntletModel(item.getId(), gauntletItem.material, dyeable);
        }

        if (item.get() instanceof ECArrowItem arrowItem) {
            if(arrowItem instanceof ECTippedArrowItem) {
                generateTippedArrowModel(item.getId(), arrowItem.material);
            } else {
                generateArrowModel(item.getId(), arrowItem.material);
            }
        }

        if (item.get() instanceof ECBowItem bowItem) {
            generateBowModel(item.getId(), bowItem.material);
        }

        if (item.get() instanceof ECCrossBowItem bowItem) {
            generateCrossBowModel(item.getId(), bowItem.material);
        }
        if (item.get() instanceof ECQuiverItem quiverItem) {
            boolean dyeable = false;
            for (ECQuiverItem.Layer layer : quiverItem.QUIVER_TEXTURE_LAYERS) {
                if (layer.dyeable()) {
                    dyeable = true;
                    break;
                }
            }
            generateQuiverModel(item.getId(), quiverItem.material, dyeable);
        }

        if (item.get() instanceof ECWeaponItem weaponItem) {
            Holder.Reference<Material> materialReference = weaponItem.material;
            Holder.Reference<WeaponType> weaponTypeReference = weaponItem.weapon;
            WeaponItemModelBuilder builder = new WeaponItemModelBuilder((DeferredItem<?>) item, materialReference, weaponTypeReference, this);

            if(weaponItem instanceof HeartStealerItem) {
                builder.setSingleTexture().setHasCustomTransformsOrModel();

                ItemModelBuilder stage1Builder =  builder.getModelWithWeaponParent("", "")
                        .texture("layer0",  new ResourceLocation(MODID, "item_large/" + item.getId().getPath() + "/stage1"));
                ItemModelBuilder stage2Builder =  builder.getModelWithWeaponParent("heartstealer/stage2_", "")
                        .texture("layer0",  new ResourceLocation(MODID, "item_large/" + item.getId().getPath() + "/stage2"));
                ItemModelBuilder stage3Builder =  builder.getModelWithWeaponParent("heartstealer/stage3_", "")
                        .texture("layer0",  new ResourceLocation(MODID, "item_large/" + item.getId().getPath() + "/stage3"));
                ItemModelBuilder stage4Builder =  builder.getModelWithWeaponParent("heartstealer/stage4_", "")
                        .texture("layer0",  new ResourceLocation(MODID, "item_large/" + item.getId().getPath() + "/stage4"));
                ItemModelBuilder stage5Builder =  builder.getModelWithWeaponParent("heartstealer/stage5_", "")
                        .texture("layer0",  new ResourceLocation(MODID, "item_large/" + item.getId().getPath() + "/stage5"));

                stage1Builder.override()
                        .predicate(new ResourceLocation("stage"), 0.4f)
                        .model(stage2Builder)
                        .end();
                stage1Builder.override()
                        .predicate(new ResourceLocation("stage"), 0.6f)
                        .model(stage3Builder)
                        .end();
                stage1Builder.override()
                        .predicate(new ResourceLocation("stage"), 0.8f)
                        .model(stage4Builder)
                        .end();
                stage1Builder.override()
                        .predicate(new ResourceLocation("stage"), 1f)
                        .model(stage5Builder)
                        .end();
            } else if (materialReference == Materials.HEAT || materialReference == Materials.FROST || materialReference == Materials.VOID_TOUCHED || materialReference == Materials.SOUL) {
                builder.setSingleTexture().setHasCustomTransformsOrModel();
                if (weaponTypeReference == WeaponTypes.DAGGER || weaponTypeReference == WeaponTypes.CUTLASS) {
                    builder.generateModel("item/", "", "");
                } else if (weaponTypeReference != WeaponTypes.MACE && weaponTypeReference != WeaponTypes.GREAT_HAMMER) {
                    builder.generateModel("item_large/", "", "");
                }
            } else if (weaponTypeReference == WeaponTypes.DAGGER || weaponTypeReference == WeaponTypes.DANCERS_SWORD || weaponTypeReference == WeaponTypes.GREAT_HAMMER ||
                    weaponTypeReference == WeaponTypes.MACE || weaponTypeReference == WeaponTypes.FLAIL) {
                builder.setHasCustomTransformsOrModel().generateWeaponModelAndStandardOverrides();
            } else {
                builder.setHasLargeModel();
                if (weaponTypeReference != WeaponTypes.KATANA && weaponTypeReference != WeaponTypes.SPEAR) {
                    builder.setDyeableOrPotionDippable();
                }
                builder.generateWeaponModelAndStandardOverrides();
            }
        }

        if (item.get() instanceof ECShieldItem) {
            withExistingParent(item.getId().getPath(), modLoc("item/bases/shield"))
                    .override()
                    .predicate(new ResourceLocation("blocking"), 1.0f)
                    .model(withExistingParent(item.getId().getPath() + "_blocking", modLoc("item/bases/shield_blocking")));
        }
    }
}

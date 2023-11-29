package com.userofbricks.expanded_combat.plugins;

import com.userofbricks.expanded_combat.api.NonNullTriFunction;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.api.material.WeaponMaterial;
import com.userofbricks.expanded_combat.api.registry.ECPlugin;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.expanded_combat.init.ECAttributes;
import com.userofbricks.expanded_combat.item.ElementalWeapon;
import com.userofbricks.expanded_combat.item.HeartStealerItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;
import static com.userofbricks.expanded_combat.api.registry.itemGeneration.WeaponItemBuilder.getItemBaseModel;

@ECPlugin
public class CustomWeaponsPlugin implements IExpandedCombatPlugin {
    public static Material HEART_STEALER;
    public static Material HEAT_MATERIAL;
    public static Material COLD_MATERIAL;
    public static Material VOID_MATERIAL;
    public static Material SOUL_MATERIAL;
    @Override
    public ResourceLocation getPluginUid() {
        return modLoc("custom_weapons");
    }

    @Override
    public void registerMaterials(RegistrationHandler registrationHandler) {
        HEART_STEALER = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Heart Stealer", CONFIG.netherite)
                .weapon(VanillaECPlugin.CLAYMORE, m -> REGISTRATE.get().item("heartstealer", HeartStealerItem::new)
                        .model((ctx, prov) -> {
                            ItemModelBuilder stage1Builder =  getItemBaseModel(prov, VanillaECPlugin.CLAYMORE, ctx, "", "")
                                    .texture("layer0",  new ResourceLocation(ctx.getId().getNamespace(), "item_large/" + ctx.getId().getPath() + "/stage1"));
                            ItemModelBuilder stage2Builder =  getItemBaseModel(prov, VanillaECPlugin.CLAYMORE, ctx, "heartstealer/stage2_", "")
                                    .texture("layer0",  new ResourceLocation(ctx.getId().getNamespace(), "item_large/" + ctx.getId().getPath() + "/stage2"));
                            ItemModelBuilder stage3Builder =  getItemBaseModel(prov, VanillaECPlugin.CLAYMORE, ctx, "heartstealer/stage3_", "")
                                    .texture("layer0",  new ResourceLocation(ctx.getId().getNamespace(), "item_large/" + ctx.getId().getPath() + "/stage3"));
                            ItemModelBuilder stage4Builder =  getItemBaseModel(prov, VanillaECPlugin.CLAYMORE, ctx, "heartstealer/stage4_", "")
                                    .texture("layer0",  new ResourceLocation(ctx.getId().getNamespace(), "item_large/" + ctx.getId().getPath() + "/stage4"));
                            ItemModelBuilder stage5Builder =  getItemBaseModel(prov, VanillaECPlugin.CLAYMORE, ctx, "heartstealer/stage5_", "")
                                    .texture("layer0",  new ResourceLocation(ctx.getId().getNamespace(), "item_large/" + ctx.getId().getPath() + "/stage5"));

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
                        })
                        .register()));

        NonNullTriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> heatConstructor = (m,w,p) -> new ElementalWeapon(m, w, p, 2, ECAttributes.HEAT_DMG);

        HEAT_MATERIAL = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Heat", CONFIG.netherite)
                .weapon(VanillaECPlugin.KATANA, null, heatConstructor, false, "Sun Master's Katana")
                .weapon(VanillaECPlugin.MACE, null, heatConstructor, false, "Sun's Firebrand")
                .weapon(VanillaECPlugin.SCYTHE, null, heatConstructor, false, "Sun's Grace")
                .weapon(VanillaECPlugin.GLAIVE, null, heatConstructor, false, "Grave Bane"));


        NonNullTriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> coldConstructor = (m,w,p) -> new ElementalWeapon(m, w, p, 2, ECAttributes.COLD_DMG);

        COLD_MATERIAL = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Frost", CONFIG.diamond)
                .weapon(VanillaECPlugin.DAGGER, null, coldConstructor, false, "Fang Of Frost")
                .weapon(VanillaECPlugin.SCYTHE, null, coldConstructor, false, null)
                .weapon(VanillaECPlugin.CLAYMORE, null, coldConstructor, false, "Frost Slayer"));


        NonNullTriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> voidConstructor = (m,w,p) -> new ElementalWeapon(m, w, p, 2, ECAttributes.VOID_DMG);

        VOID_MATERIAL = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Void Touched", CONFIG.diamond)
                .weapon(VanillaECPlugin.CLAYMORE, null, voidConstructor, false, null)
                .weapon(VanillaECPlugin.DAGGER, null, voidConstructor, false, "Void Touched Blade")
                .weapon(VanillaECPlugin.CUTLASS, null, voidConstructor, false, "Nameless Blade")
                .weapon(VanillaECPlugin.GREAT_HAMMER, null, voidConstructor, false, null));


        NonNullTriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> soulConstructor = (m,w,p) -> new ElementalWeapon(m, w, p, 2, ECAttributes.SOUL_DMG);

        SOUL_MATERIAL = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Soul", CONFIG.diamond)
                .weapon(VanillaECPlugin.KATANA, null, soulConstructor, false, "Dark Katana")
                .weapon(VanillaECPlugin.DAGGER, null, soulConstructor, false, "Eternal Soul Knife")
                .weapon(VanillaECPlugin.SCYTHE, null, soulConstructor, false, null));
    }

    @Override
    public int loadOrder() {
        return 1;
    }
}

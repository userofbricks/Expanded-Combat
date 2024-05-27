package com.userofbricks.expanded_combat.plugins;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.userofbricks.expanded_combat.api.QuadConsumer;
import com.userofbricks.expanded_combat.api.TriConsumer;
import com.userofbricks.expanded_combat.api.TriFunction;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.api.material.WeaponMaterial;
import com.userofbricks.expanded_combat.api.registry.ECPlugin;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.expanded_combat.api.registry.itemGeneration.WeaponItemBuilder;
import com.userofbricks.expanded_combat.init.ECAttributes;
import com.userofbricks.expanded_combat.item.ElementalWeapon;
import com.userofbricks.expanded_combat.item.GauntletBerserk;
import com.userofbricks.expanded_combat.item.GauntletBrawlers;
import com.userofbricks.expanded_combat.item.HeartStealerItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Function;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@ECPlugin
public class CustomWeaponsPlugin implements IExpandedCombatPlugin {
    public static Material HEART_STEALER;
    public static Material GAUNTLET;
    public static Material MAULERS;
    public static Material FIGHTER;
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
        HEART_STEALER = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Heart Stealer", CONFIG.heartStealer)
                .weapon(VanillaECPlugin.CLAYMORE, m -> REGISTRATE.get().item("heart_stealer", HeartStealerItem::new)
                        .model((ctx, prov) -> {
                        })
                        .register()));

        QuadConsumer<ItemBuilder<? extends Item, Registrate>, WeaponMaterial, Material, Material> noRecipes = (a, b, c, d) -> {};
        TriConsumer<ItemBuilder<? extends Item, Registrate>, WeaponMaterial, Material> noColor = (a, b, c) -> {};
        TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> heatConstructor = (m, w, p) -> new ElementalWeapon(m, w, p, 2, ECAttributes.HEAT_DMG);
        Function<String, QuadConsumer<DataGenContext<Item, ? extends Item>, RegistrateItemModelProvider, Material, WeaponMaterial>> modelBuilder = (s) ->(ctx, prov, m, w) -> WeaponItemBuilder.generateModel(ctx, prov, w, m, s, "", "", true);

        HEAT_MATERIAL = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Heat", CONFIG.heat)
                .weaponBuilder(VanillaECPlugin.KATANA, null, heatConstructor).lang("Sun Master's Katana").model(modelBuilder.apply("item_large/")).recipes(noRecipes).colors(noColor).build()
                .weaponBuilder(VanillaECPlugin.MACE, null, heatConstructor).lang("Sun's Firebrand").recipes(noRecipes).colors(noColor).build()
                .weaponBuilder(VanillaECPlugin.SCYTHE, null, heatConstructor).lang("Sun's Grace").model(modelBuilder.apply("item_large/")).recipes(noRecipes).colors(noColor).build()
                .weaponBuilder(VanillaECPlugin.GLAIVE, null, heatConstructor).lang("Grave Bane").model(modelBuilder.apply("item_large/")).recipes(noRecipes).colors(noColor).build());


        TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> coldConstructor = (m, w, p) -> new ElementalWeapon(m, w, p, 2, ECAttributes.COLD_DMG);

        COLD_MATERIAL = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Frost", CONFIG.frost)
                .weaponBuilder(VanillaECPlugin.DAGGER, null, coldConstructor).lang("Fang Of Frost").model(modelBuilder.apply("item/")).recipes(noRecipes).colors(noColor).build()
                .weaponBuilder(VanillaECPlugin.SCYTHE, null, coldConstructor).model(modelBuilder.apply("item_large/")).recipes(noRecipes).colors(noColor).build()
                .weaponBuilder(VanillaECPlugin.CLAYMORE, null, coldConstructor).lang("Frost Slayer").model(modelBuilder.apply("item_large/")).recipes(noRecipes).colors(noColor).build());


        TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> voidConstructor = (m, w, p) -> new ElementalWeapon(m, w, p, 2, ECAttributes.VOID_DMG);

        VOID_MATERIAL = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Void Touched", CONFIG.voidTouched)
                .weaponBuilder(VanillaECPlugin.CLAYMORE, null, voidConstructor).model(modelBuilder.apply("item_large/")).recipes(noRecipes).colors(noColor).build()
                .weaponBuilder(VanillaECPlugin.DAGGER, null, voidConstructor).lang("Void Touched Blade").model(modelBuilder.apply("item/")).recipes(noRecipes).colors(noColor).build()
                .weaponBuilder(VanillaECPlugin.CUTLASS, null, voidConstructor).lang("Nameless Blade").model(modelBuilder.apply("item/")).recipes(noRecipes).colors(noColor).build()
                .weaponBuilder(VanillaECPlugin.GREAT_HAMMER, null, voidConstructor).recipes(noRecipes).colors(noColor).build());


        TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> soulConstructor = (m, w, p) -> new ElementalWeapon(m, w, p, 2, ECAttributes.SOUL_DMG);

        SOUL_MATERIAL = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Soul", CONFIG.soul)
                .weaponBuilder(VanillaECPlugin.KATANA, null, soulConstructor).lang("Dark Katana").model(modelBuilder.apply("item_large/")).recipes(noRecipes).colors(noColor).build()
                .weaponBuilder(VanillaECPlugin.DAGGER, null, soulConstructor).lang("Eternal Soul Knife").model(modelBuilder.apply("item/")).recipes(noRecipes).colors(noColor).build()
                .weaponBuilder(VanillaECPlugin.SCYTHE, null, soulConstructor).model(modelBuilder.apply("item_large/")).recipes(noRecipes).colors(noColor).build()
                .gauntlet(null, SoulFist::new).lang("Soul Fist").recipes((a,b,c) ->{}).build(false));

        FIGHTER = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Fighters", CONFIG.fighters)
                .gauntlet(null, FightersBindings::new).lang("Fighters Bindings").recipes((a,b,c) ->{}).build(false));

        MAULERS = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Maulers", CONFIG.maulers)
                .gauntlet(null, GauntletBerserk::new).lang("Maulers").recipes((a, b, c) ->{}).build(false));

        GAUNTLET = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Gauntlet", CONFIG.gauntlet)
                .gauntlet(null, GauntletBrawlers::new).lang("Gauntlet").recipes((a, b, c) ->{}).build(false));
    }

    @Override
    public int loadOrder() {
        return 1;
    }
}

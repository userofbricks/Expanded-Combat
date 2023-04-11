package com.userofbricks.expanded_combat.values;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShieldMaterial {

    private final String name;
    private final ForgeConfigSpec.IntValue addedDurability;
    private final ForgeConfigSpec.DoubleValue baseProtectionAmmount;
    private final ForgeConfigSpec.DoubleValue afterBasePercentReduction;
    private final ForgeConfigSpec.ConfigValue<String> ingotOrMaterial;
    private final ForgeConfigSpec.DoubleValue mendingBonus;
    private final ForgeConfigSpec.BooleanValue isSingleAddition;
    private final ForgeConfigSpec.BooleanValue fireResistant;
    private final ForgeConfigSpec.ConfigValue<ArrayList<String>> requiredBeforeResource;
    private final ForgeConfigSpec.ConfigValue<ArrayList<String>> onlyReplaceResource;
    public final RegistryEntry<Item> ULModel;
    public final RegistryEntry<Item> URModel;
    public final RegistryEntry<Item> DLModel;
    public final RegistryEntry<Item> DRModel;
    public final RegistryEntry<Item> MModel;

    ShieldMaterial(ForgeConfigSpec.Builder builder, String name, double mendingBonus, double baseProtectionAmmount, double afterBasePercentReduction, String ingotOrMaterial, int addedDurability, boolean isSingleAddition, boolean fireResistant, ArrayList<String> requiredBeforeResource, ArrayList<String> onlyReplaceResource) {
        builder.push(name + " Shield");
        this.name =                           name;
        this.mendingBonus =                   builder.comment("Default Value: " + mendingBonus)             .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletMendingBonus")              .defineInRange(name.toLowerCase(Locale.ROOT) + "GauntletMendingBonus",            mendingBonus,             Double.MIN_VALUE, Double.MAX_VALUE);
        this.addedDurability =                builder.comment("Default value: " + addedDurability)          .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldAddedDurability")             .defineInRange(name.toLowerCase(Locale.ROOT) + "ShieldAddedDurability",           addedDurability,          0, Integer.MAX_VALUE);
        this.baseProtectionAmmount =          builder.comment("Dafault value: " + baseProtectionAmmount)    .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldBaseProtectionAmmount")       .defineInRange(name.toLowerCase(Locale.ROOT) + "ShieldBaseProtectionAmmount",     baseProtectionAmmount,    0, Double.MAX_VALUE);
        this.afterBasePercentReduction =      builder.comment("Dafault value: " + afterBasePercentReduction).translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldAfterBasePercentReduction")   .defineInRange(name.toLowerCase(Locale.ROOT) + "ShieldAfterBasePercentReduction", afterBasePercentReduction,0, 1);
        this.ingotOrMaterial =                builder
                .comment("default Value: " + ingotOrMaterial)
                .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldIngredientItems")
                .define(name.toLowerCase(Locale.ROOT) + "ShieldIngredientItems", ingotOrMaterial);
        this.isSingleAddition =               builder.comment("Dafault value: " + isSingleAddition)         .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldIsSingleAddition")            .define(name.toLowerCase(Locale.ROOT) + "ShieldIsSingleAddition",                 isSingleAddition);
        this.fireResistant =                  builder.comment("Default value: " + fireResistant)            .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldFireResistance")              .define(name.toLowerCase(Locale.ROOT) + "ShieldFireResistance",                   fireResistant);
        requiredBeforeResource.removeIf(requiredBeforeName -> !name.equals(""));
        this.requiredBeforeResource = builder
                .comment("Default value: " + requiredBeforeResource)
                .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldRequiredBefore")
                .define(name.toLowerCase(Locale.ROOT) + "ShieldRequiredBefore", requiredBeforeResource);
        onlyReplaceResource.removeIf(onlyReplaceName -> !name.equals(""));
        this.onlyReplaceResource =    builder
                .comment("Default value: " + onlyReplaceResource)
                .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldOnlyReplace")
                .define(name.toLowerCase(Locale.ROOT) + "ShieldOnlyReplace", onlyReplaceResource);
        builder.pop(1);

        //register items used for models
        ULModel = createModelItem(name, "ul");
        URModel = createModelItem(name, "ur");
        DLModel = createModelItem(name, "dl");
        DRModel = createModelItem(name, "dr");
        MModel = createModelItem(name, "m");
    }

    ShieldMaterial(ForgeConfigSpec.Builder builder, String name, double medingBonus, double baseProtectionAmmount, double afterBasePercentReduction, Ingredient ingotOrMaterial, int addedDurability, boolean isSingleAddition, boolean fireResistant, ArrayList<String> requiredBeforeResource, ArrayList<String> onlyReplaceResource, List<ShieldMaterial> shieldMaterials) {
        this(builder, name, medingBonus, baseProtectionAmmount, afterBasePercentReduction, IngredientUtil.getItemStringFromIngrediant(ingotOrMaterial), addedDurability, isSingleAddition, fireResistant, requiredBeforeResource, onlyReplaceResource);
        shieldMaterials.add(this);
    }

    ShieldMaterial(ForgeConfigSpec.Builder builder, String name, double medingBonus, double baseProtectionAmmount, double afterBasePercentReduction, String ingotOrMaterial, int addedDurability, boolean isSingleAddition, boolean fireResistant, ArrayList<String> requiredBeforeResource, ArrayList<String> onlyReplaceResource, List<ShieldMaterial> shieldMaterials) {
        this(builder, name, medingBonus, baseProtectionAmmount, afterBasePercentReduction, ingotOrMaterial, addedDurability, isSingleAddition, fireResistant, requiredBeforeResource, onlyReplaceResource);
        shieldMaterials.add(this);
    }

    private RegistryEntry<Item> createModelItem(String name, String suffix) {
        return ExpandedCombat.REGISTRATE.get().item("shield_model/" + name.toLowerCase(Locale.ROOT) + "_" + suffix, Item::new)
                .model((ctx, prov) -> prov.withExistingParent("item/" + ctx.getName(), prov.modLoc("item/bases/shield_" + suffix))
                        .texture("base", prov.modLoc("item/shields/" + name.toLowerCase(Locale.ROOT))))
                .register();
    }

    public double getAfterBasePercentReduction() {
        return afterBasePercentReduction.get();
    }

    public Ingredient getIngotOrMaterial() {
        return IngredientUtil.getIngrediantFromItemString(ingotOrMaterial.get());
    }

    public double getMendingBonus() {
        return mendingBonus.get();
    }

    public double getBaseProtectionAmmount() {
        return baseProtectionAmmount.get();
    }

    public int getAddedDurability() {
        return addedDurability.get();
    }

    public boolean isEmpty() {
        return this.name.equals("empty");
    }

    public boolean isSingleAddition() {
        return isSingleAddition.get();
    }

    public boolean satifiesbeforeRequirement(String shieldMaterialName) {
        if (requiredBeforeResource.get().isEmpty()) return true;
        for (String name :
                requiredBeforeResource.get()) {
            if (name.equals(shieldMaterialName)) return true;
        }
        return false;
    }

    /**
     * used for single additions only
     */
    public boolean satifiesOnlyReplaceRequirement(String shieldMaterialName) {
        if (onlyReplaceResource.get().isEmpty()) return true;
        for (String name :
                onlyReplaceResource.get()) {
            if (name.equals(shieldMaterialName)) return true;
        }
        return false;
    }

    public boolean getFireResistant() {
        return fireResistant.get();
    }

    public String getName() {
        return name;
    }

    public static ShieldMaterial getFromName(String name) {
        for (ShieldMaterial material :
                ECConfig.SERVER.shieldMaterials) {
            if (material.getName().equals(name)) {
                return material;
            }
        }
        return ECConfig.SERVER.emptyShield;
    }

    public static ShieldMaterial getFromItemStack(ItemStack itemStack) {
        for (ShieldMaterial material :
                ECConfig.SERVER.shieldMaterials) {
            if (material.getIngotOrMaterial().test(itemStack)) {
                return material;
            }
        }
        return ECConfig.SERVER.emptyShield;
    }
}

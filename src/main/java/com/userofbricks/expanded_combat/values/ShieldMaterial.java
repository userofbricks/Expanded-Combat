package com.userofbricks.expanded_combat.values;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class ShieldMaterial {

    private final String name;
    private final ForgeConfigSpec.IntValue addedDurability;
    private final ForgeConfigSpec.DoubleValue baseProtectionAmmount;
    private final ForgeConfigSpec.DoubleValue afterBasePercentReduction;
    private final Ingredient ingotOrMaterial;
    private final ForgeConfigSpec.DoubleValue mendingBonus;
    private final ForgeConfigSpec.BooleanValue isSingleAddition;
    private final ForgeConfigSpec.BooleanValue fireResistant;
    private final String requiredBeforeResourceLocation;
    @Nullable
    private final TagKey<Item> requiredBefore;
    private final String onlyReplaceResourceLocation;
    @Nullable
    private final TagKey<Item> onlyReplace;
    public final RegistryEntry<Item> ULModel;
    public final RegistryEntry<Item> URModel;
    public final RegistryEntry<Item> DLModel;
    public final RegistryEntry<Item> DRModel;
    public final RegistryEntry<Item> MModel;

    ShieldMaterial(ForgeConfigSpec.Builder builder, String name, double mendingBonus, double baseProtectionAmmount, double afterBasePercentReduction, Ingredient ingotOrMaterial, int addedDurability, boolean isSingleAddition, boolean fireResistant, String requiredBeforeResourceLocation, String onlyReplaceResourceLocation) {
        builder.push(name + " Shield");
        this.name =                           name;
        this.mendingBonus =                   builder.comment("Default Value: " + mendingBonus)             .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletMendingBonus")              .defineInRange(name.toLowerCase(Locale.ROOT) + "GauntletMendingBonus",            mendingBonus,             Double.MIN_VALUE, Double.MAX_VALUE);
        this.addedDurability =                builder.comment("Default value: " + addedDurability)          .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldAddedDurability")             .defineInRange(name.toLowerCase(Locale.ROOT) + "ShieldAddedDurability",           addedDurability,          0, Integer.MAX_VALUE);
        this.baseProtectionAmmount =          builder.comment("Dafault value: " + baseProtectionAmmount)    .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldBaseProtectionAmmount")       .defineInRange(name.toLowerCase(Locale.ROOT) + "ShieldBaseProtectionAmmount",     baseProtectionAmmount,    0, Double.MAX_VALUE);
        this.afterBasePercentReduction =      builder.comment("Dafault value: " + afterBasePercentReduction).translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldAfterBasePercentReduction")   .defineInRange(name.toLowerCase(Locale.ROOT) + "ShieldAfterBasePercentReduction", afterBasePercentReduction,0, 1);
        this.ingotOrMaterial =                ingotOrMaterial;
        this.isSingleAddition =               builder.comment("Dafault value: " + isSingleAddition)         .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldIsSingleAddition")            .define(name.toLowerCase(Locale.ROOT) + "ShieldIsSingleAddition",                 isSingleAddition);
        this.fireResistant =        builder.comment("Default value: " + fireResistant)      .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "ShieldFireResistance")     .define(name.toLowerCase(Locale.ROOT) + "ShieldFireResistance", fireResistant);
        this.requiredBeforeResourceLocation = requiredBeforeResourceLocation;
        if (requiredBeforeResourceLocation.isEmpty()) {
            this.requiredBefore =             null;
        } else {
            this.requiredBefore =             ItemTags.create(new ResourceLocation(requiredBeforeResourceLocation));
        }
        this.onlyReplaceResourceLocation =    onlyReplaceResourceLocation;
        if (onlyReplaceResourceLocation.isEmpty()) {
            this.onlyReplace =                null;
        } else {
            this.onlyReplace =                ItemTags.create(new ResourceLocation(onlyReplaceResourceLocation));
        }
        builder.pop(1);

        //register items used for models
        ULModel = createModelItem(name, "ul");
        URModel = createModelItem(name, "ur");
        DLModel = createModelItem(name, "dl");
        DRModel = createModelItem(name, "dr");
        MModel = createModelItem(name, "m");
    }

    ShieldMaterial(ForgeConfigSpec.Builder builder, String name, double medingBonus, double baseProtectionAmmount, double afterBasePercentReduction, Ingredient ingotOrMaterial, int addedDurability, boolean isSingleAddition, boolean fireResistant, String requiredBeforeResourceLocation, String onlyReplaceResourceLocation, List<ShieldMaterial> shieldMaterials) {
        this(builder, name, medingBonus, baseProtectionAmmount, afterBasePercentReduction, ingotOrMaterial, addedDurability, isSingleAddition, fireResistant, requiredBeforeResourceLocation, onlyReplaceResourceLocation);
        shieldMaterials.add(this);
    }

    private RegistryEntry<Item> createModelItem(String name, String suffix) {
        return ExpandedCombat.REGISTRATE.get().item("shield_model/" + name.toLowerCase(Locale.ROOT) + "_" + suffix, Item::new)
                .model((ctx, prov) -> {
                    prov.withExistingParent("item/" + ctx.getName(), prov.modLoc("item/bases/shield_" + suffix))
                            .texture("base", prov.modLoc("item/shields/" + name.toLowerCase(Locale.ROOT)));
                })
                .register();
    }

    public double getAfterBasePercentReduction() {
        return afterBasePercentReduction.get();
    }

    public Ingredient getIngotOrMaterial() {
        return ingotOrMaterial;
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

    @Nullable
    public TagKey<Item> getRequiredBefore() {
        return requiredBefore;
    }

    private String getRequiredBeforeResourceLocation() {
        return requiredBeforeResourceLocation;
    }

    private String getOnlyReplaceResourceLocation() {
        return onlyReplaceResourceLocation;
    }

    public boolean getFireResistant() {
        return fireResistant.get();
    }

    @Nullable
    public TagKey<Item> getOnlyReplace() {
        return onlyReplace;
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

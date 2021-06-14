package com.userofbricks.expandedcombat.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.world.World;
import net.minecraft.entity.LivingEntity;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.DispenserBlock;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public class ECShieldItem extends ShieldItem
{
    private final int tier;
    
    public ECShieldItem(int tier, Item.Properties properties) {
        super(properties.stacksTo(1));
        this.tier = tier;
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }
    @Nonnull
    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.getOrCreateTag().putString("UL_Material", "empty");
        stack.getOrCreateTag().putString("UR_Material", "empty");
        stack.getOrCreateTag().putString("DL_Material", "empty");
        stack.getOrCreateTag().putString("DR_Material", "empty");
        stack.getOrCreateTag().putString("M_Material", "empty");
        return stack;
    }

    @Override
    public void fillItemCategory(@Nonnull ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for (ShieldMaterial material : ShieldMaterial.values()) {
                if (material.getTier() == this.tier && material != ShieldMaterial.empty) {
                    ItemStack stack = this.getDefaultInstance();
                    stack.getOrCreateTag().putString("UL_Material", material.getName());
                    stack.getOrCreateTag().putString("UR_Material", material.getName());
                    stack.getOrCreateTag().putString("DL_Material", material.getName());
                    stack.getOrCreateTag().putString("DR_Material", material.getName());
                    stack.getOrCreateTag().putString("M_Material", material.getName());
                    items.add(stack);
                }
            }
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        int durability = 336;
        int ul = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("UL_Material")).getAddedDurability();
        int ur = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("UR_Material")).getAddedDurability();
        int dl = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("DL_Material")).getAddedDurability();
        int dr = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("DR_Material")).getAddedDurability();
        int m = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("M_Material")).getAddedDurability();
        return durability + ul + ur + dl + dr + m;
    }
    
    @ParametersAreNonnullByDefault
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        Ingredient ul = ShieldMaterial.getFromName(toRepair.getOrCreateTag().getString("UL_Material")).getIngotOrMaterial();
        Ingredient ur = ShieldMaterial.getFromName(toRepair.getOrCreateTag().getString("UR_Material")).getIngotOrMaterial();
        Ingredient dl = ShieldMaterial.getFromName(toRepair.getOrCreateTag().getString("DL_Material")).getIngotOrMaterial();
        Ingredient dr = ShieldMaterial.getFromName(toRepair.getOrCreateTag().getString("DR_Material")).getIngotOrMaterial();
        Ingredient m = ShieldMaterial.getFromName(toRepair.getOrCreateTag().getString("M_Material")).getIngotOrMaterial();
        List<Ingredient> h = getNumberOfSame(ul, ur, dl, dr, m);
        List<Ingredient> i = getNumberOfSame(ur, ul, dl, dr, m);
        List<Ingredient> j = getNumberOfSame(dl, ur, ul, dr, m);
        List<Ingredient> k = getNumberOfSame(dr, ur, dl, ul, m);
        List<Ingredient> l = getNumberOfSame(m, ur, dl, dr, ul);
        List<Ingredient> first = h.size() > i.size() ? h:i;
        List<Ingredient> second = k.size() > j.size() ? k:j;
        List<Ingredient> third = second.size() > first.size() ? second:first;
        List<Ingredient> result = third.size() > l.size() ? third:l;
        return result.get(0).test(repair);
    }

    private List<Ingredient> getNumberOfSame(Ingredient reference, Ingredient canadate1, Ingredient canadate2, Ingredient canadate3, Ingredient canadate4) {
        List<Ingredient> list = new ArrayList<>();
        list.add(reference);
        if (reference == canadate1) list.add(canadate1);
        if (reference == canadate2) list.add(canadate2);
        if (reference == canadate3) list.add(canadate3);
        if (reference == canadate4) list.add(canadate4);
        return list;
    }

    @Override
    public boolean isShield(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public float getXpRepairRatio(ItemStack stack) {
        return 2.0f + getMendingBonus(stack);
    }
    
    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        String ul = stack.getOrCreateTag().getString("UL_Material");
        String ur = stack.getOrCreateTag().getString("UR_Material");
        String dl = stack.getOrCreateTag().getString("DL_Material");
        String dr = stack.getOrCreateTag().getString("DR_Material");
        String m = stack.getOrCreateTag().getString("M_Material");
        list.add(new TranslationTextComponent("tooltip.expanded_combat.shield_material.upper_left").withStyle(TextFormatting.GRAY, TextFormatting.ITALIC).append(new TranslationTextComponent("tooltip.expanded_combat.shield_material" + ul).withStyle(TextFormatting.GRAY, TextFormatting.ITALIC)));
        list.add(new TranslationTextComponent("tooltip.expanded_combat.shield_material.upper_right").withStyle(TextFormatting.GRAY, TextFormatting.ITALIC).append(new TranslationTextComponent("tooltip.expanded_combat.shield_material" + ur).withStyle(TextFormatting.GRAY, TextFormatting.ITALIC)));
        list.add(new TranslationTextComponent("tooltip.expanded_combat.shield_material.pegs_trim").withStyle(TextFormatting.GRAY, TextFormatting.ITALIC).append(new TranslationTextComponent("tooltip.expanded_combat.shield_material" + m).withStyle(TextFormatting.GRAY, TextFormatting.ITALIC)));
        list.add(new TranslationTextComponent("tooltip.expanded_combat.shield_material.lower_left").withStyle(TextFormatting.GRAY, TextFormatting.ITALIC).append(new TranslationTextComponent("tooltip.expanded_combat.shield_material" + dl).withStyle(TextFormatting.GRAY, TextFormatting.ITALIC)));
        list.add(new TranslationTextComponent("tooltip.expanded_combat.shield_material.lower_right").withStyle(TextFormatting.GRAY, TextFormatting.ITALIC).append(new TranslationTextComponent("tooltip.expanded_combat.shield_material" + dr).withStyle(TextFormatting.GRAY, TextFormatting.ITALIC)));

        if (getMendingBonus(stack) != 0.0f) {
            if (getMendingBonus(stack) > 0.0f) {
                list.add(new TranslationTextComponent("tooltip.expanded_combat.mending_bonus").withStyle(TextFormatting.GREEN).append(new StringTextComponent(TextFormatting.GREEN + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(getMendingBonus(stack)))));
            }
            else if (getMendingBonus(stack) < 0.0f) {
                list.add(new TranslationTextComponent("tooltip.expanded_combat.mending_bonus").withStyle(TextFormatting.RED).append(new StringTextComponent(TextFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(getMendingBonus(stack)))));
            }
        }
        super.appendHoverText(stack, world, list, flag);
    }

    public float getMendingBonus(ItemStack stack) {
        float ul = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("UL_Material")).getMedingBonus() /5;
        float ur = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("UR_Material")).getMedingBonus() /5;
        float dl = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("DL_Material")).getMedingBonus() /5;
        float dr = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("DR_Material")).getMedingBonus() /5;
        float m = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("M_Material")).getMedingBonus() /5;
        return ul + ur + dl + dr + m;
    }

    public float getBaseProtection(ItemStack stack) {
        float ul = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("UL_Material")).getBaseProtectionAmmount() /5;
        float ur = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("UR_Material")).getBaseProtectionAmmount() /5;
        float dl = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("DL_Material")).getBaseProtectionAmmount() /5;
        float dr = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("DR_Material")).getBaseProtectionAmmount() /5;
        float m = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("M_Material")).getBaseProtectionAmmount() /5;
        return ul + ur + dl + dr + m;
    }

    public float getPercentageProtection(ItemStack stack) {
        float ul = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("UL_Material")).getAfterBasePercentReduction() /5;
        float ur = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("UR_Material")).getAfterBasePercentReduction() /5;
        float dl = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("DL_Material")).getAfterBasePercentReduction() /5;
        float dr = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("DR_Material")).getAfterBasePercentReduction() /5;
        float m = ShieldMaterial.getFromName(stack.getOrCreateTag().getString("M_Material")).getAfterBasePercentReduction() /5;
        return ul + ur + dl + dr + m;
    }

    public int getTier() {
        return this.tier;
    }
}

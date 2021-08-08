package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.client.renderer.model.ECShieldBlockEntityWithoutLevelRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

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
    public void fillItemCategory(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
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
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
        String ul = stack.getOrCreateTag().getString("UL_Material");
        String ur = stack.getOrCreateTag().getString("UR_Material");
        String dl = stack.getOrCreateTag().getString("DL_Material");
        String dr = stack.getOrCreateTag().getString("DR_Material");
        String m = stack.getOrCreateTag().getString("M_Material");
        list.add(new TranslatableComponent("tooltip.expanded_combat.shield_material.upper_left").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(new TranslatableComponent("tooltip.expanded_combat.shield_material." + ul).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        list.add(new TranslatableComponent("tooltip.expanded_combat.shield_material.upper_right").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(new TranslatableComponent("tooltip.expanded_combat.shield_material." + ur).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        list.add(new TranslatableComponent("tooltip.expanded_combat.shield_material.pegs_trim").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(new TranslatableComponent("tooltip.expanded_combat.shield_material." + m).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        list.add(new TranslatableComponent("tooltip.expanded_combat.shield_material.lower_left").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(new TranslatableComponent("tooltip.expanded_combat.shield_material." + dl).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        list.add(new TranslatableComponent("tooltip.expanded_combat.shield_material.lower_right").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(new TranslatableComponent("tooltip.expanded_combat.shield_material." + dr).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));

        if (getMendingBonus(stack) != 0.0f) {
            if (getMendingBonus(stack) > 0.0f) {
                list.add(new TranslatableComponent("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN).append(new TextComponent(ChatFormatting.GREEN + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(getMendingBonus(stack)))));
            }
            else if (getMendingBonus(stack) < 0.0f) {
                list.add(new TranslatableComponent("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.RED).append(new TextComponent(ChatFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(getMendingBonus(stack)))));
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
    public void initializeClient(@Nonnull java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return new ECShieldBlockEntityWithoutLevelRenderer();
            }
        });
    }

    public int getTier() {
        return this.tier;
    }
}

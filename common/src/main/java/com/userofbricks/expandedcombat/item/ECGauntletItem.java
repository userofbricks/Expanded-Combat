package com.userofbricks.expandedcombat.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

public class ECGauntletItem extends Item implements ICustomMendingRatio
{
    private final ResourceLocation GAUNTLET_TEXTURE;
    private final IGauntletMaterial material;
    private final double attackDamage;
    protected final int armorAmount;
    public static final UUID ATTACK_UUID = UUID.fromString("7ce10414-adcc-4bf2-8804-f5dbd39fadaf");
    public static final UUID ARMOR_UUID = UUID.fromString("38faf191-bf78-4654-b349-cc1f4f1143bf");
    public static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.fromString("b64fd3d6-a9fe-46a1-a972-90e4b0849678");
    public static final UUID KNOCKBACK_UUID = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");
    public Boolean hasWeaponInHand = false;

    public ECGauntletItem(IGauntletMaterial materialIn, Properties properties) {
        super(properties.defaultDurability(materialIn.getDurability()));
        this.material = materialIn;
        this.GAUNTLET_TEXTURE = new ResourceLocation("expanded_combat", "textures/entity/gauntlet/" + materialIn.getName() + ".png");
        this.attackDamage = materialIn.getAttackDamage();
        this.armorAmount = materialIn.getArmorAmount();
    }
    
    public IGauntletMaterial getMaterial() {
        return this.material;
    }
    
    public int getEnchantmentValue() {
        return this.material.getEnchantability();
    }
    
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return this.material.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    public float getXpRepairRatio( ItemStack stack) {
        return this.material == GauntletMaterials.gold ? 4.0f : 2.0f;
    }

    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
        if (this.material == GauntletMaterials.gold) {
            list.add(new TranslatableComponent("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN).append(new TextComponent(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(2L))));
        }
    }
    
    public int getArmorAmount() {
        return this.armorAmount;
    }
    
    public double getAttackDamage() {
        return this.attackDamage;
    }
    
    public ResourceLocation getGAUNTLET_TEXTURE() {
        return this.GAUNTLET_TEXTURE;
    }

    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return stack.getItem() instanceof ECGauntletItem && ((ECGauntletItem) stack.getItem()).getMaterial() == GauntletMaterials.gold;
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        if (this.allowdedIn(tab)) {
            ItemStack istack = new ItemStack(this);
            if (this.getMaterial() == GauntletMaterials.steeleaf) {
                istack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
            }
            list.add(istack);
        }
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        if (enchantment == Enchantments.KNOCKBACK || enchantment == Enchantments.PUNCH_ARROWS) {
            return true;
        }
        return enchantment.category.canEnchant(stack.getItem());
    }
}

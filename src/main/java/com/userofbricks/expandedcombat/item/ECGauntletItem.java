package com.userofbricks.expandedcombat.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expandedcombat.enchentments.ECEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.UUID;

public class ECGauntletItem extends Item implements ICurioItem
{
    private final ResourceLocation GAUNTLET_TEXTURE;
    private final IGauntletMaterial material;
    private final double attackDamage;
    protected final int armorAmount;
    private static final UUID ATTACK_UUID = UUID.fromString("7ce10414-adcc-4bf2-8804-f5dbd39fadaf");
    private static final UUID ARMOR_UUID = UUID.fromString("38faf191-bf78-4654-b349-cc1f4f1143bf");
    private static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.fromString("b64fd3d6-a9fe-46a1-a972-90e4b0849678");
    private static final UUID KNOCKBACK_UUID = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");
    public Boolean hasWeaponInHand = false;

    @ParametersAreNonnullByDefault
    public ECGauntletItem(IGauntletMaterial materialIn, Item.Properties properties) {
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

    @Override
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

    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        livingEntity.level.playSound(null, new BlockPos(livingEntity.position()), this.material.getSoundEvent(), SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        String identifier = slotContext.getIdentifier();
        Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();
        if (CuriosApi.getCuriosHelper().getCurioTags(stack.getItem()).contains(identifier) && stack.getItem() instanceof ECGauntletItem) {
            double attackDamage = ((ECGauntletItem)stack.getItem()).getAttackDamage();
            double nagaDamage = ((ECGauntletItem)stack.getItem()).getMaterial() == GauntletMaterials.naga ? (attackDamage/2.0d*3) : 0;
            double yetiDamage = ((ECGauntletItem)stack.getItem()).getMaterial() == GauntletMaterials.yeti ? (attackDamage/2.0d) : 0;
            int armorAmount = ((ECGauntletItem)stack.getItem()).getArmorAmount();
            double knockbackResistance = ((ECGauntletItem)stack.getItem()).getMaterial().getKnockbackResistance();
            double toughness = ((ECGauntletItem)stack.getItem()).getMaterial().getToughness();
            if (((ECGauntletItem) stack.getItem()).hasWeaponInHand) {
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", (attackDamage + Math.round(attackDamage / 2.0d * EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack)) + nagaDamage + yetiDamage) / 2d, AttributeModifier.Operation.ADDITION));
            } else {
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", (attackDamage + Math.round(attackDamage / 2.0d * EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack)) + nagaDamage + yetiDamage), AttributeModifier.Operation.ADDITION));
            }
            atts.put(Attributes.ARMOR, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor bonus", armorAmount, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor Toughness bonus", toughness, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ECGauntletItem.KNOCKBACK_RESISTANCE_UUID, "Knockback resistance bonus", (knockbackResistance + EnchantmentHelper.getItemEnchantmentLevel(ECEnchantments.KNOCKBACK_RESISTANCE.get(), stack) / 5.0f), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ECGauntletItem.KNOCKBACK_UUID, "Knockback bonus", EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, stack), AttributeModifier.Operation.ADDITION));
        }
        return atts;
    }

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        if (enchantment == Enchantments.KNOCKBACK || enchantment == Enchantments.PUNCH_ARROWS) {
            return true;
        }
        return enchantment.category.canEnchant(stack.getItem());
    }
}

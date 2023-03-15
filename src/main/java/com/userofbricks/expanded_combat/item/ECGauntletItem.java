package com.userofbricks.expanded_combat.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.enchentments.ECEnchantments;
import com.userofbricks.expanded_combat.values.ECConfig;
import com.userofbricks.expanded_combat.values.GauntletMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.UUID;

public class ECGauntletItem extends Item implements ICurioItem, Wearable
{
    private final ResourceLocation GAUNTLET_TEXTURE;
    private final GauntletMaterial material;
    private static final UUID ATTACK_UUID = UUID.fromString("7ce10414-adcc-4bf2-8804-f5dbd39fadaf");
    private static final UUID ARMOR_UUID = UUID.fromString("38faf191-bf78-4654-b349-cc1f4f1143bf");
    private static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.fromString("b64fd3d6-a9fe-46a1-a972-90e4b0849678");
    private static final UUID KNOCKBACK_UUID = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");
    public Boolean hasWeaponInHand = false;

    @ParametersAreNonnullByDefault
    public ECGauntletItem(GauntletMaterial materialIn, Item.Properties properties) {
        super(properties);
        this.material = materialIn;
        this.GAUNTLET_TEXTURE = new ResourceLocation("expanded_combat", "textures/entity/gauntlet/" + materialIn.getTextureName() + ".png");
    }
    
    public GauntletMaterial getMaterial() {
        return this.material;
    }
    
    public int getEnchantmentValue(ItemStack stack) {
        return this.material.getEnchantability();
    }
    
    public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
        return this.material.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.material.getDurability();
    }

    public boolean canBeDepleted() {
        return true;
    }

    public float getXpRepairRatio( ItemStack stack) {
        return this.material == ECConfig.SERVER.goldGauntlet ? 4.0f : 2.0f;
    }

    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("tooltip.expanded_combat.half_added_damage_when_holding_item").withStyle(ChatFormatting.GRAY));
        if (this.material == ECConfig.SERVER.goldGauntlet) {
            list.add(Component.translatable("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN)
                    .append(Component.literal(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(2L))));
        }
    }
    
    public int getArmorAmount() {
        return this.material.getArmorAmount();
    }
    
    public double getAttackDamage() {
        return this.material.getAttackDamage();
    }
    
    public ResourceLocation getGAUNTLET_TEXTURE() {
        return this.GAUNTLET_TEXTURE;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return stack.getItem() instanceof ECGauntletItem && ((ECGauntletItem) stack.getItem()).getMaterial() == ECConfig.SERVER.goldGauntlet;
    }

    /*TODO: creative tabs
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        if (this.allowdedIn(tab)) {
            ItemStack istack = new ItemStack(this);
            if (this.getMaterial() == GauntletMaterials.steeleaf) {
                istack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
            }
            list.add(istack);
        }
    }*/

    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        livingEntity.level.playSound(null, new BlockPos(livingEntity.position()), this.material.getSoundEvent(), SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        String identifier = slotContext.identifier();
        Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();
        if (CuriosApi.getCuriosHelper().getCurioTags(stack.getItem()).contains(identifier) && stack.getItem() instanceof ECGauntletItem) {
            double attackDamage = ((ECGauntletItem)stack.getItem()).getAttackDamage();
            double nagaDamage = ((ECGauntletItem)stack.getItem()).getMaterial() == ECConfig.SERVER.nagaGauntlet ? (attackDamage/2.0d*3) : 0;
            double yetiDamage = ((ECGauntletItem)stack.getItem()).getMaterial() == ECConfig.SERVER.yetiGauntlet ? (attackDamage/2.0d) : 0;
            int armorAmount = ((ECGauntletItem)stack.getItem()).getArmorAmount();
            double knockbackResistance = ((ECGauntletItem)stack.getItem()).getMaterial().getKnockbackResistance();
            double toughness = ((ECGauntletItem)stack.getItem()).getMaterial().getArmorToughness();
            if (((ECGauntletItem) stack.getItem()).hasWeaponInHand) {
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", (attackDamage + Math.round(attackDamage / 2.0d * EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack)) + nagaDamage + yetiDamage) / 2d, AttributeModifier.Operation.ADDITION));
            } else {
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", (attackDamage + Math.round(attackDamage / 2.0d * EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack)) + nagaDamage + yetiDamage), AttributeModifier.Operation.ADDITION));
            }
            atts.put(Attributes.ARMOR, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor bonus", armorAmount, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor Toughness bonus", toughness, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ECGauntletItem.KNOCKBACK_RESISTANCE_UUID, "Knockback resistance bonus", (knockbackResistance + EnchantmentHelper.getTagEnchantmentLevel(ECEnchantments.KNOCKBACK_RESISTANCE.get(), stack) / 5.0f), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ECGauntletItem.KNOCKBACK_UUID, "Knockback bonus", EnchantmentHelper.getTagEnchantmentLevel(Enchantments.KNOCKBACK, stack), AttributeModifier.Operation.ADDITION));
        }
        return atts;
    }

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.KNOCKBACK || enchantment == Enchantments.PUNCH_ARROWS) {
            return true;
        }
        return enchantment.category.canEnchant(stack.getItem());
    }
}

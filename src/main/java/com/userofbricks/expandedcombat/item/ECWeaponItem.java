package com.userofbricks.expandedcombat.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ECWeaponItem extends Item implements IVanishable {
    private final IWeaponTier tier;
    private final IWeaponType type;
    private final float attackDamage;
    /** Modifiers applied when the item is in the mainhand of a user. */
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
    protected static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");
    protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("bc644060-615a-4259-a648-5367cd0d45fa");

    public ECWeaponItem(IWeaponTier tierIn, IWeaponType typeIn, Properties builderIn) {
        super(builderIn.defaultMaxDamage(tierIn.getMaxUses()));
        this.tier = tierIn;
        this.type = typeIn;
        this.attackDamage = type.getBaseAttackDamage() + tier.getAttackDamage();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", type.getBaseAttackSpead(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", type.getKnockback(), AttributeModifier.Operation.ADDITION));
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            builder.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", type.getBaseAttackRange() - 3.0d, AttributeModifier.Operation.ADDITION));
        }
        this.attributeModifiers = builder.build();
    }

    public IWeaponTier getTier() {
        return this.tier;
    }
    public IWeaponType getType() {
        return this.type;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public double getAttackRange() {
        return this.getType().getBaseAttackRange();
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.isIn(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(2, entityLiving, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }

        return true;
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return blockIn.isIn(Blocks.COBWEB);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getItemEnchantability() {
        return this.tier.getEnchantability();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return this.tier.getRepairMaterial().test(repair) || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public float getXpRepairRatio(ItemStack stack) { return 2 + type.getTypeMendingBonus() + tier.getMendingBonus(); }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        float shieldMendingBonus = type.getTypeMendingBonus() + tier.getMendingBonus();
        if (shieldMendingBonus != 0.0f) {
            if(shieldMendingBonus > 0.0f){
                list.add(new StringTextComponent(TextFormatting.GREEN + ("Mending Bonus +" + ItemStack.DECIMALFORMAT.format(shieldMendingBonus))));
            }else if (shieldMendingBonus < 0.0f) {
                list.add(new StringTextComponent(TextFormatting.RED + ("Mending Bonus " + ItemStack.DECIMALFORMAT.format(shieldMendingBonus))));
            }
        }
    }

    public static class Dyeable extends ECWeaponItem implements IDyeableArmorItem {
        public Dyeable(IWeaponTier tierIn, IWeaponType typeIn, Properties builderIn) {
            super(tierIn, typeIn, builderIn);
        }
    }

    public static class HasPotion extends ECWeaponItem {
        public HasPotion(IWeaponTier tierIn, IWeaponType typeIn, Properties builderIn) {
            super(tierIn, typeIn, builderIn);
        }

        @Override
        public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (PotionUtils.getPotionFromItem(stack) != Potions.EMPTY) {
                for(EffectInstance effectInstance: PotionUtils.getPotionFromItem(stack).getEffects()) {
                    EffectInstance potionEffect = new EffectInstance(effectInstance.getPotion(), effectInstance.getDuration() / 2);
                    target.addPotionEffect(potionEffect);
                }
            }
            return super.hitEntity(stack, target, attacker);
        }
    }

    public static class HasPotionAndIsDyeable extends HasPotion implements IDyeableArmorItem{
        public HasPotionAndIsDyeable(IWeaponTier tierIn, IWeaponType typeIn, Properties builderIn) {
            super(tierIn, typeIn, builderIn);
        }
    }
}

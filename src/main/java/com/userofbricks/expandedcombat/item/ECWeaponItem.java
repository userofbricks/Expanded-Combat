package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.enchentments.ECEnchantments;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potions;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.*;
import com.userofbricks.expandedcombat.util.CombatEventHandler;
import net.minecraft.util.text.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.util.ITooltipFlag;

import java.util.List;
import net.minecraft.world.IBlockReader;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tags.ITag;
import net.minecraft.tags.BlockTags;
import net.minecraft.block.material.Material;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import java.util.Objects;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.entity.ai.attributes.Attributes;
import com.google.common.collect.ImmutableMultimap;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attribute;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.IVanishable;

public class ECWeaponItem extends SwordItem
{
    private final IWeaponTier weaponTier;
    private final IWeaponType type;
    private final float AttackDamage;
    private Multimap<Attribute, AttributeModifier> attributeModifiers;
    protected static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");
    protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("bc644060-615a-4259-a648-5367cd0d45fa");
    
    public ECWeaponItem( IWeaponTier tierIn,  IWeaponType typeIn,  Item.Properties builderIn) {
        super(new IItemTier() {
            @Override
            public int getUses() {
                return 0;
            }

            @Override
            public float getSpeed() {
                return 0;
            }

            @Override
            public float getAttackDamageBonus() {
                return 0;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return 0;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }
        }, 0, 0, builderIn.defaultDurability(tierIn.getMaxUses()));
        this.weaponTier = tierIn;
        this.type = typeIn;
        this.AttackDamage = this.type.getBaseAttackDamage() + this.weaponTier.getAttackDamage();
        float steeleafKnockback = this.weaponTier == WeaponTier.IRONWOOD ? 1f/5f : 0f;
         ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECWeaponItem.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.AttackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ECWeaponItem.BASE_ATTACK_SPEED_UUID, "Weapon modifier", this.type.getBaseAttackSpead(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ECWeaponItem.ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", this.type.getKnockback() + steeleafKnockback, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }
    
    public static void setAtributeModifierMultimap( ECWeaponItem weaponItem) {
         ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECWeaponItem.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", weaponItem.getDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ECWeaponItem.BASE_ATTACK_SPEED_UUID, "Weapon modifier", weaponItem.getWeaponType().getBaseAttackSpead(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ECWeaponItem.ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", weaponItem.getWeaponType().getKnockback(), AttributeModifier.Operation.ADDITION));
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            builder.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))), new AttributeModifier(ECWeaponItem.ATTACK_REACH_MODIFIER, "Weapon modifier", weaponItem.getAttackRange(), AttributeModifier.Operation.ADDITION));
        }
        else {
            builder.put(AttributeRegistry.ATTACK_REACH.get(), new AttributeModifier(ECWeaponItem.ATTACK_REACH_MODIFIER, "Weapon modifier", weaponItem.getAttackRange(), AttributeModifier.Operation.ADDITION));
        }
        weaponItem.attributeModifiers = builder.build();
    }
    
    public IWeaponTier getWeaponTier() {
        return this.weaponTier;
    }
    
    public IWeaponType getWeaponType() {
        return this.type;
    }

    @Override
    public float getDamage() {
        return this.AttackDamage;
    }
    
    public double getAttackRange() {
        return this.getWeaponType().getBaseAttackRange();
    }

    @Override
    public boolean canAttackBlock( BlockState state,  World worldIn,  BlockPos pos,  PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public float getDestroySpeed( ItemStack stack,  BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0f;
        }
         Material material = state.getMaterial();
        return (material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE) ? 1.0f : 1.5f;
    }

    @Override
    public boolean hurtEnemy( ItemStack weapon,  LivingEntity target,  LivingEntity attacker) {
        boolean result = super.hurtEnemy(weapon, target, attacker);
        if (this.getWeaponTier() == WeaponTier.FIERY) {
            if (result && !target.level.isClientSide && !target.fireImmune()) {
                target.setRemainingFireTicks(15);
            } else {
                for (int var1 = 0; var1 < 20; ++var1) {
                    double px = target.getX() + random.nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                    double py = target.getY() + random.nextFloat() * target.getBbHeight();
                    double pz = target.getZ() + random.nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                    target.level.addParticle(ParticleTypes.FLAME, px, py, pz, 0.02, 0.02, 0.02);
                }
            }
        } else if (this.getWeaponTier() == WeaponTier.KNIGHTLY && target.getArmorValue() > 0) {
            // TODO scale bonus dmg with the amount of armor?
            target.hurt(DamageSource.MAGIC, 2);
            // don't prevent main damage from applying
            target.hurtTime = 0;
            // enchantment attack sparkles
            ((ServerWorld) target.level).getChunkSource().broadcastAndSend(target, new SAnimateHandPacket(target, 5));
        }
        return result;
    }

    @Override
    public boolean mineBlock( ItemStack stack,  World worldIn,  BlockState state,  BlockPos pos,  LivingEntity entityLiving) {
        if (state.getDestroySpeed(worldIn, pos) != 0.0f) {
            stack.hurtAndBreak(2, entityLiving, entity -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        }
        return true;
    }

    @Override
    public boolean isCorrectToolForDrops( BlockState blockIn) {
        return blockIn.is(Blocks.COBWEB);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers( EquipmentSlotType equipmentSlot,  ItemStack stack) {
        return ((equipmentSlot == EquipmentSlotType.MAINHAND || (this.getWeaponType().getWieldingType() == WeaponTypes.WieldingType.DUALWIELD && equipmentSlot == EquipmentSlotType.OFFHAND)) ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot, stack));
    }

    @Override
    public int getEnchantmentValue() {
        return this.weaponTier.getEnchantability();
    }

    @Override
    public boolean isValidRepairItem( ItemStack toRepair,  ItemStack repair) {
        return this.weaponTier.getRepairMaterial().test(repair);
    }
    
    public float getXpRepairRatio( ItemStack stack) {
        return 2.0f + this.type.getTypeMendingBonus() + this.weaponTier.getMendingBonus();
    }
    
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText( ItemStack stack,  World world,  List<ITextComponent> list,  ITooltipFlag flag) {
        if (this.getWeaponTier() == WeaponTier.FIERY) {
            list.add(new TranslationTextComponent("tooltip.expanded_combat.fiery.weapon"));
        } else if (this.getWeaponTier() == WeaponTier.KNIGHTLY) {
            list.add(new TranslationTextComponent("tooltip.expanded_combat.knightly.weapon"));
        }
        float mendingBonus = this.type.getTypeMendingBonus() + this.weaponTier.getMendingBonus();
        if (mendingBonus != 0.0f) {
            if (mendingBonus > 0.0f) {
                list.add(new TranslationTextComponent("tooltip.expanded_combat.mending_bonus").withStyle(TextFormatting.GREEN).append(new StringTextComponent(TextFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(mendingBonus))));
            }
            else if (mendingBonus < 0.0f) {
                list.add(new TranslationTextComponent("tooltip.expanded_combat.mending_bonus").withStyle(TextFormatting.RED).append(new StringTextComponent(TextFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(mendingBonus))));
            }
        }
    }

    public ITextComponent getName(ItemStack stack) {
        return new TranslationTextComponent(this.getWeaponTier().getTierName()).append(" ").append(this.getWeaponType().getTypeName());
    }
    
    public ActionResult<ItemStack> use( World worldIn,  PlayerEntity playerIn,  Hand handIn) {
        if (handIn == Hand.OFF_HAND && worldIn.isClientSide) {
            CombatEventHandler.checkForOffhandAttack();
             ItemStack offhand = playerIn.getItemInHand(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, offhand);
        }
        return new ActionResult<>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
    }

    @Override
    public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> list) {
        if (allowdedIn(tab)) {
            ItemStack istack = new ItemStack(this);
            if (this.getWeaponTier() == WeaponTier.STEELEAF) {
                istack.enchant(Enchantments.MOB_LOOTING, 2);
            }
            list.add(istack);
        }
    }
    
    public static class Dyeable extends ECWeaponItem implements IDyeableArmorItem
    {
        public Dyeable( IWeaponTier tierIn,  IWeaponType typeIn,  Item.Properties builderIn) {
            super(tierIn, typeIn, builderIn);
        }
    }

    public static class HasPotion extends ECWeaponItem
    {
        public HasPotion( IWeaponTier tierIn,  IWeaponType typeIn,  Item.Properties builderIn) {
            super(tierIn, typeIn, builderIn);
        }

        @Override
        public boolean hurtEnemy(ItemStack weapon, LivingEntity target, LivingEntity attacker) {
            if (PotionUtils.getPotion(weapon) != Potions.EMPTY) {
                for ( EffectInstance effectInstance : PotionUtils.getPotion(weapon).getEffects()) {
                     EffectInstance potionEffect = new EffectInstance(effectInstance.getEffect(), effectInstance.getDuration() / 2);
                    target.addEffect(potionEffect);
                }
            }
            return super.hurtEnemy(weapon, target, attacker);
        }
    }
    
    public static class HasPotionAndIsDyeable extends HasPotion implements IDyeableArmorItem
    {
        public HasPotionAndIsDyeable( IWeaponTier tierIn,  IWeaponType typeIn,  Item.Properties builderIn) {
            super(tierIn, typeIn, builderIn);
        }
    }
}

package com.userofbricks.expandedcombat.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import com.userofbricks.expandedcombat.util.CombatEventHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class ECWeaponItem extends SwordItem
{
    private final IWeaponTier weaponTier;
    private final IWeaponType type;
    private final float AttackDamage;
    private Multimap<Attribute, AttributeModifier> attributeModifiers;
    protected static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");
    protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("bc644060-615a-4259-a648-5367cd0d45fa");
    
    public ECWeaponItem( IWeaponTier tierIn,  IWeaponType typeIn,  Item.Properties builderIn) {
        super(new Tier() {
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
    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0f;
        }
         Material material = state.getMaterial();
        return (material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.WATER_PLANT && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE) ? 1.0f : 1.5f;
    }

    @Override
    public boolean hurtEnemy(ItemStack weapon, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(weapon, target, attacker);
        if (this.getWeaponTier() == WeaponTier.FIERY) {
            if (result && !target.level.isClientSide && !target.fireImmune()) {
                target.setRemainingFireTicks(15);
            } else {
                Random random = new Random();
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
            //((ServerLevel) target.level).getChunkSource().broadcastAndSend(target, new SAnimateHandPacket(target, 5));
        }
        return result;
    }

    @Override
    public boolean mineBlock( ItemStack stack,  Level worldIn,  BlockState state,  BlockPos pos,  LivingEntity entityLiving) {
        if (state.getDestroySpeed(worldIn, pos) != 0.0f) {
            stack.hurtAndBreak(2, entityLiving, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public boolean isCorrectToolForDrops( BlockState blockIn) {
        return blockIn.is(Blocks.COBWEB);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers( EquipmentSlot equipmentSlot,  ItemStack stack) {
        return ((equipmentSlot == EquipmentSlot.MAINHAND || (this.getWeaponType().getWieldingType() == WeaponTypes.WieldingType.DUALWIELD && equipmentSlot == EquipmentSlot.OFFHAND)) ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot, stack));
    }

    @Override
    public int getEnchantmentValue() {
        return this.weaponTier.getEnchantability();
    }

    @Override
    public boolean isValidRepairItem( ItemStack toRepair,  ItemStack repair) {
        return this.weaponTier.getRepairMaterial().get().test(repair);
    }
    
    public float getXpRepairRatio( ItemStack stack) {
        return 2.0f + this.type.getTypeMendingBonus() + this.weaponTier.getMendingBonus();
    }
    
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
        if (this.getWeaponTier() == WeaponTier.FIERY) {
            list.add(new TranslatableComponent("tooltip.expanded_combat.fiery.weapon"));
        } else if (this.getWeaponTier() == WeaponTier.KNIGHTLY) {
            list.add(new TranslatableComponent("tooltip.expanded_combat.knightly.weapon"));
        }
        float mendingBonus = this.type.getTypeMendingBonus() + this.weaponTier.getMendingBonus();
        if (mendingBonus != 0.0f) {
            if (mendingBonus > 0.0f) {
                list.add(new TranslatableComponent("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN).append(new TextComponent(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(mendingBonus))));
            }
            else if (mendingBonus < 0.0f) {
                list.add(new TranslatableComponent("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.RED).append(new TextComponent(ChatFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(mendingBonus))));
            }
        }
    }

    public Component getName(ItemStack stack) {
        return new TranslatableComponent(this.getWeaponTier().getTierName()).append(" ").append(this.getWeaponType().getTypeName());
    }
    
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (handIn == InteractionHand.OFF_HAND && worldIn.isClientSide) {
            //todo CombatEventHandler.checkForOffhandAttack();
             ItemStack offhand = playerIn.getItemInHand(handIn);
            return InteractionResultHolder.consume(offhand);
        }
        return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        if (allowdedIn(tab)) {
            ItemStack istack = new ItemStack(this);
            if (this.getWeaponTier() == WeaponTier.STEELEAF) {
                istack.enchant(Enchantments.MOB_LOOTING, 2);
            }
            list.add(istack);
        }
    }
    
    public static class Dyeable extends ECWeaponItem implements DyeableLeatherItem
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
                for ( MobEffectInstance effectInstance : PotionUtils.getPotion(weapon).getEffects()) {
                    MobEffectInstance potionEffect = new MobEffectInstance(effectInstance.getEffect(), effectInstance.getDuration() / 2);
                    target.addEffect(potionEffect);
                }
            }
            return super.hurtEnemy(weapon, target, attacker);
        }
    }
    
    public static class HasPotionAndIsDyeable extends HasPotion implements DyeableLeatherItem
    {
        public HasPotionAndIsDyeable( IWeaponTier tierIn,  IWeaponType typeIn,  Item.Properties builderIn) {
            super(tierIn, typeIn, builderIn);
        }
    }
}

package com.userofbricks.expanded_combat.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.client.renderer.item.ECWeaponBlockEntityWithoutLevelRenderer;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

public class ECWeaponItem extends SwordItem implements ISimpleMaterialItem {
    private final Material material;
    private final WeaponMaterial weapon;
    protected static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");
    protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("bc644060-615a-4259-a648-5367cd0d45fa");

    public ECWeaponItem(Material material, WeaponMaterial weapon, Properties properties) {
        super(new Tier() {
            @Override public int getUses() {return (int) (material.getConfig().durability.toolDurability * weapon.config().durabilityMultiplier);}
            @Override public float getSpeed() {return 0;} //means nothing to weapons
            @Override public float getAttackDamageBonus() {return (float) material.getConfig().offense.addedAttackDamage;}
            @Override public int getLevel() {return 0;} //means nothing to weapons TODO: might want to add this though seems as though other mods use this value for sorting and such
            @Override public int getEnchantmentValue() {return material.getConfig().enchanting.offenseEnchantability;}
            @Override public @NotNull Ingredient getRepairIngredient() {return IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem);}
        }, 3 + weapon.config().baseAttackDamage, weapon.config().attackSpeed, properties);
        this.material = material;
        this.weapon = weapon;
    }

    public Material getMaterial() {
        return this.material;
    }

    public WeaponMaterial getWeapon() {
        return this.weapon;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.getDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", this.weapon.config().attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", this.weapon.config().knockback, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", this.weapon.config().attackRange, AttributeModifier.Operation.ADDITION));
        if (equipmentSlot == EquipmentSlot.MAINHAND) return builder.build();
        else if (this.weapon.config().wieldType == ECConfig.WeaponMaterialConfig.WieldingType.DUALWIELD && equipmentSlot == EquipmentSlot.OFFHAND) return builder.build();
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public float getMendingBonus() {return this.material.getConfig().mendingBonus + this.weapon.config().mendingBonus;}

    public float getXpRepairRatio( ItemStack stack) {
        return 2.0f + getMendingBonus();
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new ECWeaponBlockEntityWithoutLevelRenderer();
            }
        });
    }

    public static class Dyeable extends ECWeaponItem implements DyeableLeatherItem
    {
        public Dyeable(Material material, WeaponMaterial weapon, Item.Properties builderIn) {
            super(material, weapon, builderIn);
        }
    }

    public static class HasPotion extends ECWeaponItem
    {
        public HasPotion(Material material, WeaponMaterial weapon, Item.Properties builderIn) {
            super(material, weapon, builderIn);
        }

        @Override
        public boolean hurtEnemy(@NotNull ItemStack weapon, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
            if (PotionUtils.getPotion(weapon) != Potions.EMPTY) {
                for ( MobEffectInstance effectInstance : PotionUtils.getPotion(weapon).getEffects()) {
                    MobEffectInstance potionEffect = new MobEffectInstance(effectInstance.getEffect(), effectInstance.getDuration() / 2, effectInstance.getAmplifier(), effectInstance.isAmbient(), effectInstance.isVisible());
                    target.addEffect(potionEffect);
                    Collection<MobEffectInstance> collection = PotionUtils.getCustomEffects(weapon);
                    if (!collection.isEmpty()) {
                        for(MobEffectInstance mobeffectinstance : collection) {
                            target.addEffect(new MobEffectInstance(mobeffectinstance));
                        }
                    }
                }
            }
            return super.hurtEnemy(weapon, target, attacker);
        }

        public @NotNull ItemStack getDefaultInstance() {
            return PotionUtils.setPotion(super.getDefaultInstance(), Potions.EMPTY);
        }
    }

    public static class HasPotionAndIsDyeable extends HasPotion implements DyeableLeatherItem
    {
        public HasPotionAndIsDyeable(Material material, WeaponMaterial weapon, Item.Properties builderIn) {
            super(material, weapon, builderIn);
        }
    }
}

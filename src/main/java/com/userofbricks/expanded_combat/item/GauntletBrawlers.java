package com.userofbricks.expanded_combat.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.init.ECAttributes;
import com.userofbricks.expanded_combat.init.ECEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class GauntletBrawlers extends GauntletItem {

    public GauntletBrawlers(Properties properties, Material materialIn, Layer... layers) {
        super(properties, materialIn, layers);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> atts = HashMultimap.create();
        @Nullable LivingEntity entity = slotContext.entity();

        double totalBaseDamage = 3+5+4;
        double totalExtraDamage = getAdditionalDamageAfterEnchantments(totalBaseDamage);

        atts.put(ECAttributes.GAUNTLET_DMG_WITHOUT_WEAPON, new AttributeModifier(uuid, totalBaseDamage + totalExtraDamage, AttributeModifier.Operation.ADD_VALUE));

        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, ((GauntletItem)stack.getItem()).getArmorAmount(), AttributeModifier.Operation.ADD_VALUE));

        double toughness = ((GauntletItem)stack.getItem()).getMaterial().defense().armorToughness();
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, toughness, AttributeModifier.Operation.ADD_VALUE));

        double knockbackResistance = ((GauntletItem)stack.getItem()).getMaterial().defense().knockbackResistance();
        atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, knockbackResistance, AttributeModifier.Operation.ADD_VALUE));

        if (entity != null) {
            Registry<Enchantment> enchantmentRegistry = entity.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
            double totalEnchantedDamage = stack.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(Enchantments.PUNCH)) * 2;

            atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(modLoc("gauntlet_ench_atk_dmg"), totalEnchantedDamage, AttributeModifier.Operation.ADD_VALUE));
            atts.put(ECAttributes.GAUNTLET_DMG_WITHOUT_WEAPON, new AttributeModifier(modLoc("gauntlet_ench_dmg_no_weapon"), totalEnchantedDamage, AttributeModifier.Operation.ADD_VALUE));
            atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(modLoc("gauntlet_ench_knock_resist"), stack.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ECEnchantments.KNOCKBACK_RESISTANCE)) / 10f, AttributeModifier.Operation.ADD_VALUE));
            atts.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(modLoc("gauntlet_ench_knock"), stack.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(Enchantments.KNOCKBACK)), AttributeModifier.Operation.ADD_VALUE));
            if (stack.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ECEnchantments.AGILITY)) > 0) {
                atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(modLoc("gauntlet_ench_atk_speed"), stack.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ECEnchantments.AGILITY)) * 0.02, AttributeModifier.Operation.ADD_VALUE));
            }
        }
        return atts;
    }

    @Override
    public Supplier<ICurioRenderer> getGauntletRenderer() {
        return super.getGauntletRenderer();
    }
}

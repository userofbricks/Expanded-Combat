package com.userofbricks.expanded_combat.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.ECAttributes;
import com.userofbricks.expanded_combat.init.ECEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.registries.DeferredHolder;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.UUID;
import java.util.function.Supplier;

public class GauntletBrawlers extends GauntletItem {

    public GauntletBrawlers(Properties properties, DeferredHolder<Material, Material> materialIn, Layer... layers) {
        super(properties, materialIn, layers);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> atts = HashMultimap.create();

        double totalBaseDamage = 3+5+4;
        double totalExtraDamage = getAdditionalDamageAfterEnchantments(totalBaseDamage);
        double totalEnchantedDamage = stack.getEnchantmentLevel(Enchantments.PUNCH) * 2;

        atts.put(ECAttributes.GAUNTLET_DMG_WITHOUT_WEAPON, new AttributeModifier(uuid, "Attack damage bonus", totalBaseDamage + totalExtraDamage + totalEnchantedDamage, AttributeModifier.Operation.ADD_VALUE));

        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor bonus", ((GauntletItem)stack.getItem()).getArmorAmount(), AttributeModifier.Operation.ADD_VALUE));

        double toughness = ((GauntletItem)stack.getItem()).getMaterial().defense().armorToughness();
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor Toughness bonus", toughness, AttributeModifier.Operation.ADD_VALUE));

        double knockbackResistance = ((GauntletItem)stack.getItem()).getMaterial().defense().knockbackResistance();
        atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Knockback resistance bonus", knockbackResistance + (stack.getEnchantmentLevel(ECEnchantments.KNOCKBACK_RESISTANCE.get()) / 10f), AttributeModifier.Operation.ADD_VALUE));

        atts.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(uuid, "Knockback bonus", stack.getEnchantmentLevel(Enchantments.KNOCKBACK), AttributeModifier.Operation.ADD_VALUE));

        if (stack.getEnchantmentLevel(ECEnchantments.AGILITY.get()) > 0) {
            atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "Agility Attack Speed", stack.getEnchantmentLevel(ECEnchantments.AGILITY.get()) * 0.02, AttributeModifier.Operation.ADD_VALUE));
        }
        return atts;
    }

    @Override
    public Supplier<ICurioRenderer> getGauntletRenderer() {
        return super.getGauntletRenderer();
    }
}

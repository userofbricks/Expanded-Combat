package com.userofbricks.expanded_combat.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.init.ECAttributes;
import com.userofbricks.expanded_combat.init.ECEnchantments;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.UUID;
import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class UniqueStandardGaunlet extends ECGauntletItem{
    public UniqueStandardGaunlet(Properties properties, Material material) {
        super(properties, material);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();

        double totalBaseDamage = 3+5+4;
        float totalExtraDamage = ((UniqueStandardGaunlet)stack.getItem()).getMaterial().getAdditionalDamageAfterEnchantments().apply((float) totalBaseDamage);
        double totalEnchantedDamage = stack.getEnchantmentLevel(Enchantments.PUNCH_ARROWS) * 2;

        atts.put(ECAttributes.GAUNTLET_DMG_WITHOUT_WEAPON.get(), new AttributeModifier(UniqueStandardGaunlet.ATTACK_UUID, "Attack damage bonus", totalBaseDamage + totalExtraDamage + totalEnchantedDamage, AttributeModifier.Operation.ADDITION));

        atts.put(Attributes.ARMOR, new AttributeModifier(UniqueStandardGaunlet.ARMOR_UUID, "Armor bonus", ((UniqueStandardGaunlet)stack.getItem()).getArmorAmount(), AttributeModifier.Operation.ADDITION));

        double toughness = ((UniqueStandardGaunlet)stack.getItem()).getMaterial().getConfig().defense.armorToughness;
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UniqueStandardGaunlet.ARMOR_UUID, "Armor Toughness bonus", toughness, AttributeModifier.Operation.ADDITION));

        double knockbackResistance = ((UniqueStandardGaunlet)stack.getItem()).getMaterial().getConfig().defense.knockbackResistance;
        atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UniqueStandardGaunlet.KNOCKBACK_RESISTANCE_UUID, "Knockback resistance bonus", knockbackResistance + (stack.getEnchantmentLevel(ECEnchantments.KNOCKBACK_RESISTANCE.get()) / 5.0f), AttributeModifier.Operation.ADDITION));

        atts.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(UniqueStandardGaunlet.KNOCKBACK_UUID, "Knockback bonus", stack.getEnchantmentLevel(Enchantments.KNOCKBACK), AttributeModifier.Operation.ADDITION));

        if (stack.getEnchantmentLevel(ECEnchantments.AGILITY.get()) > 0) {
            atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("33dad864-864b-4dbd-acae-88b72cc358cf"), "Agility Attack Speed", stack.getEnchantmentLevel(ECEnchantments.AGILITY.get()) * 0.02, AttributeModifier.Operation.ADDITION));
        }
        return atts;
    }

    @Override
    public Supplier<ICurioRenderer> getGauntletRenderer() {
        return super.getGauntletRenderer();
    }

    @Override
    public ResourceLocation getGauntletTexture(ItemStack stack) {
        return modLoc("textures/model/gauntlet/gauntlet.png");
    }
}

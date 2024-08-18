package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.GripType;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ElementalWeapon extends ECWeaponItem {
    public final Holder<Attribute> damageAttributeRegistryObject;

    public ElementalWeapon(DeferredHolder<Material, Material> material, DeferredHolder<WeaponType, WeaponType> weapon, Properties properties, int addedDmg, Holder<Attribute> damageAttributeRegistryObject) {
        super(material, weapon, properties, addedDmg);
        this.damageAttributeRegistryObject = damageAttributeRegistryObject;
    }

    public double getDamage() {
        return getMaterial().offense().addedAttackDamage() + getWeapon().baseAttackDamage();
    }

    public ItemAttributeModifiers getAttributeModifiers() {
        EquipmentSlotGroup slotGroup = getWeapon().gripType() == GripType.DUALWIELD ? EquipmentSlotGroup.HAND : EquipmentSlotGroup.MAINHAND;

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 3 + this.addedDmg, AttributeModifier.Operation.ADD_VALUE), slotGroup);
        builder.add(damageAttributeRegistryObject, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.getDamage(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getWeapon().attackSpeed(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        builder.add(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", getWeapon().knockback(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", getWeapon().attackRange(), AttributeModifier.Operation.ADD_VALUE), slotGroup);

        return builder.build();
    }

}

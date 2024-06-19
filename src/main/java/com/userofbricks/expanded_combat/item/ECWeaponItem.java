package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.GripType;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.UUID;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECWeaponItem extends Item implements IMaterialItem {
    public final Holder.Reference<Material> material;
    public final Holder.Reference<WeaponType> weapon;
    public final int addedDmg;
    protected static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");
    protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("bc644060-615a-4259-a648-5367cd0d45fa");

    public ECWeaponItem(Holder.Reference<Material> material, Holder.Reference<WeaponType> weapon, Properties properties) {
        this(material, weapon, properties, 0);
    }

    public ECWeaponItem(Holder.Reference<Material> material, Holder.Reference<WeaponType> weapon, Properties properties, int addedDmg) {
        super(properties.stacksTo(1).component(DataComponents.DAMAGE, 0).component(DataComponents.TOOL, createToolProperties()));
        this.material = material;
        this.weapon = weapon;
        this.addedDmg = addedDmg;
    }

    protected DataComponentMap.Builder componentBuilder() {
        DataComponentMap.Builder components = DataComponentMap.builder().addAll(super.components());

        components.set(DataComponents.MAX_DAMAGE, (int)((material.isBound() ? getMaterial().durabilities().toolBaseDurability() : 10) * (weapon.isBound() ? getWeapon().durabilityMultiplier() : 1)))
                .set(DataComponents.MAX_STACK_SIZE, 1);
        components.set(DataComponents.ATTRIBUTE_MODIFIERS, getAttributeModifiers());
        if (getMaterial().defense().fireResistant()) components.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE);

        return components;
    }
    public DataComponentMap components() {
        return Item.Properties.validateComponents(componentBuilder().build());
    }

    private static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F), Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }

    public Material getMaterial() {
        return this.material.value();
    }

    public WeaponType getWeapon() {
        return this.weapon.value();
    }

    //TODO: make offhand get checked for action when main hand is in cool down if dual wield and make dmg get lowered if holding something in offhand it two handed
    public ItemAttributeModifiers getAttributeModifiers() {
        EquipmentSlotGroup slotGroup = getWeapon().gripType() == GripType.DUALWIELD ? EquipmentSlotGroup.HAND : EquipmentSlotGroup.MAINHAND;

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getDamage(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getWeapon().attackSpeed(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        builder.add(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", getWeapon().knockback(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", getWeapon().attackRange(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        return builder.build();
    }

    public double getDamage() {
        return 3 + getMaterial().offense().addedAttackDamage() + getWeapon().baseAttackDamage() + addedDmg;
    }

    public float getMendingBonus() {return getMaterial().enchantingRelated().mendingBonus() + getWeapon().mendingBonus();}

    @Override
    public boolean hurtEnemy(ItemStack weapon, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(weapon, target, attacker);
        weapon.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

}

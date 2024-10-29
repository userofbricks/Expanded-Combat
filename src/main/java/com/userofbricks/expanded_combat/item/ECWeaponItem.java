package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.weapon_type.GripType;
import com.userofbricks.expanded_combat.api.weapon_type.WeaponType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.DamageResistant;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECWeaponItem extends Item implements IMaterialItem {
    public final Material material;
    public final WeaponType weapon;
    public final int addedDmg;
    protected static final ResourceLocation ATTACK_KNOCKBACK_MODIFIER = modLoc("base_attack_knockback");
    protected static final ResourceLocation ATTACK_REACH_MODIFIER = modLoc("base_attack_reach");

    public ECWeaponItem(Material material, WeaponType weapon, Properties properties) {
        this(material, weapon, properties, 0);
    }

    public ECWeaponItem(Material material, WeaponType weapon, Properties properties, int addedDmg) {
        super(properties.stacksTo(1)
                .component(DataComponents.DAMAGE, 0)
                .component(DataComponents.TOOL, createToolProperties())
                .enchantable(material.enchanting().offenseEnchantability())
        );
        this.material = material;
        this.weapon = weapon;
        this.addedDmg = addedDmg;
    }

    //TODO: see if works in constructor, if not move to the event implementation
    //sadly can't put this in the constructors due to materials technically being loaded after items. although with cloth config this might not be true anymore.
    protected DataComponentMap.Builder componentBuilder() {
        DataComponentMap.Builder components = DataComponentMap.builder().addAll(super.components());

        components.set(DataComponents.MAX_DAMAGE, (int)(getMaterial().durability().toolBaseDurability() * getWeapon().config().durabilityMultiplier()))
                .set(DataComponents.MAX_STACK_SIZE, 1);
        components.set(DataComponents.ATTRIBUTE_MODIFIERS, getAttributeModifiers());
        if (getMaterial().defense().fireResistant()) components.set(DataComponents.DAMAGE_RESISTANT, new DamageResistant(DamageTypeTags.IS_FIRE));

        return components;
    }
    public DataComponentMap components() {
        return Item.Properties.validateComponents(componentBuilder().build());
    }

    //copy the tool properties for swords from ToolMaterial class
    private static Tool createToolProperties() {
        HolderGetter<Block> holdergetter = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return new Tool(
                List.of(
                        Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
                        Tool.Rule.overrideSpeed(holdergetter.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
                ),
                1.0F,
                2
        );
    }

    public Material getMaterial() {
        return material;
    }

    public WeaponType getWeapon() {
        return weapon;
    }

    //TODO: make offhand get checked for action when main hand is in cool down if dual wield and make dmg get lowered if holding something in offhand it two handed
    public ItemAttributeModifiers getAttributeModifiers() {
        EquipmentSlotGroup slotGroup = getWeapon().config().gripType() == GripType.DUALWIELD ? EquipmentSlotGroup.HAND : EquipmentSlotGroup.MAINHAND;

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, getDamage(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, getWeapon().config().attackSpeed(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        builder.add(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, getWeapon().config().knockback(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ATTACK_REACH_MODIFIER, getWeapon().config().attackRange(), AttributeModifier.Operation.ADD_VALUE), slotGroup);
        return builder.build();
    }

    public double getDamage() {
        return 3 + getMaterial().offense().addedAttackDamage() + getWeapon().config().baseAttackDamage() + addedDmg;
    }

    public float getMendingBonus() {
        return getMaterial().enchanting().mendingBonus() + getWeapon().config().mendingBonus();
    }

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
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }

}

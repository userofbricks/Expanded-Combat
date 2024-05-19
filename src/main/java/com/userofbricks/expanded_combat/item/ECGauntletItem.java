package com.userofbricks.expanded_combat.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.client.renderer.GauntletRenderer;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.ECAttributes;
import com.userofbricks.expanded_combat.init.ECEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class ECGauntletItem extends Item implements ICurioItem, ISimpleMaterialItem
{
    public final Layer[] GAUNTLET_TEXTURE_LAYERS;
    private final Holder.Reference<Material> material;
    protected static final UUID ATTACK_UUID = UUID.fromString("7ce10414-adcc-4bf2-8804-f5dbd39fadaf");
    protected static final UUID ARMOR_UUID = UUID.fromString("38faf191-bf78-4654-b349-cc1f4f1143bf");
    protected static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.fromString("b64fd3d6-a9fe-46a1-a972-90e4b0849678");
    protected static final UUID KNOCKBACK_UUID = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");

    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected @NotNull ItemStack execute(@NotNull BlockSource blockSource, @NotNull ItemStack itemStack) {
            return ECGauntletItem.dispenseGauntlet(blockSource, itemStack) ? itemStack : super.execute(blockSource, itemStack);
        }
    };

    //TODO: need to change the entity selector to use some sort of curios check for the gauntlet slot existing on the entity
    public static boolean dispenseGauntlet(BlockSource blockSource, ItemStack stack) {
        BlockPos blockpos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.level()
                .getEntitiesOfClass(
                        LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(stack))
                );
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingentity = list.get(0);
            Optional<SlotResult> optionalSlotResult = CuriosApi.getCuriosInventory(livingentity).flatMap(curiosInventory -> curiosInventory.findCurio(ExpandedCombat.GAUNTLET_CURIOS_IDENTIFIER, 0));

            if (optionalSlotResult.isPresent() && !optionalSlotResult.get().stack().isEmpty()) return false;

            ItemStack itemstack = stack.split(1);
            CuriosApi.getCuriosInventory(livingentity).get().setEquippedCurio(ExpandedCombat.GAUNTLET_CURIOS_IDENTIFIER, 0, itemstack);

            if (livingentity instanceof Mob) {
                ((Mob)livingentity).setPersistenceRequired();
            }

            return true;
        }
    }

    public ECGauntletItem(Properties properties, Holder.Reference<Material> materialIn, Layer... layers) {
        super(
                properties.durability(materialIn.value().durabilities().gauntletDurability())
        );
        this.material = materialIn;
        this.GAUNTLET_TEXTURE_LAYERS = layers;
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
    }
    public Material getMaterial() {
        return this.material.value();
    }
    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return (getMaterial().enchantingRelated().offenseEnchantability()/2) + (getMaterial().enchantingRelated().defenseEnchantability()/2);
    }
    @Override
    public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
        return getMaterial().repairItem().test(repair) || super.isValidRepairItem(toRepair, repair);
    }
    @Override
    public float getMendingBonus() {
        return getMaterial().enchantingRelated().mendingBonus();
    }
    @Override
    public float getXpRepairRatio( ItemStack stack) {
        return 2f + getMendingBonus();
    }
    public int getArmorAmount() {
        return getMaterial().defense().gauntletArmorAmount();
    }
    public double getAttackDamage() {
        return getMaterial().offense().addedAttackDamage();
    }
    public boolean hasEmissiveTexture(ItemStack stack) {
        return false;
    }
    public ResourceLocation getEmissiveTexture(ItemStack stack) {
        return new ResourceLocation("null");
    }
    public Supplier<ICurioRenderer> getGauntletRenderer() {
        return GauntletRenderer::new;
    }
    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return ((ECGauntletItem) stack.getItem()).getMaterial().defense().makesPiglinsNeutral();
    }
    @NotNull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        ECGauntletItem gauntletItem = (ECGauntletItem) stack.getItem();
        return new ICurio.SoundInfo(BuiltInRegistries.SOUND_EVENT.get(gauntletItem.getMaterial().defense().equipSound()), 1.0f, 1.0f);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> atts = HashMultimap.create();

        double totalBaseDamage = Math.max(((ECGauntletItem)stack.getItem()).getAttackDamage(), 0.5);
        double totalExtraDamage = getAdditionalDamageAfterEnchantments(totalBaseDamage);
        double totalEnchantedDamage = stack.getEnchantmentLevel(Enchantments.PUNCH);

        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", (totalBaseDamage + totalEnchantedDamage + totalExtraDamage)/2.0d, AttributeModifier.Operation.ADD_VALUE));
        atts.put(ECAttributes.GAUNTLET_DMG_WITHOUT_WEAPON, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", ((totalBaseDamage + totalExtraDamage)/2.0d) + totalEnchantedDamage, AttributeModifier.Operation.ADD_VALUE));

        atts.put(Attributes.ARMOR, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor bonus", ((ECGauntletItem)stack.getItem()).getArmorAmount(), AttributeModifier.Operation.ADD_VALUE));

        double toughness = ((ECGauntletItem)stack.getItem()).getMaterial().defense().armorToughness();
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor Toughness bonus", toughness, AttributeModifier.Operation.ADD_VALUE));

        double knockbackResistance = ((ECGauntletItem)stack.getItem()).getMaterial().defense().knockbackResistance();
        atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ECGauntletItem.KNOCKBACK_RESISTANCE_UUID, "Knockback resistance bonus", knockbackResistance + (stack.getEnchantmentLevel(ECEnchantments.KNOCKBACK_RESISTANCE.get()) / 10f), AttributeModifier.Operation.ADD_VALUE));

        atts.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ECGauntletItem.KNOCKBACK_UUID, "Knockback bonus", stack.getEnchantmentLevel(Enchantments.KNOCKBACK), AttributeModifier.Operation.ADD_VALUE));

        if (stack.getEnchantmentLevel(ECEnchantments.AGILITY.get()) > 0) {
            atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("33dad864-864b-4dbd-acae-88b72cc358cf"), "Agility Attack Speed", stack.getEnchantmentLevel(ECEnchantments.AGILITY.get()) * 0.02, AttributeModifier.Operation.ADD_VALUE));
        }
        return atts;
    }

    private double getAdditionalDamageAfterEnchantments(double totalBaseDamage) {
        return 0;
    }

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    //TODO Move to tag system
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.KNOCKBACK || enchantment == Enchantments.PUNCH) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack,enchantment);
    }

    public static final class Layer {
        private final String suffix;
        private final boolean dyeable;
        private final Function<ResourceLocation, ResourceLocation> texture;

        public Layer(String suffix, boolean pDyeable) {
            this.suffix = suffix;
            this.dyeable = pDyeable;
            this.texture = this.resolveTexture();
        }

        public Layer(String suffix) {
            this(suffix, false);
        }
        public Layer() {
            this("", false);
        }

        private Function<ResourceLocation, ResourceLocation> resolveTexture() {
            return assetName -> assetName.withPath(p_324187_ -> "textures/models/gauntlet/" + assetName.getPath() + "_" + suffix + ".png");
        }

        public ResourceLocation texture(ResourceLocation material) {
            return texture.apply(material);
        }

        public boolean dyeable() {
            return this.dyeable;
        }
    }
}

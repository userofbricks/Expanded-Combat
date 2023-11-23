package com.userofbricks.expanded_combat.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.client.renderer.GauntletRenderer;
import com.userofbricks.expanded_combat.init.ECEnchantments;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import com.userofbricks.expanded_combat.init.LangStrings;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class ECGauntletItem extends Item implements ICurioItem, ISimpleMaterialItem
{
    private final ResourceLocation GAUNTLET_TEXTURE;
    private final Material material;
    private static final UUID ATTACK_UUID = UUID.fromString("7ce10414-adcc-4bf2-8804-f5dbd39fadaf");
    private static final UUID ARMOR_UUID = UUID.fromString("38faf191-bf78-4654-b349-cc1f4f1143bf");
    private static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.fromString("b64fd3d6-a9fe-46a1-a972-90e4b0849678");
    private static final UUID KNOCKBACK_UUID = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");

    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected @NotNull ItemStack execute(@NotNull BlockSource blockSource, @NotNull ItemStack itemStack) {
            return ECGauntletItem.dispenseGauntlet(blockSource, itemStack) ? itemStack : super.execute(blockSource, itemStack);
        }
    };

    public static boolean dispenseGauntlet(BlockSource blockSource, ItemStack stack) {
        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(stack)));
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingentity = list.get(0);
            Optional<SlotResult> optionalSlotResult = CuriosApi.getCuriosHelper().findCurio(livingentity, ExpandedCombat.GAUNTLET_CURIOS_IDENTIFIER, 0);

            if (optionalSlotResult.isPresent() && !optionalSlotResult.get().stack().isEmpty()) return false;

            ItemStack itemstack = stack.split(1);
            CuriosApi.getCuriosHelper().setEquippedCurio(livingentity, ExpandedCombat.GAUNTLET_CURIOS_IDENTIFIER, 0, itemstack);

            if (livingentity instanceof Mob) {
                ((Mob)livingentity).setPersistenceRequired();
            }

            return true;
        }
    }


    @ParametersAreNonnullByDefault
    public ECGauntletItem(Properties properties, Material materialIn) {
        super(properties);
        this.material = materialIn;
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
        this.GAUNTLET_TEXTURE = new ResourceLocation(ExpandedCombat.MODID, "textures/entity/gauntlet/" + materialIn.getLocationName().getPath() + ".png");
    }
    
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return (this.material.getConfig().enchanting.offenseEnchantability/2) + (this.material.getConfig().enchanting.defenseEnchantability/2);
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
        return IngredientUtil.getIngrediantFromItemString(this.material.getConfig().crafting.repairItem).test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.material.getConfig().durability.toolDurability;
    }

    @Override
    public boolean canBeDepleted() {
        return true;
    }

    @Override
    public boolean isFireResistant() {
        return this.material.getConfig().fireResistant;
    }

    @Override
    public boolean canBeHurtBy(@NotNull DamageSource damageSource) {
        return !this.material.getConfig().fireResistant || !damageSource.is(DamageTypeTags.IS_FIRE);
    }

    @Override
    public float getMendingBonus() {
        return material.getConfig().mendingBonus;
    }

    @Override
    public float getXpRepairRatio( ItemStack stack) {
        return 2f + getMendingBonus();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        if (getAttackDamage() != 0){
            list.add(Component.translatable(LangStrings.WHEN_HANDS_EMPTY).withStyle(ChatFormatting.GOLD));
            list.add(
                    Component.literal((getAttackDamage() > 0 ? ChatFormatting.BLUE + " +" : ChatFormatting.RED + " ") +
                                    ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.getAttackDamage() / 2) + " ")
                            .append(Component.translatable(Attributes.ATTACK_DAMAGE.getDescriptionId()).withStyle(getAttackDamage() > 0 ? ChatFormatting.BLUE : ChatFormatting.RED))
            );
            list.add(Component.empty());
        }
    }
    
    public int getArmorAmount() {
        return this.material.getConfig().defense.gauntletArmorAmount;
    }
    
    public double getAttackDamage() {
        return this.material.getConfig().offense.addedAttackDamage;
    }

    @Deprecated(since = "2.8.1", forRemoval = true)
    public ResourceLocation getGAUNTLET_TEXTURE() {
        return this.GAUNTLET_TEXTURE;
    }
    public ResourceLocation getGAUNTLET_TEXTURE(ItemStack stack) {
        return this.getGAUNTLET_TEXTURE();
    }
    public Supplier<ICurioRenderer> getGauntletRenderer() {
        return GauntletRenderer::new;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return ((ECGauntletItem) stack.getItem()).getMaterial().getName().equals("Gold");
    }

    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        livingEntity.level().playSound(null, livingEntity.blockPosition(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(this.material.getConfig().equipSound))), SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        String identifier = slotContext.identifier();
        Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();
        if (CuriosApi.getCuriosHelper().getCurioTags(stack.getItem()).contains(identifier) && stack.getItem() instanceof ECGauntletItem) {
            double attackDamage = Math.max(((ECGauntletItem)stack.getItem()).getAttackDamage(), 0.5);
            float extraDamage = ((ECGauntletItem)stack.getItem()).getMaterial().getAdditionalDamageAfterEnchantments().apply((float) attackDamage);
            int armorAmount = ((ECGauntletItem)stack.getItem()).getArmorAmount();
            double knockbackResistance = ((ECGauntletItem)stack.getItem()).getMaterial().getConfig().defense.knockbackResistance;
            double toughness = ((ECGauntletItem)stack.getItem()).getMaterial().getConfig().defense.armorToughness;
            atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", ((attackDamage / 2.0d) + Math.round(attackDamage / 2.0d * EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack)) + extraDamage)/2, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor bonus", armorAmount, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor Toughness bonus", toughness, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ECGauntletItem.KNOCKBACK_RESISTANCE_UUID, "Knockback resistance bonus", (knockbackResistance + EnchantmentHelper.getTagEnchantmentLevel(ECEnchantments.KNOCKBACK_RESISTANCE.get(), stack) / 5.0f), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ECGauntletItem.KNOCKBACK_UUID, "Knockback bonus", EnchantmentHelper.getTagEnchantmentLevel(Enchantments.KNOCKBACK, stack), AttributeModifier.Operation.ADDITION));
            if (stack.getEnchantmentLevel(ECEnchantments.AGILITY.get()) > 0) {
                atts.put(Attributes.ATTACK_SPEED, new AttributeModifier("Agility Attack Speed", stack.getEnchantmentLevel(ECEnchantments.AGILITY.get()) * 0.02, AttributeModifier.Operation.ADDITION));
            }
        }
        return atts;
    }

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.KNOCKBACK || enchantment == Enchantments.PUNCH_ARROWS) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack,enchantment);
    }

    public static class Dyeable extends ECGauntletItem implements DyeableLeatherItem {
        private final ResourceLocation GAUNTLET_TEXTURE_OVERLAY;

        public Dyeable(Properties properties, Material materialIn) {
            super(properties, materialIn);
            this.GAUNTLET_TEXTURE_OVERLAY = new ResourceLocation("expanded_combat", "textures/entity/gauntlet/" + materialIn.getLocationName().getPath() + "_overlay" + ".png");
        }

        @Deprecated(since = "2.8.1", forRemoval = true)
        public ResourceLocation getGAUNTLET_TEXTURE_OVERLAY() {
            return GAUNTLET_TEXTURE_OVERLAY;
        }

        public ResourceLocation getGAUNTLET_TEXTURE_OVERLAY(ItemStack stack) {
            return this.getGAUNTLET_TEXTURE_OVERLAY();
        }
    }
}

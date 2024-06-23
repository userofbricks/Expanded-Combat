package com.userofbricks.expanded_combat.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.client.renderer.GauntletRenderer;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.ECAttributes;
import com.userofbricks.expanded_combat.init.ECEnchantments;
import com.userofbricks.expanded_combat.init.Materials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
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


public class GauntletItem extends Item implements ICurioItem, IMaterialItem
{
    public final Layer[] GAUNTLET_TEXTURE_LAYERS;
    public final Holder.Reference<Material> material;

    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected @NotNull ItemStack execute(@NotNull BlockSource blockSource, @NotNull ItemStack itemStack) {
            return GauntletItem.dispenseGauntlet(blockSource, itemStack) ? itemStack : super.execute(blockSource, itemStack);
        }
    };

    public static boolean dispenseGauntlet(BlockSource blockSource, ItemStack stack) {
        BlockPos blockpos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.level()
                .getEntitiesOfClass(
                        LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS.and(entity -> {
                            if(entity instanceof LivingEntity livingEntity) {
                                return CuriosApi.getEntitySlots(livingEntity).containsKey(ExpandedCombat.GAUNTLET_CURIOS_IDENTIFIER);
                            }
                            return false;
                        })
                );
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingentity = list.getFirst();
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

    public GauntletItem(Properties properties, Holder.Reference<Material> materialIn, Layer... layers) {
        super(properties);
        this.material = materialIn;
        this.GAUNTLET_TEXTURE_LAYERS = layers;
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
    }
    public @NotNull DataComponentMap components() {
        DataComponentMap.Builder components = DataComponentMap.builder().addAll(super.components());

        components.set(DataComponents.MAX_DAMAGE, getMaterial().durabilities().gauntletDurability())
                .set(DataComponents.MAX_STACK_SIZE, 1);
        if (getMaterial().defense().fireResistant()) components.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE);

        return Item.Properties.validateComponents(components.build());
    }
    public Material getMaterial() {
        return this.material.isBound() ? material.value() : Materials.NOTBOUNDBACKUP;
    }
    @Override
    @ParametersAreNonnullByDefault
    public int getEnchantmentValue(ItemStack stack) {
        return (getMaterial().enchantingRelated().offenseEnchantability()/2) + (getMaterial().enchantingRelated().defenseEnchantability()/2);
    }

    @Override
    public Holder.Reference<Material> getMaterialReference() {
        return material;
    }

    public int getArmorAmount() {
        return getMaterial().defense().gauntletArmorAmount();
    }
    public double getAttackDamage() {
        return getMaterial().offense().addedAttackDamage();
    }
    public Supplier<ICurioRenderer> getGauntletRenderer() {
        return GauntletRenderer::new;
    }
    @NotNull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        GauntletItem gauntletItem = (GauntletItem) stack.getItem();
        return new ICurio.SoundInfo(BuiltInRegistries.SOUND_EVENT.get(gauntletItem.getMaterial().defense().equipSound()), 1.0f, 1.0f);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> atts = HashMultimap.create();

        double totalBaseDamage = Math.max(((GauntletItem)stack.getItem()).getAttackDamage(), 0.5);
        double totalExtraDamage = getAdditionalDamageAfterEnchantments(totalBaseDamage);
        double totalEnchantedDamage = stack.getEnchantmentLevel(Enchantments.PUNCH);

        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "Attack damage bonus", (totalBaseDamage + totalEnchantedDamage + totalExtraDamage)/2.0d, AttributeModifier.Operation.ADD_VALUE));
        atts.put(ECAttributes.GAUNTLET_DMG_WITHOUT_WEAPON, new AttributeModifier(uuid, "Attack damage bonus", ((totalBaseDamage + totalExtraDamage)/2.0d) + totalEnchantedDamage, AttributeModifier.Operation.ADD_VALUE));

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

    public double getAdditionalDamageAfterEnchantments(double totalBaseDamage) {
        return 0;
    }

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    //TODO Move to tag system
    @Override
    @ParametersAreNonnullByDefault
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
        public Layer(ResourceLocation relativeTexture, boolean pDyeable){
            this.suffix = "";
            this.dyeable = pDyeable;
            this.texture = assetName -> relativeTexture.withPath(p_324187_ -> "textures/models/gauntlet/" + relativeTexture.getPath() + ".png");
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

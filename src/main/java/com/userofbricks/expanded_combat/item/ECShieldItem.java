package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.registry.ShieldMaterialUseTick;
import com.userofbricks.expanded_combat.client.renderer.item.ECShieldBlockEntityWithoutLevelRenderer;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.PluginInit;
import com.userofbricks.expanded_combat.datagen.LangStrings;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.userofbricks.expanded_combat.init.ItemDataComponents.SHIELD_MATERIALS;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECShieldItem extends ShieldItem {
    public ECShieldItem(Item.Properties properties) {
        super(properties.stacksTo(1).component(SHIELD_MATERIALS, ShieldMaterials.DEFAULT));
    }

    /**
     * combines the added material durability from each material with the base shield durability
     * @param stack the stack to find the durability for
     * @return the amount of durability the item should have at max
     */
    @Override
    public int getMaxDamage(ItemStack stack) {
        int durability = 336;
        int ul = getUpperLeftMaterial(stack).value().durabilities().addedShieldDurability();
        int ur = getUpperRightMaterial(stack).value().durabilities().addedShieldDurability();
        int dl = getDownLeftMaterial(stack).value().durabilities().addedShieldDurability();
        int dr = getDownRightMaterial(stack).value().durabilities().addedShieldDurability();
        int m = getMiddleMaterial(stack).value().durabilities().addedShieldDurability();
        return durability + ul + ur + dl + dr + m;
    }

    public boolean canBeDepleted() {
        return true;
    }

    /**
     * finds what is the most common material in the shield and tests that material like normal
     * @param toRepair the item to repair
     * @param repair the material being repaired with
     * @return weather the repair material is the correct type
     */
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        if (repair.getItem() instanceof EnchantedBookItem) return  false;
        Material ul = getUpperLeftMaterial(toRepair).value();
        Material ur = getUpperRightMaterial(toRepair).value();
        Material dl = getDownLeftMaterial(toRepair).value();
        Material dr = getDownRightMaterial(toRepair).value();
        Material m = getMiddleMaterial(toRepair).value();
        ShieldMaterials shieldMaterials = toRepair.getOrDefault(SHIELD_MATERIALS, ShieldMaterials.DEFAULT);
        int last = shieldMaterials.lastRepairNumber() + 1;
        if (last >= 5) last = 0;
        List<Material> slotMaterials = Arrays.asList(ul, ur, dl, dr, m);
        Material currentSlotMaterial = slotMaterials.get(last);

        toRepair.set(SHIELD_MATERIALS, shieldMaterials.updateLastRepair(last));
        Ingredient ingredient = currentSlotMaterial.repairItem();
        return !ingredient.isEmpty() && ingredient.test(repair);
    }

    /**
     * increases the mending amount per xp based on the materials used.
     * @param stack the stack that we want the mendiong bonus for.
     * @return the mending amount per xp.
     */
    @Override
    public float getXpRepairRatio(ItemStack stack) {
        return 2.0f + getMendingBonus(stack);
    }

    /**
     * gets all five material mending increases or decreases per fifth and adds them together
     * @param stack the stack that we want the mendiong bonus for.
     * @return the mending bonus.
     */
    public float getMendingBonus(ItemStack stack) {
        float ul = getUpperLeftMaterial(stack).value().enchantingRelated().mendingBonus()/5;
        float ur = getUpperRightMaterial(stack).value().enchantingRelated().mendingBonus()/5;
        float dl = getDownLeftMaterial(stack).value().enchantingRelated().mendingBonus()/5;
        float dr = getDownRightMaterial(stack).value().enchantingRelated().mendingBonus()/5;
        float m = getMiddleMaterial(stack).value().enchantingRelated().mendingBonus()/5;
        return ul + ur + dl + dr + m;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext pContext, List<Component> list, TooltipFlag flag) {
        stack.addToTooltip(SHIELD_MATERIALS, pContext, list::add, flag);

        if (getMendingBonus(stack) != 0.0f) {
            if (getMendingBonus(stack) > 0.0f) {
                list.add(1, Component.translatable(LangStrings.GOLD_MENDING_TOOLTIP).withStyle(ChatFormatting.BLUE).append(Component.literal(ChatFormatting.BLUE + " " + ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(getMendingBonus(stack)))));
            }
            else if (getMendingBonus(stack) < 0.0f) {
                list.add(1, Component.translatable(LangStrings.GOLD_MENDING_TOOLTIP).withStyle(ChatFormatting.RED).append(Component.literal(ChatFormatting.RED + " " + ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(getMendingBonus(stack)))));
            }
        }
        super.appendHoverText(stack, pContext, list, flag);
    }

    public static double getBaseProtection(ItemStack stack) {
        double ul = getUpperLeftMaterial(stack).value().defense().baseProtectionAmmount() /5;
        double ur = getUpperRightMaterial(stack).value().defense().baseProtectionAmmount() /5;
        double dl = getDownLeftMaterial(stack).value().defense().baseProtectionAmmount() /5;
        double dr = getDownRightMaterial(stack).value().defense().baseProtectionAmmount() /5;
        double m = getMiddleMaterial(stack).value().defense().baseProtectionAmmount() /5;
        return ul + ur + dl + dr + m;
    }

    public static double getPercentageProtection(ItemStack stack) {
        double ul = getUpperLeftMaterial(stack).value().defense().afterBasePercentReduction() /5;
        double ur = getUpperRightMaterial(stack).value().defense().afterBasePercentReduction() /5;
        double dl = getDownLeftMaterial(stack).value().defense().afterBasePercentReduction() /5;
        double dr = getDownRightMaterial(stack).value().defense().afterBasePercentReduction() /5;
        double m = getMiddleMaterial(stack).value().defense().afterBasePercentReduction() /5;
        return ul + ur + dl + dr + m;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new ECShieldBlockEntityWithoutLevelRenderer();
            }
        });
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.BINDING_CURSE) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack,enchantment);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int timeUsing) {
        super.onUseTick(level, livingEntity, itemStack, timeUsing);
        Map<Holder<Material>, Integer> materials = new HashMap<>();
        for (Holder<Material> material : Arrays.asList(getUpperLeftMaterial(itemStack), getUpperRightMaterial(itemStack), getMiddleMaterial(itemStack),
                getDownLeftMaterial(itemStack), getDownRightMaterial(itemStack)))
        {
            if (materials.containsKey(material)) materials.replace(material, materials.get(material) + 1);
            else materials.put(material, 1);
        }
        for (Map.Entry<Holder<Material>, Integer> material : materials.entrySet()) {
            ShieldMaterialUseTick useTick = PluginInit.getShieldUseTickEntry(material.getKey());
            if (useTick != null) useTick.onUseTick().apply(level, livingEntity, itemStack, timeUsing, material.getValue());
        }
    }

    public static Holder<Material> getUpperLeftMaterial(ItemStack stack) {
        return stack.getOrDefault(SHIELD_MATERIALS, ShieldMaterials.DEFAULT).ULMaterial();
    }

    public static Holder<Material> getUpperRightMaterial(ItemStack stack) {
        return stack.getOrDefault(SHIELD_MATERIALS, ShieldMaterials.DEFAULT).URMaterial();
    }

    public static Holder<Material> getDownLeftMaterial(ItemStack stack) {
        return stack.getOrDefault(SHIELD_MATERIALS, ShieldMaterials.DEFAULT).DLMaterial();
    }

    public static Holder<Material> getDownRightMaterial(ItemStack stack) {
        return stack.getOrDefault(SHIELD_MATERIALS, ShieldMaterials.DEFAULT).DRMaterial();
    }

    public static Holder<Material> getMiddleMaterial(ItemStack stack) {
        return stack.getOrDefault(SHIELD_MATERIALS, ShieldMaterials.DEFAULT).MMaterial();
    }

    public static ItemStack makeShieldBeMaterial(ItemStack stack, Holder<Material> material) {
        stack.set(SHIELD_MATERIALS, new ShieldMaterials(material, material, material, material, material, 0));
        return stack;
    }
}

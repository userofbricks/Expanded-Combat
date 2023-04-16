package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.client.renderer.item.ECShieldBlockEntityWithoutLevelRenderer;
import com.userofbricks.expanded_combat.item.materials.ShieldMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ECShieldItem extends ShieldItem {

    public static final String ULMaterialTagName = "UL_Material";
    public static final String URMaterialTagName = "UR_Material";
    public static final String DLMaterialTagName = "DL_Material";
    public static final String DRMaterialTagName = "DR_Material";
    public static final String MMaterialTagName = "M_Material";
    public static final String LastRepairNumber = "Last_Repair_Number";

    public ECShieldItem(boolean fireresistant, Item.Properties properties) {
        super(fireresistant ? properties.fireResistant().stacksTo(1) : properties.stacksTo(1));
    }

    @Nonnull
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.getOrCreateTag().putString(ULMaterialTagName, "empty");
        stack.getOrCreateTag().putString(URMaterialTagName, "empty");
        stack.getOrCreateTag().putString(DLMaterialTagName, "empty");
        stack.getOrCreateTag().putString(DRMaterialTagName, "empty");
        stack.getOrCreateTag().putString(MMaterialTagName, "empty");
        stack.getOrCreateTag().putInt(LastRepairNumber, 0);
        return stack;
    }

    /**
     * combines the added material durability from each material with the base shield durability
     * @param stack the stack to find the durability for
     * @return the amount of durability the item should have at max
     */
    @Override
    public int getMaxDamage(ItemStack stack) {
        int durability = 336;
        int ul = ShieldMaterial.getFromName(getUpperLeftMaterial(stack)).getAddedDurability();
        int ur = ShieldMaterial.getFromName(getUpperRightMaterial(stack)).getAddedDurability();
        int dl = ShieldMaterial.getFromName(getDownLeftMaterial(stack)).getAddedDurability();
        int dr = ShieldMaterial.getFromName(getDownRightMaterial(stack)).getAddedDurability();
        int m = ShieldMaterial.getFromName(getMiddleMaterial(stack)).getAddedDurability();
        return durability + ul + ur + dl + dr + m;
    }

    /**
     * finds what is the most common material in the shield and tests that material like normal
     * @param toRepair the item to repair
     * @param repair the material being repaired with
     * @return weather the repair material is the correct type
     */
    @ParametersAreNonnullByDefault
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        String ul = getUpperLeftMaterial(toRepair);
        String ur = getUpperRightMaterial(toRepair);
        String dl = getDownLeftMaterial(toRepair);
        String dr = getDownRightMaterial(toRepair);
        String m = getMiddleMaterial(toRepair);
        int last = toRepair.getOrCreateTag().getInt(LastRepairNumber);
        List<String> slotMaterials = Arrays.asList(ul, ur, dl, dr, m);
        String currentSlotMaterial = "empty";
        int checked = 0;
        while (currentSlotMaterial.equals("empty") || currentSlotMaterial.equals("")) {
            currentSlotMaterial = slotMaterials.get(last);
            checked++;
            last++;
            if(checked == 5) return false;
            if (last == 5) last = 0;
        }
        toRepair.getOrCreateTag().putInt(LastRepairNumber, last);
        return ShieldMaterial.getFromName(currentSlotMaterial).getIngotOrMaterial().test(repair);
    }

    /* TODO: find out if another way is required
    @Override
    public boolean isShield(ItemStack stack, LivingEntity entity) {
        return true;
    }
     */

    /**
     * increases the mending amount per xp based on the materials used.
     * @param stack the stack that we want the mendiong bonus for.
     * @return the mending amount per xp.
     */
    @Override
    public float getXpRepairRatio(ItemStack stack) {
        return 2.0f + (float)getMendingBonus(stack);
    }

    /**
     * gets all five material mending increases or decreases per fifth and adds them together
     * @param stack the stack that we want the mendiong bonus for.
     * @return the mending bonus.
     */
    public double getMendingBonus(ItemStack stack) {
        double ul = ShieldMaterial.getFromName(getUpperLeftMaterial(stack)).getMendingBonus()/5;
        double ur = ShieldMaterial.getFromName(getUpperRightMaterial(stack)).getMendingBonus()/5;
        double dl = ShieldMaterial.getFromName(getDownLeftMaterial(stack)).getMendingBonus()/5;
        double dr = ShieldMaterial.getFromName(getDownRightMaterial(stack)).getMendingBonus()/5;
        double m = ShieldMaterial.getFromName(getMiddleMaterial(stack)).getMendingBonus()/5;
        return ul + ur + dl + dr + m;
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
        String ul = getUpperLeftMaterial(stack);
        String ur = getUpperRightMaterial(stack);
        String dl = getDownLeftMaterial(stack);
        String dr = getDownRightMaterial(stack);
        String m = getMiddleMaterial(stack);
        list.add(Component.translatable("tooltip.expanded_combat.shield_material.upper_left").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable("tooltip.expanded_combat.shield_material." + ul).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        list.add(Component.translatable("tooltip.expanded_combat.shield_material.upper_right").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable("tooltip.expanded_combat.shield_material." + ur).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        list.add(Component.translatable("tooltip.expanded_combat.shield_material.pegs_trim").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable("tooltip.expanded_combat.shield_material." + m).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        list.add(Component.translatable("tooltip.expanded_combat.shield_material.lower_left").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable("tooltip.expanded_combat.shield_material." + dl).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        list.add(Component.translatable("tooltip.expanded_combat.shield_material.lower_right").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable("tooltip.expanded_combat.shield_material." + dr).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));

        if (getMendingBonus(stack) != 0.0f) {
            if (getMendingBonus(stack) > 0.0f) {
                list.add(Component.translatable("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN).append(Component.literal(ChatFormatting.GREEN + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(getMendingBonus(stack)))));
            }
            else if (getMendingBonus(stack) < 0.0f) {
                list.add(Component.translatable("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.RED).append(Component.literal(ChatFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(getMendingBonus(stack)))));
            }
        }
        super.appendHoverText(stack, world, list, flag);
    }

    public double getBaseProtection(ItemStack stack) {
        double ul = ShieldMaterial.getFromName(getUpperLeftMaterial(stack)).getBaseProtectionAmmount() /5;
        double ur = ShieldMaterial.getFromName(getUpperRightMaterial(stack)).getBaseProtectionAmmount() /5;
        double dl = ShieldMaterial.getFromName(getDownLeftMaterial(stack)).getBaseProtectionAmmount() /5;
        double dr = ShieldMaterial.getFromName(getDownRightMaterial(stack)).getBaseProtectionAmmount() /5;
        double m = ShieldMaterial.getFromName(getMiddleMaterial(stack)).getBaseProtectionAmmount() /5;
        return ul + ur + dl + dr + m;
    }

    public double getPercentageProtection(ItemStack stack) {
        double ul = ShieldMaterial.getFromName(getUpperLeftMaterial(stack)).getAfterBasePercentReduction() /5;
        double ur = ShieldMaterial.getFromName(getUpperRightMaterial(stack)).getAfterBasePercentReduction() /5;
        double dl = ShieldMaterial.getFromName(getDownLeftMaterial(stack)).getAfterBasePercentReduction() /5;
        double dr = ShieldMaterial.getFromName(getDownRightMaterial(stack)).getAfterBasePercentReduction() /5;
        double m = ShieldMaterial.getFromName(getMiddleMaterial(stack)).getAfterBasePercentReduction() /5;
        return ul + ur + dl + dr + m;
    }
    public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new ECShieldBlockEntityWithoutLevelRenderer();
            }
        });
    }

    private String getUpperLeftMaterial(ItemStack stack) {
        return stack.getOrCreateTag().getString(ULMaterialTagName);
    }

    private String getUpperRightMaterial(ItemStack stack) {
        return stack.getOrCreateTag().getString(URMaterialTagName);
    }

    private String getDownLeftMaterial(ItemStack stack) {
        return stack.getOrCreateTag().getString(DLMaterialTagName);
    }

    private String getDownRightMaterial(ItemStack stack) {
        return stack.getOrCreateTag().getString(DRMaterialTagName);
    }

    private String getMiddleMaterial(ItemStack stack) {
        return stack.getOrCreateTag().getString(MMaterialTagName);
    }
}

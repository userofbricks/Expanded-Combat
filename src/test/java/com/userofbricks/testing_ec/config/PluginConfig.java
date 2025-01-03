package com.userofbricks.testing_ec.config;

import com.userofbricks.expanded_combat.config.ConfigName;
import com.userofbricks.expanded_combat.config.MaterialConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;

import static com.userofbricks.testing_ec.TestingPlugin.MODID;

@Config(name = MODID)
public class PluginConfig implements ConfigData {

    @ConfigEntry.Category("Materials") @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Test Material Settings")
    public MaterialConfig testMaterial = new MaterialConfig.Builder()
            .toolDurabilityMul(300) // should end up being (2031/37)*300 = 16500
            .bowDurability(3000)
            .addedShieldDurability(3000)
            .offenseEnchantability(300)
            .defenseEnchantability(300)
            .equipSound(SoundEvents.ARMOR_EQUIP_GENERIC)
            .craftingItem(Items.AMETHYST_BLOCK)
            .repairItem(Items.AMETHYST_SHARD)
            .repairAddItem(Items.GOLD_INGOT)
            .mendingBonus(30)
            .fireResistant()
            .gauntletAttackDamage(30)
            .arrowDamage(30)
            .flaming()
            .noTippedArrows()
            .multishotLevel(1)
            .bowPower(1)
            .velocityMultiplier(10)
            .gauntletArmorAmount(30)
            .armorToughness(30)
            .knockbackResistance(0.5)
            .baseProtectionAmmount(5)
            .afterBasePercentReduction(0.5f)
            .quiverSlots(30)
            .build();
}

package com.userofbricks.expanded_combat.item.recipes.conditions;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class ECConfigBooleanCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(ExpandedCombat.MODID, "config_boolean");
    private final String configBooleanName;

    public ECConfigBooleanCondition(String configBooleanName)
    {
        this.configBooleanName = configBooleanName;
    }

    @Override
    public ResourceLocation getID()
    {
        return NAME;
    }

    @Override
    public boolean test(IContext context)
    {
        return switch (configBooleanName) {
            default -> false;
            case "gauntlet" -> CONFIG.enableGauntlets;
            case "shield" -> CONFIG.enableShields;
            case "bow" -> CONFIG.enableBows;
            case "half_bow" -> CONFIG.enableHalfBows;
            case "crossbow" -> CONFIG.enableCrossbows;
            case "arrow" -> CONFIG.enableArrows;
            case "weapon" -> CONFIG.enableWeapons;
        };
    }

    @Override
    public String toString()
    {
        return "config_boolean(\"" + this.configBooleanName + "\")";
    }

    public static class Serializer implements IConditionSerializer<ECConfigBooleanCondition>
    {
        public static final ECConfigBooleanCondition.Serializer INSTANCE = new ECConfigBooleanCondition.Serializer();

        @Override
        public void write(JsonObject json, ECConfigBooleanCondition value)
        {
            json.addProperty("config_entry", value.configBooleanName);
        }

        @Override
        public ECConfigBooleanCondition read(JsonObject json)
        {
            return new ECConfigBooleanCondition(GsonHelper.getAsString(json, "config_entry"));
        }

        @Override
        public ResourceLocation getID()
        {
            return ECConfigBooleanCondition.NAME;
        }
    }
}

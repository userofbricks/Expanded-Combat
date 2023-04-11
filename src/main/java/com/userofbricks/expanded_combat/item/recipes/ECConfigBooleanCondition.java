package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.values.ECConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import java.util.Arrays;

public class ECConfigBooleanCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(ExpandedCombat.MODID, "config_boolean");
    private final ForgeConfigSpec.BooleanValue configBoolean;

    public ECConfigBooleanCondition(ForgeConfigSpec.BooleanValue configBoolean)
    {
        this.configBoolean = configBoolean;
    }

    @Override
    public ResourceLocation getID()
    {
        return NAME;
    }

    @Override
    public boolean test(IContext context)
    {
        return configBoolean.get();
    }

    @Override
    public String toString()
    {
        return "config_boolean(\"" + String.join("-", configBoolean.getPath()) + "\")";
    }

    public static class Serializer implements IConditionSerializer<ECConfigBooleanCondition>
    {
        public static final ECConfigBooleanCondition.Serializer INSTANCE = new ECConfigBooleanCondition.Serializer();

        @Override
        public void write(JsonObject json, ECConfigBooleanCondition value)
        {
            json.addProperty("config_entry", String.join("-", value.configBoolean.getPath()));
        }

        @Override
        public ECConfigBooleanCondition read(JsonObject json)
        {
            return new ECConfigBooleanCondition(ECConfig.SERVER_SPEC.get(Arrays.asList(GsonHelper.getAsString(json, "config_entry").split("-"))));
        }

        @Override
        public ResourceLocation getID()
        {
            return ECConfigBooleanCondition.NAME;
        }
    }
}

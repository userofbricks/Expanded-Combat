package com.userofbricks.expanded_combat.item.recipes.conditions;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.config.ECConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class ECConfigBowRecipeTypeCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(ExpandedCombat.MODID, "config_bow_recipe_type");
    private final ECConfig.BowRecipeType configEnumName;

    public ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType configEnumName)
    {
        this.configEnumName = configEnumName;
    }

    @Override
    public ResourceLocation getID()
    {
        return NAME;
    }

    @Override
    public boolean test(ICondition.IContext context)
    {
        return CONFIG.bowRecipeType == this.configEnumName;
    }

    @Override
    public String toString()
    {
        return "config_bow_recipe_type(\"" + this.configEnumName + "\")";
    }

    public static class Serializer implements IConditionSerializer<ECConfigBowRecipeTypeCondition>
    {
        public static final ECConfigBowRecipeTypeCondition.Serializer INSTANCE = new ECConfigBowRecipeTypeCondition.Serializer();

        @Override
        public void write(JsonObject json, ECConfigBowRecipeTypeCondition value)
        {
            json.addProperty("crafting_type", value.configEnumName.toString());
        }

        @Override
        public ECConfigBowRecipeTypeCondition read(JsonObject json)
        {
            return new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.valueOf(GsonHelper.getAsString(json, "crafting_type")));
        }

        @Override
        public ResourceLocation getID()
        {
            return ECConfigBowRecipeTypeCondition.NAME;
        }
    }
}

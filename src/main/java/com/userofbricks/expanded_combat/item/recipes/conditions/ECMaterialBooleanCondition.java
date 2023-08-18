package com.userofbricks.expanded_combat.item.recipes.conditions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ECMaterialBooleanCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(ExpandedCombat.MODID, "config_material_boolean");
    private final String materialName;
    private final String[] locationPath;

    public ECMaterialBooleanCondition(String configBooleanName, String... locationPath) {
        this.materialName = configBooleanName;
        this.locationPath = locationPath;
    }

    @Override
    public ResourceLocation getID()
    {
        return NAME;
    }

    @Override
    public boolean test(IContext context)
    {
        Material material = MaterialInit.valueOf(materialName);

        Iterator<String> locationIterator = Arrays.stream(locationPath).iterator();
        if (!locationIterator.hasNext()) return false;
        final boolean result;
        switch (locationIterator.next()) {
            default -> result = false;
            case "halfbow" -> result = material.halfbow;
            case "config" -> {
                ECConfig.MaterialConfig config = material.getConfig();
                if (!locationIterator.hasNext()) return false;
                switch (locationIterator.next()) {
                    default -> result = false;
                    case "fire_resistant" -> result = config.fireResistant;
                    case "offense" -> {
                        ECConfig.MaterialConfig.Offense offense = config.offense;
                        if (!locationIterator.hasNext()) return false;
                        switch (locationIterator.next()) {
                            default -> result = false;
                            case "flaming" -> result = offense.flaming;
                            case "can_be_tipped" -> result = offense.canBeTipped;
                        }
                    }
                    case "crafting" -> {
                        ECConfig.MaterialConfig.Crafting crafting = config.crafting;
                        if (!locationIterator.hasNext()) return false;
                        if ("is_single_addition".equals(locationIterator.next())) {
                            result = crafting.isSingleAddition;
                        } else {
                            result = false;
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String toString()
    {
        return "config_boolean(\"" + this.materialName + "\")";
    }

    public static class Serializer implements IConditionSerializer<ECMaterialBooleanCondition>
    {
        public static final ECMaterialBooleanCondition.Serializer INSTANCE = new ECMaterialBooleanCondition.Serializer();

        @Override
        public void write(JsonObject json, ECMaterialBooleanCondition value)
        {
            json.addProperty("material", value.materialName);
            JsonArray locationPath = new JsonArray();
            for (String location :
                    value.locationPath) {
                locationPath.add(location);
            }
            json.add("location_path", locationPath);

        }

        @Override
        public ECMaterialBooleanCondition read(JsonObject json)
        {
            ArrayList<String> locationList = new ArrayList<>();
            List<JsonElement> locations = json.getAsJsonArray("location_path").asList();
            for (JsonElement location : locations) {
                locationList.add(location.getAsString());
            }
            return new ECMaterialBooleanCondition(GsonHelper.getAsString(json, "material"), locationList.toArray(new String[0]));
        }

        @Override
        public ResourceLocation getID()
        {
            return ECMaterialBooleanCondition.NAME;
        }
    }
}

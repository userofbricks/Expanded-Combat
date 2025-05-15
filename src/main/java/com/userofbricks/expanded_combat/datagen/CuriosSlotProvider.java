package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;
import static net.minecraft.world.entity.EntityType.*;

public class CuriosSlotProvider extends CuriosDataProvider {
    public CuriosSlotProvider(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(MODID, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        createSlot(QUIVER_CURIOS_IDENTIFIER)
                .size(1)
                .icon(modLoc("slot/empty_" + QUIVER_CURIOS_IDENTIFIER + "_slot"))
                .addCosmetic(true);

        createEntities("gauntlet")
                .addPlayer()
                .addEntities(ZOMBIE, ZOMBIE_VILLAGER, PIGLIN, ZOMBIFIED_PIGLIN, PILLAGER, VINDICATOR)
                .addSlots(GAUNTLET_CURIOS_IDENTIFIER)
                .addCondition(new ECConfigBooleanCondition("gauntlet"));

        createEntities("quiver")
                .addPlayer()
                .addEntities(ZOMBIE, ZOMBIE_VILLAGER, PIGLIN, ZOMBIFIED_PIGLIN, PILLAGER, VINDICATOR, STRAY, SKELETON, BOGGED)
                .addSlots(QUIVER_CURIOS_IDENTIFIER)
                .addCondition(new ECConfigBooleanCondition("quiver"));
    }
}

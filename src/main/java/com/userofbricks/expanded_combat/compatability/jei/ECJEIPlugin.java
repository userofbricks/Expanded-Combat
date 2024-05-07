package com.userofbricks.expanded_combat.compatability.jei;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.FletchingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expanded_combat.compatability.jei.container_handelers.CuriosContainerHandler;
import com.userofbricks.expanded_combat.compatability.jei.item_subtype.ShieldSubtypeInterpreter;
import com.userofbricks.expanded_combat.compatability.jei.recipe_category.FletchingRecipeCategory;
import com.userofbricks.expanded_combat.compatability.jei.recipe_category.ShieldSmithingRecipeCategory;
import com.userofbricks.expanded_combat.compatability.jei.recipes.*;
import com.userofbricks.expanded_combat.init.ECContainers;
import com.userofbricks.expanded_combat.inventory.container.FletchingTableMenu;
import com.userofbricks.expanded_combat.inventory.container.ShieldSmithingMenu;
import com.userofbricks.expanded_combat.item.ECArrowItem;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.init.MaterialInit;
import com.userofbricks.expanded_combat.item.recipes.IFletchingRecipe;
import com.userofbricks.expanded_combat.item.recipes.IShieldSmithingRecipe;
import com.userofbricks.expanded_combat.util.ModIDs;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.common.util.ErrorUtil;
import mezz.jei.library.plugins.vanilla.brewing.PotionSubtypeInterpreter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.ModList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.client.gui.CuriosScreenV2;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.compatability.jei.recipe_category.FletchingRecipeCategory.FLETCHING;
import static com.userofbricks.expanded_combat.compatability.jei.recipe_category.ShieldSmithingRecipeCategory.SHIELD_SMITHING;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECJEIPlugin implements IModPlugin {
    private static final Logger LOGGER = LogManager.getLogger();
    @Nullable
    private IRecipeCategory<IFletchingRecipe> fletchingCategory;
    @Nullable
    private IRecipeCategory<IShieldSmithingRecipe> shieldSmithingCategory;

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MODID, "jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        for (Material material :
                MaterialInit.arrowMaterials) {
            registration.registerSubtypeInterpreter(material.getTippedArrowEntry().get(), PotionSubtypeInterpreter.INSTANCE);
        }
        for (Material material :
                MaterialInit.weaponMaterials) {
            material.getWeapons().forEach((weaponName, registryEntry) -> {
                if (registryEntry.get() instanceof ECWeaponItem.HasPotion) registration.registerSubtypeInterpreter(registryEntry.get(), PotionSubtypeInterpreter.INSTANCE);
            });
        }
        registration.registerSubtypeInterpreter(ECItems.SHIELD_TIER_1.get(), ShieldSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(ECItems.SHIELD_TIER_2.get(), ShieldSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(ECItems.SHIELD_TIER_3.get(), ShieldSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(ECItems.SHIELD_TIER_4.get(), ShieldSubtypeInterpreter.INSTANCE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(shieldSmithingCategory = new ShieldSmithingRecipeCategory(guiHelper));
        if (!ModList.get().isLoaded(ModIDs.apotheosis)) {
            registration.addRecipeCategories(fletchingCategory = new FletchingRecipeCategory(guiHelper));
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ErrorUtil.checkNotNull(shieldSmithingCategory, "shieldSmithingCategory");

        IIngredientManager ingredientManager = registration.getIngredientManager();
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IStackHelper stackHelper = jeiHelpers.getStackHelper();
        ECRecipes vanillaRecipes = new ECRecipes(ingredientManager);
        for (Material material :
                MaterialInit.arrowMaterials) {
            registration.addRecipes(RecipeTypes.CRAFTING, ECTippedArrowRecipeMaker.createRecipes(stackHelper, (ECArrowItem) material.getArrowEntry().get()));
        }

        for (RegistryEntry<? extends Item> item:ECItems.ITEMS) {
            if (item.get() instanceof ECWeaponItem.HasPotion) {
                registration.addRecipes(RecipeTypes.CRAFTING, ECPotionWeaponRecipeMaker.createRecipes(stackHelper, (ECWeaponItem.HasPotion) item.get()));
            }
        }

        if (fletchingCategory != null) {
            registration.addRecipes(FLETCHING, vanillaRecipes.getFletchingRecipes(fletchingCategory));
            registration.addRecipes(FLETCHING, ECFletchingTippedArrowRecipeMaker.createTippedArrowRecipes(stackHelper));
        }
        registration.addRecipes(SHIELD_SMITHING, vanillaRecipes.getShieldSmithingRecipes(shieldSmithingCategory));
        registration.addRecipes(SHIELD_SMITHING, ECShieldSmithingRecipeMaker.createShieldSmithingRecipes(stackHelper));
        registration.addRecipes(RecipeTypes.CRAFTING, ECShieldDecorationRecipeMaker.createRecipes(stackHelper));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        if (fletchingCategory != null) registration.addRecipeTransferHandler(FletchingTableMenu.class, ECContainers.FLETCHING.get(), FLETCHING, 0, 2, 3, 36);
        registration.addRecipeTransferHandler(ShieldSmithingMenu.class, ECContainers.SHIELD_SMITHING.get(), SHIELD_SMITHING, 0, 6, 7, 36);
    }
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (fletchingCategory != null) registration.addRecipeCatalyst(new ItemStack(Blocks.FLETCHING_TABLE), FLETCHING);
        registration.addRecipeCatalyst(new ItemStack(Blocks.SMITHING_TABLE), SHIELD_SMITHING);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        if (fletchingCategory != null) registration.addRecipeClickArea(FletchingTableScreen.class, 102, 48, 22, 15, FLETCHING);
        registration.addRecipeClickArea(ShieldSmithingTableScreen.class, 102, 48, 22, 15, SHIELD_SMITHING);
        registration.addGuiContainerHandler(CuriosScreenV2.class, new CuriosContainerHandler());
    }
}

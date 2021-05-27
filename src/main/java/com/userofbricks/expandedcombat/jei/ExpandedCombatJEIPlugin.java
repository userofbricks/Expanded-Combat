package com.userofbricks.expandedcombat.jei;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.FletchingTableScreen;
import com.userofbricks.expandedcombat.inventory.container.FlechingTableContainer;
import com.userofbricks.expandedcombat.item.ECArrowItem;
import com.userofbricks.expandedcombat.item.ECItems;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import com.userofbricks.expandedcombat.item.recipes.FletchingRecipe;
import com.userofbricks.expandedcombat.item.recipes.IFletchingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.inventory.SmithingTableScreen;
import net.minecraft.inventory.container.SmithingTableContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;

@JeiPlugin
public class ExpandedCombatJEIPlugin implements IModPlugin {
    @Nullable
    private IRecipeCategory<IFletchingRecipe> fletchingCategory;
    /**
     * The fletching recipe category.
     *
     * Automatically includes every {@link FletchingRecipe}.
     * @since Expended Combat 2.2.0-alpha_2
     */
    public static final ResourceLocation FLETCHING = new ResourceLocation(ExpandedCombat.MODID, "fletching");

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ExpandedCombat.MODID, "recipies");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ECItems.IRON_TIPPED_ARROW.get(), PotionSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(ECItems.DIAMOND_TIPPED_ARROW.get(), PotionSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(ECItems.NETHERITE_TIPPED_ARROW.get(), PotionSubtypeInterpreter.INSTANCE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(
                fletchingCategory = new BaseFletchingRecipeCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (fletchingCategory == null) {
            throw new NullPointerException("fletchingCategory" + " must not be null.");
        }

        IIngredientManager ingredientManager = registration.getIngredientManager();
        IVanillaRecipeFactory vanillaRecipeFactory = registration.getVanillaRecipeFactory();
        ECRecipes vanillaRecipes = new ECRecipes();
        registration.addRecipes(ECTippedArrowRecipeMaker.createTippedArrowRecipes((ECArrowItem) ECItems.IRON_ARROW.get()), VanillaRecipeCategoryUid.CRAFTING);
        registration.addRecipes(ECTippedArrowRecipeMaker.createTippedArrowRecipes((ECArrowItem) ECItems.DIAMOND_ARROW.get()), VanillaRecipeCategoryUid.CRAFTING);
        registration.addRecipes(ECTippedArrowRecipeMaker.createTippedArrowRecipes((ECArrowItem) ECItems.NETHERITE_ARROW.get()), VanillaRecipeCategoryUid.CRAFTING);
        for (RegistryObject<Item> item:ECItems.ITEMS.getEntries()) {
            if (item.get() instanceof ECWeaponItem.HasPotion) {
                registration.addRecipes(ECScytheRecipeMaker.createTippedScytheRecipes((ECWeaponItem.HasPotion) item.get()), VanillaRecipeCategoryUid.CRAFTING);
            }
        }
        registration.addRecipes(GauntletAnvilRecipeMaker.getAnvilRecipes(vanillaRecipeFactory, ingredientManager), VanillaRecipeCategoryUid.ANVIL);
        registration.addRecipes(vanillaRecipes.getFletchingRecipes(fletchingCategory), FLETCHING);
        registration.addRecipes(ECFletchingTippedArrowRecipeMaker.createTippedArrowRecipes((ECArrowItem) ECItems.IRON_ARROW.get()), FLETCHING);
        registration.addRecipes(ECFletchingTippedArrowRecipeMaker.createTippedArrowRecipes((ECArrowItem) ECItems.DIAMOND_ARROW.get()), FLETCHING);
        registration.addRecipes(ECFletchingTippedArrowRecipeMaker.createTippedArrowRecipes((ECArrowItem) ECItems.NETHERITE_ARROW.get()), FLETCHING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(FlechingTableContainer.class, FLETCHING, 0, 2, 3, 36);
    }
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Blocks.FLETCHING_TABLE), FLETCHING);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FletchingTableScreen.class, 102, 48, 22, 15, FLETCHING);
    }
}

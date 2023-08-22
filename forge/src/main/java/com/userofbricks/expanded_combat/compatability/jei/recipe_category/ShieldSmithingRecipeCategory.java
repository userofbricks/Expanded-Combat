package com.userofbricks.expanded_combat.compatability.jei.recipe_category;

import com.userofbricks.expanded_combat.item.recipes.IShieldSmithingRecipe;
import com.userofbricks.expanded_combat.util.LangStrings;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.library.util.RecipeUtil;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ShieldSmithingRecipeCategory implements IRecipeCategory<IShieldSmithingRecipe> {

    private final IDrawable background;
    private final IDrawable icon;
    public static final ResourceLocation iconLocation = new ResourceLocation(MODID, "textures/gui/jei/recipe_icons.png");

    public static final RecipeType<IShieldSmithingRecipe> SHIELD_SMITHING =
            RecipeType.create(MODID, "shield_smithing", IShieldSmithingRecipe.class);

    public ShieldSmithingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(FletchingRecipeCategory.textureLocation, 0, 28, 125, 54);
        icon = guiHelper.createDrawable(iconLocation, 0, 0, 18, 18);
    }

    @Override
    public RecipeType<IShieldSmithingRecipe> getRecipeType() {
        return SHIELD_SMITHING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(LangStrings.SHIELD_UPGRADE_CONTAINER);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IShieldSmithingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 19)
                .addIngredients(recipe.getBase());

        builder.addSlot(RecipeIngredientRole.INPUT, 41, 1)
                .addIngredients(recipe.getULAddition());

        builder.addSlot(RecipeIngredientRole.INPUT, 59, 1)
                .addIngredients(recipe.getURAddition());

        builder.addSlot(RecipeIngredientRole.INPUT, 50, 19)
                .addIngredients(recipe.getMAddition());

        builder.addSlot(RecipeIngredientRole.INPUT, 41, 37)
                .addIngredients(recipe.getDLAddition());

        builder.addSlot(RecipeIngredientRole.INPUT, 59, 37)
                .addIngredients(recipe.getDRAddition());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 19)
                .addItemStack(RecipeUtil.getResultItem(recipe));
    }
}

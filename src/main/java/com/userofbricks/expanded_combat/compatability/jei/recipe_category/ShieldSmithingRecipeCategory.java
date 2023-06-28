package com.userofbricks.expanded_combat.compatability.jei.recipe_category;

import com.userofbricks.expanded_combat.item.recipes.ShieldSmithingRecipie;
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
public class ShieldSmithingRecipeCategory implements IRecipeCategory<ShieldSmithingRecipie> {

    private final IDrawable background;
    private final IDrawable icon;
    public static final ResourceLocation iconLocation = new ResourceLocation(MODID, "textures/gui/jei/recipe_icons.png");

    public static final RecipeType<ShieldSmithingRecipie> SHIELD_SMITHING =
            RecipeType.create(MODID, "shield_smithing", ShieldSmithingRecipie.class);

    public ShieldSmithingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(FletchingRecipeCategory.textureLocation, 0, 18, 125, 18*3);
        icon = guiHelper.createDrawable(iconLocation, 0, 0, 18, 18);
    }

    @Override
    public RecipeType<ShieldSmithingRecipie> getRecipeType() {
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
    public void setRecipe(IRecipeLayoutBuilder builder, ShieldSmithingRecipie recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 0, 20);
                //.addIngredients(recipe.getBase());

        builder.addSlot(RecipeIngredientRole.INPUT, 49, 0);
                //.addIngredients(recipe.getAddition());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 107, 0)
                .addItemStack(RecipeUtil.getResultItem(recipe));
    }
}

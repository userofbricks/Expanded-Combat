package com.userofbricks.expanded_combat.compatability.jei.recipe_category;

import com.userofbricks.expanded_combat.datagen.LangStrings;
import com.userofbricks.expanded_combat.init.ECRecipeInit;
import com.userofbricks.expanded_combat.item.recipes.IShieldSmithingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ShieldSmithingRecipeCategory implements IRecipeCategory<RecipeHolder<IShieldSmithingRecipe>> {

    private final IDrawable background;
    private final IDrawable icon;
    public static final ResourceLocation iconLocation = modLoc("textures/gui/jei/recipe_icons.png");

    public static final RecipeType<RecipeHolder<IShieldSmithingRecipe>> SHIELD_SMITHING =
            RecipeType.createFromVanilla(ECRecipeInit.SHIELD_TYPE.get());

    public ShieldSmithingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(FletchingRecipeCategory.textureLocation, 0, 28, 125, 54);
        icon = guiHelper.createDrawable(iconLocation, 0, 0, 18, 18);
    }

    @Override
    public RecipeType<RecipeHolder<IShieldSmithingRecipe>> getRecipeType() {
        return SHIELD_SMITHING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(LangStrings.SHIELD_UPGRADE_CONTAINER);
    }

    public void draw(RecipeHolder<IShieldSmithingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
    }

    public int getWidth() {
        return background.getWidth();
    }

    public int getHeight() {
        return background.getHeight();
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<IShieldSmithingRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 19)
                .addIngredients(recipe.value().getBase());

        builder.addSlot(RecipeIngredientRole.INPUT, 41, 1)
                .addIngredients(recipe.value().getULAddition());

        builder.addSlot(RecipeIngredientRole.INPUT, 59, 1)
                .addIngredients(recipe.value().getURAddition());

        builder.addSlot(RecipeIngredientRole.INPUT, 50, 19)
                .addIngredients(recipe.value().getMAddition());

        builder.addSlot(RecipeIngredientRole.INPUT, 41, 37)
                .addIngredients(recipe.value().getDLAddition());

        builder.addSlot(RecipeIngredientRole.INPUT, 59, 37)
                .addIngredients(recipe.value().getDRAddition());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 19)
                .addItemStack(RecipeUtil.getResultItem(recipe.value()));
    }
}

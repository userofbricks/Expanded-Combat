package com.userofbricks.expanded_combat.compatability.jei.recipe_category;

import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import com.userofbricks.expanded_combat.item.recipes.FletchingRecipe;
import com.userofbricks.expanded_combat.item.recipes.IFletchingRecipe;
import mezz.jei.api.gui.ITickTimer;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FletchingRecipeCategory implements IRecipeCategory<RecipeHolder<IFletchingRecipe>> {

    private final IDrawable background;
    private final IDrawable icon;
    private final ITickTimer tickTimer;
    public static final ResourceLocation textureLocation = modLoc("textures/gui/jei/recipe_backgrounds.png");

    public static final RecipeType<RecipeHolder<IFletchingRecipe>> FLETCHING =
            RecipeType.createFromVanilla(ECRecipeSerializerInit.FLETCHING_TYPE.get());

    public FletchingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(textureLocation, 0, 0, 125, 18);
        icon = guiHelper.createDrawableItemStack(new ItemStack(Blocks.FLETCHING_TABLE));
        tickTimer = guiHelper.createTickTimer(256, 64, false);
    }

    @Override
    public RecipeType<RecipeHolder<IFletchingRecipe>> getRecipeType() {
        return FLETCHING;
    }

    @Override
    public Component getTitle() {
        return Blocks.FLETCHING_TABLE.getName();
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
    public void draw(RecipeHolder<IFletchingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
        if ((tickTimer.getValue() != 0 || tickTimer.getValue() != 1) && recipe.value().getMaxCraftingAmount() > 1) {
            guiGraphics.pose().pushPose();
            drawAcendingNumbers(guiGraphics, 7, 12, recipe.value().getMaxCraftingAmount());
            drawAcendingNumbers(guiGraphics, 114, 12, recipe.value().getMaxCraftingAmount());
            guiGraphics.pose().popPose();
        }
    }

    public void drawAcendingNumbers(GuiGraphics guiGraphics, int offsetX, int offsetY, int maxRecipeRepeats) {
        int firstNumber = tickTimer.getValue() / 10;
        int secondNumber = tickTimer.getValue() % 10;

        //Don't render if number is above maxRecipe Repeats
        if (maxRecipeRepeats/10 < firstNumber) return;
        if (maxRecipeRepeats/10 == firstNumber && maxRecipeRepeats%10 < secondNumber) return;

        int width = 8;
        int height = 10;
        int firstNumTexX = firstNumber * width;
        int secondNumTexX = secondNumber * width;

        int numTexY = 18;

        for (int i = 0; i < 2; i++) {
            int numTexX = i == 0 ? firstNumTexX : secondNumTexX;
            if (!(i == 0 && tickTimer.getValue() < 10)) {
                guiGraphics.blit(textureLocation, offsetX, offsetY, numTexX, numTexY, width, height);
            }
            offsetX = offsetX + 6;
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<IFletchingRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addIngredients(recipe.value().getBase());

        builder.addSlot(RecipeIngredientRole.INPUT, 50, 1)
                .addIngredients(recipe.value().getAddition());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 1)
                .addItemStack(RecipeUtil.getResultItem(recipe.value()));
    }

    @Override
    public boolean isHandled(RecipeHolder<IFletchingRecipe> recipe) {
        return recipe.value() instanceof FletchingRecipe;
    }
}

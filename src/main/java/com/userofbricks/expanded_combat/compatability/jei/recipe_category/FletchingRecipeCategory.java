package com.userofbricks.expanded_combat.compatability.jei.recipe_category;

import com.mojang.blaze3d.systems.RenderSystem;
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
import mezz.jei.library.util.RecipeUtil;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FletchingRecipeCategory implements IRecipeCategory<IFletchingRecipe> {

    private final IDrawable background;
    private final IDrawable icon;
    private final ITickTimer tickTimer;
    public static final ResourceLocation textureLocation = new ResourceLocation(MODID, "textures/gui/jei/recipe_backgrounds.png");

    public static final RecipeType<IFletchingRecipe> FLETCHING =
            RecipeType.create(MODID, "fletching", IFletchingRecipe.class);

    public FletchingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(textureLocation, 0, 0, 125, 18);
        icon = guiHelper.createDrawableItemStack(new ItemStack(Blocks.FLETCHING_TABLE));
        tickTimer = guiHelper.createTickTimer(256, 64, false);
    }

    @Override
    public RecipeType<IFletchingRecipe> getRecipeType() {
        return FLETCHING;
    }

    @Override
    public Component getTitle() {
        return Blocks.FLETCHING_TABLE.getName();
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
    public void draw(IFletchingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        if ((tickTimer.getValue() != 0 || tickTimer.getValue() != 1) && recipe.getMaxCraftingAmount() > 1) {
            guiGraphics.pose().pushPose();
            drawAcendingNumbers(guiGraphics, 7, 12, recipe.getMaxCraftingAmount());
            drawAcendingNumbers(guiGraphics, 114, 12, recipe.getMaxCraftingAmount());
            guiGraphics.pose().popPose();
        }
    }

    public void drawAcendingNumbers(GuiGraphics guiGraphics, int offsetX, int offsetY, int maxRecipeRepeats) {
        int firstNumber = tickTimer.getValue() / 10;
        int secondNumber = tickTimer.getValue() % 10;

        //Don't render if number is above maxRecipe Repeats
        if (maxRecipeRepeats/10 < firstNumber) return;
        if (maxRecipeRepeats/10 == firstNumber && maxRecipeRepeats%10 > secondNumber) return;

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
    public void setRecipe(IRecipeLayoutBuilder builder, IFletchingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addIngredients(recipe.getBase());

        builder.addSlot(RecipeIngredientRole.INPUT, 50, 1)
                .addIngredients(recipe.getAddition());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 1)
                .addItemStack(RecipeUtil.getResultItem(recipe));
    }

    @Override
    public boolean isHandled(IFletchingRecipe recipe) {
        return recipe instanceof FletchingRecipe;
    }
}

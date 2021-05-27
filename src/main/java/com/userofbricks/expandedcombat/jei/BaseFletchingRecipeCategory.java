package com.userofbricks.expandedcombat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.recipes.FletchingRecipe;
import com.userofbricks.expandedcombat.item.recipes.IFletchingRecipe;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;

import java.util.Arrays;

public class BaseFletchingRecipeCategory implements IRecipeCategory<IFletchingRecipe> {

	private final IDrawable background;
	private final IDrawable icon;
	private final ITickTimer tickTimer;
	private final ResourceLocation textureLocation = new ResourceLocation(ExpandedCombat.MODID, "textures/gui/jei/fletching.png");

	public BaseFletchingRecipeCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(textureLocation, 0, 0, 125, 18);
		icon = guiHelper.createDrawableIngredient(new ItemStack(Blocks.FLETCHING_TABLE));
		tickTimer = guiHelper.createTickTimer(256, 64, false);
	}

	@Override
	public ResourceLocation getUid() {
		return ExpandedCombatJEIPlugin.FLETCHING;
	}

	@Override
	public Class<? extends FletchingRecipe> getRecipeClass() {
		return FletchingRecipe.class;
	}

	@Override
	@Deprecated
	public String getTitle() {
		return getTitleAsTextComponent().getString();
	}

	@Override
	public ITextComponent getTitleAsTextComponent() {
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

	/* TODO
	    look into the other ways of setting inputs.
	    jei uses Itemstacks not Ingredients.
	    as such it converts the ingredients listed below to ItemStack Lists.
	    this could possibly make the overlay methods below obselette
	 */
	@Override
	public void setIngredients(IFletchingRecipe recipe, IIngredients ingredients) {

		ingredients.setInputIngredients(Arrays.asList(recipe.getBase(), recipe.getAddition()));
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
	}

	@Override
	public void draw(IFletchingRecipe recipe, MatrixStack ms, double mouseX, double mouseY) {
		if (tickTimer.getValue() != 0 || tickTimer.getValue() != 1) {
			RenderSystem.enableAlphaTest();
			RenderSystem.enableBlend();
			drawAcendingNumbers(ms, 5, 9);
			drawAcendingNumbers(ms, 112, 9);
			RenderSystem.disableBlend();
			RenderSystem.disableAlphaTest();
		}
	}

	public void drawAcendingNumbers(MatrixStack matrixStack, int xOffset, int yOffset) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bind(textureLocation);

		int firstNumber = tickTimer.getValue() / 10;
		int secondNumber = tickTimer.getValue() % 10;
		int x = xOffset;
		int width = 8;
		int height = 10;
		int u1 = firstNumber * width;
		int u2 = secondNumber * width;
		int v = 18;
		float f = 1.0F / 256;
		float f1 = 1.0F / 256;
		for (int i = 0; i < 2; i++) {
			int u = i == 0 ? u1 : u2;
			if (!(i == 0 && tickTimer.getValue() < 10)) {
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuilder();
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
				Matrix4f matrix = matrixStack.last().pose();
				bufferbuilder.vertex(matrix, x, yOffset + height, 0).uv(u * f, (v + (float) height) * f1).endVertex();
				bufferbuilder.vertex(matrix, x + width, yOffset + height, 0).uv((u + (float) width) * f, (v + (float) height) * f1).endVertex();
				bufferbuilder.vertex(matrix, x + width, yOffset, 0).uv((u + (float) width) * f, v * f1).endVertex();
				bufferbuilder.vertex(matrix, x, yOffset, 0).uv(u * f, v * f1).endVertex();
				tessellator.end();
			}
			x = x + 6;
		}
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IFletchingRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 0);
		guiItemStacks.init(1, true, 49, 0);
		guiItemStacks.init(2, false, 107, 0);

		guiItemStacks.set(ingredients);
	}

}

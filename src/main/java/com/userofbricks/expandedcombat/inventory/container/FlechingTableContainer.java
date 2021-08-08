package com.userofbricks.expandedcombat.inventory.container;

import com.userofbricks.expandedcombat.item.recipes.IFletchingRecipe;
import com.userofbricks.expandedcombat.item.recipes.RecipeSerializerInit;
import com.userofbricks.expandedcombat.item.recipes.SingleFletchingRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class FlechingTableContainer extends ItemCombinerMenu {
    private final Level level;
    @Nullable
    private IFletchingRecipe selectedRecipe;
    private final List<IFletchingRecipe> recipes;

    public FlechingTableContainer(int p_i231591_1_, Inventory p_i231591_2_, ContainerLevelAccess p_i231591_3_) {
        super(ECContainers.FLETCHING.get(), p_i231591_1_, p_i231591_2_, p_i231591_3_);
        this.level = p_i231591_2_.player.level;
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(RecipeSerializerInit.FLETCHING_TYPE);
    }

    public FlechingTableContainer(int i, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
       this(i, playerInventory, ContainerLevelAccess.NULL);
    }

    @Override
    protected boolean isValidBlock(BlockState p_230302_1_) {
        return p_230302_1_.is(Blocks.FLETCHING_TABLE);
    }

    @Override
    protected boolean mayPickup(Player p_230303_1_, boolean p_230303_2_) {
        return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
    }

    @Nonnull
    @Override
    protected void onTake(Player p_230301_1_, ItemStack p_230301_2_) {
        p_230301_2_.onCraftedBy(p_230301_1_.level, p_230301_1_, p_230301_2_.getCount());
        this.resultSlots.awardUsedRecipes(p_230301_1_);
        if (!(selectedRecipe instanceof SingleFletchingRecipe)) {
            this.inputSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.shrinkStackInSlot(0);
        }
        this.shrinkStackInSlot(1);
        this.access.execute((p_234653_0_, p_234653_1_) -> {
            p_234653_0_.levelEvent(1044, p_234653_1_, 0);
        });
    }

    private void shrinkStackInSlot(int slot) {
        ItemStack itemstack = this.inputSlots.getItem(slot);
        itemstack.shrink(1);
        this.inputSlots.setItem(slot, itemstack);
    }

    @Override
    public void createResult() {
        List<IFletchingRecipe> list = this.level.getRecipeManager().getRecipesFor(RecipeSerializerInit.FLETCHING_TYPE, this.inputSlots, this.level);
        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.selectedRecipe = list.get(0);
            ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots);
            this.resultSlots.setRecipeUsed(this.selectedRecipe);
            this.resultSlots.setItem(0, itemstack);
        }

    }

    @Override
    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack p_241210_1_) {
        return this.recipes.stream().anyMatch((p_241444_1_) -> p_241444_1_.isAdditionIngredient(p_241210_1_));
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack p_94530_1_, Slot p_94530_2_) {
        return p_94530_2_.container != this.resultSlots && super.canTakeItemForPickAll(p_94530_1_, p_94530_2_);
    }
}

package com.userofbricks.expandedcombat.inventory.container;

import com.userofbricks.expandedcombat.item.recipes.IFletchingRecipe;
import com.userofbricks.expandedcombat.item.recipes.RecipeSerializerInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class FlechingTableContainer extends AbstractRepairContainer {
    private final World level;
    @Nullable
    private IFletchingRecipe selectedRecipe;
    private final List<IFletchingRecipe> recipes;

    public FlechingTableContainer(int p_i231591_1_, PlayerInventory p_i231591_2_, IWorldPosCallable p_i231591_3_) {
        super(ECContainers.FLETCHING.get(), p_i231591_1_, p_i231591_2_, p_i231591_3_);
        this.level = p_i231591_2_.player.level;
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(RecipeSerializerInit.FLETCHING_TYPE);
    }

    public FlechingTableContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
       this(i, playerInventory, (IWorldPosCallable) (Minecraft.getInstance().level != null ? Minecraft.getInstance().level : IWorldPosCallable.NULL));
    }

    @Override
    protected boolean isValidBlock(BlockState p_230302_1_) {
        return p_230302_1_.is(Blocks.FLETCHING_TABLE);
    }

    @Override
    protected boolean mayPickup(PlayerEntity p_230303_1_, boolean p_230303_2_) {
        return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
    }

    @Nonnull
    @Override
    protected ItemStack onTake(PlayerEntity p_230301_1_, ItemStack p_230301_2_) {
        p_230301_2_.onCraftedBy(p_230301_1_.level, p_230301_1_, p_230301_2_.getCount());
        this.resultSlots.awardUsedRecipes(p_230301_1_);
        this.inputSlots.setItem(0, ItemStack.EMPTY);
        this.shrinkStackInSlot();
        this.access.execute((p_234653_0_, p_234653_1_) -> {
            p_234653_0_.levelEvent(1044, p_234653_1_, 0);
        });
        return p_230301_2_;
    }

    private void shrinkStackInSlot() {
        ItemStack itemstack = this.inputSlots.getItem(1);
        itemstack.shrink(1);
        this.inputSlots.setItem(1, itemstack);
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

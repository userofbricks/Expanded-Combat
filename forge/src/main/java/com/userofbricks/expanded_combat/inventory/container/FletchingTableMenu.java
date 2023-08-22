package com.userofbricks.expanded_combat.inventory.container;

import com.userofbricks.expanded_combat.item.recipes.ECRecipeSerializerInit;
import com.userofbricks.expanded_combat.item.recipes.IFletchingRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Optional;

public class FletchingTableMenu extends ItemCombinerMenu {
    private final Level level;
    @Nullable
    private IFletchingRecipe selectedRecipe;

    public FletchingTableMenu(int p_i231591_1_, Inventory p_i231591_2_, ContainerLevelAccess p_i231591_3_) {
        super(ECContainers.FLETCHING.get(), p_i231591_1_, p_i231591_2_, p_i231591_3_);
        this.level = p_i231591_2_.player.level();
    }

    public FletchingTableMenu(int i, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        this(i, playerInventory, ContainerLevelAccess.NULL);
    }

    @Override
    protected boolean isValidBlock(BlockState p_230302_1_) {
        return p_230302_1_.is(Blocks.FLETCHING_TABLE);
    }

    @Override
    protected boolean mayPickup(@NotNull Player p_230303_1_, boolean p_230303_2_) {
        return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
    }

    @Override
    protected void onTake(@NotNull Player player, ItemStack itemStack) {
        itemStack.onCraftedBy(player.level(), player, itemStack.getCount());
        this.resultSlots.awardUsedRecipes(player, Collections.singletonList(itemStack));

        assert selectedRecipe != null;
        this.shrinkStackInSlot(0, Math.min(inputSlots.getItem(0).getCount(), selectedRecipe.getMaxCraftingAmount()));

        this.shrinkStackInSlot(1, 1);
        this.access.execute((level, blockPos) -> level.levelEvent(1044, blockPos, 0));
    }

    private void shrinkStackInSlot(int slot, int count) {
        ItemStack itemstack = this.inputSlots.getItem(slot);
        itemstack.shrink(count);
        this.inputSlots.setItem(slot, itemstack);
    }

    @Override
    public void createResult() {
        Optional<IFletchingRecipe> optional = this.level.getRecipeManager().getRecipeFor(ECRecipeSerializerInit.FLETCHING_TYPE.get(), this.inputSlots, this.level);
        if (optional.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.selectedRecipe = optional.get();
            ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots, this.level.registryAccess());
            this.resultSlots.setRecipeUsed(this.selectedRecipe);
            this.resultSlots.setItem(0, itemstack);
        }

    }

    @Override
    protected @NotNull ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 27, 47, (p_266635_) -> true)
                .withSlot(1, 76, 47, (p_266634_) -> true)
                .withResultSlot(2, 134, 47).build();
    }

    @Override
    public boolean canTakeItemForPickAll(@NotNull ItemStack p_94530_1_, Slot p_94530_2_) {
        return p_94530_2_.container != this.resultSlots && super.canTakeItemForPickAll(p_94530_1_, p_94530_2_);
    }
}

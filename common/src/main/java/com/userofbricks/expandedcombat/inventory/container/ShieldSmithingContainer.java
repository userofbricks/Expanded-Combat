package com.userofbricks.expandedcombat.inventory.container;

import com.userofbricks.expandedcombat.item.recipes.ShieldSmithingRecipie;
import com.userofbricks.expandedcombat.mixin.AbstractContainerMenuAccessor;
import com.userofbricks.expandedcombat.registries.ECContainers;
import com.userofbricks.expandedcombat.registries.ECRecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ShieldSmithingContainer extends AbstractContainerMenu {
    protected final ResultContainer resultSlots = new ResultContainer();
    protected final Container inputSlots = new SimpleContainer(6) {
        public void setChanged() {
            super.setChanged();
            ShieldSmithingContainer.this.slotsChanged(this);
        }
    };

    public final ContainerLevelAccess access;
    protected final Player player;
    private final Level level;

    private ShieldSmithingRecipie selectedRecipe;
    private final List<ShieldSmithingRecipie> recipes;

    public ShieldSmithingContainer(int id, Inventory playerInventory, ContainerLevelAccess iWorldPosCallable) {
        super(ECContainers.SHIELD_SMITHING.get(), id);
        this.slots.clear();
        ((AbstractContainerMenuAccessor)this).getLastSlots().clear();
        this.access = iWorldPosCallable;
        this.player = playerInventory.player;
        this.level = playerInventory.player.level;
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(ECRecipeSerializers.SHIELD_TYPE);
        this.addSlot(new Slot(this.inputSlots, 0, 27, 47) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem().getMaxStackSize() == 1 && super.mayPlace(stack);
            }
        });
        this.addSlot(new Slot(this.inputSlots, 1, 67, 29));
        this.addSlot(new Slot(this.inputSlots, 2, 85, 29));
        this.addSlot(new Slot(this.inputSlots, 3, 76, 47));
        this.addSlot(new Slot(this.inputSlots, 4, 67, 65));
        this.addSlot(new Slot(this.inputSlots, 5, 85, 65));
        this.addSlot(new Slot(this.resultSlots, 6, 134, 47) {
            public boolean mayPlace(ItemStack p_75214_1_) {
                return false;
            }

            public boolean mayPickup(Player p_82869_1_) {
                return ShieldSmithingContainer.this.mayPickup();
            }

            public void onTake(Player p_190901_1_, ItemStack p_190901_2_) {
                ShieldSmithingContainer.this.onTake(p_190901_1_, p_190901_2_);
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public ShieldSmithingContainer(int i, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
       this(i, playerInventory, ContainerLevelAccess.NULL);
    }

    public ShieldSmithingContainer(int i, Inventory inventory) {
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    protected boolean mayPickup() {
        return this.selectedRecipe.matches(this.inputSlots, this.level);
    }

    protected ItemStack onTake(Player p_230301_1_, ItemStack p_230301_2_) {
        p_230301_2_.onCraftedBy(p_230301_1_.level, p_230301_1_, p_230301_2_.getCount());
        this.resultSlots.awardUsedRecipes(p_230301_1_);
        this.inputSlots.setItem(0, ItemStack.EMPTY);
        this.shrinkStackInSlot(1);
        this.shrinkStackInSlot(2);
        this.shrinkStackInSlot(3);
        this.shrinkStackInSlot(4);
        this.shrinkStackInSlot(5);
        this.access.execute((p_234653_0_, p_234653_1_) -> p_234653_0_.levelEvent(1044, p_234653_1_, 0));
        return p_230301_2_;
    }

    private void shrinkStackInSlot(int slot) {
        ItemStack itemstack = this.inputSlots.getItem(slot);
        itemstack.shrink(slot);
        this.inputSlots.setItem(slot, itemstack);
    }

    public void createResult() {
        List<ShieldSmithingRecipie> list = this.level.getRecipeManager().getRecipesFor(ECRecipeSerializers.SHIELD_TYPE, this.inputSlots, this.level);
        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.selectedRecipe = list.get(0);
            ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots);
            this.resultSlots.setRecipeUsed(this.selectedRecipe);
            this.resultSlots.setItem(0, itemstack);
        }
    }

    public void slotsChanged(Container p_75130_1_) {
        super.slotsChanged(p_75130_1_);
        if (p_75130_1_ == this.inputSlots) {
            this.createResult();
        }

    }

    @Override
    public void removed(Player p_75134_1_) {
        super.removed(p_75134_1_);
        this.access.execute((p_234647_2_, p_234647_3_) -> this.clearContainer(p_75134_1_, this.inputSlots));
    }

    public boolean stillValid(Player p_75145_1_) {
        return this.access.evaluate((p_234646_2_, p_234646_3_) -> p_75145_1_.distanceToSqr((double)p_234646_3_.getX() + 0.5D, (double)p_234646_3_.getY() + 0.5D, (double)p_234646_3_.getZ() + 0.5D) <= 64.0D, true);
    }

    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack p_241210_1_) {
        return this.recipes.stream().anyMatch((p_241444_1_) -> p_241444_1_.isAdditionIngredient(p_241210_1_));
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack p_94530_1_, Slot p_94530_2_) {
        return p_94530_2_.container != this.resultSlots && super.canTakeItemForPickAll(p_94530_1_, p_94530_2_);
    }

    public ItemStack quickMoveStack(Player p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_82846_2_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_82846_2_ == 6) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (p_82846_2_ != 0 && p_82846_2_ != 1 && p_82846_2_ != 2 && p_82846_2_ != 3 && p_82846_2_ != 4 && p_82846_2_ != 5) {
                if (p_82846_2_ < 43) {
                    int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
                    if (!this.moveItemStackTo(itemstack1, i, 6, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 7, 43, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_82846_1_, itemstack1);
        }

        return itemstack;
    }
}

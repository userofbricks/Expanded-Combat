package com.userofbricks.expandedcombat.inventory.container;

import com.userofbricks.expandedcombat.item.recipes.RecipeSerializerInit;
import com.userofbricks.expandedcombat.item.recipes.ShieldSmithingRecipie;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class ShieldSmithingContainer extends Container {
    protected final CraftResultInventory resultSlots = new CraftResultInventory();
    protected final IInventory inputSlots = new Inventory(6) {
        public void setChanged() {
            super.setChanged();
            ShieldSmithingContainer.this.slotsChanged(this);
        }
    };

    public final IWorldPosCallable access;
    protected final PlayerEntity player;
    private final World level;

    private ShieldSmithingRecipie selectedRecipe;
    private final List<ShieldSmithingRecipie> recipes;

    public ShieldSmithingContainer(int id, PlayerInventory playerInventory, IWorldPosCallable iWorldPosCallable) {
        super(ECContainers.SHIELD_SMITHING.get(), id);
        this.slots.clear();
        this.lastSlots.clear();
        this.access = iWorldPosCallable;
        this.player = playerInventory.player;
        this.level = playerInventory.player.level;
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(RecipeSerializerInit.SHIELD_TYPE);
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

            public boolean mayPickup(PlayerEntity p_82869_1_) {
                return ShieldSmithingContainer.this.mayPickup();
            }

            @Nonnull
            public ItemStack onTake(PlayerEntity p_190901_1_, ItemStack p_190901_2_) {
                return ShieldSmithingContainer.this.onTake(p_190901_1_, p_190901_2_);
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

    public ShieldSmithingContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
       this(i, playerInventory, IWorldPosCallable.NULL);
    }

    protected boolean mayPickup() {
        return this.selectedRecipe.matches(this.inputSlots, this.level);
    }

    @Nonnull
    protected ItemStack onTake(PlayerEntity p_230301_1_, ItemStack p_230301_2_) {
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
        List<ShieldSmithingRecipie> list = this.level.getRecipeManager().getRecipesFor(RecipeSerializerInit.SHIELD_TYPE, this.inputSlots, this.level);
        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.selectedRecipe = list.get(0);
            ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots);
            this.resultSlots.setRecipeUsed(this.selectedRecipe);
            this.resultSlots.setItem(0, itemstack);
        }
    }

    public void slotsChanged(IInventory p_75130_1_) {
        super.slotsChanged(p_75130_1_);
        if (p_75130_1_ == this.inputSlots) {
            this.createResult();
        }

    }

    @Override
    public void removed(PlayerEntity p_75134_1_) {
        super.removed(p_75134_1_);
        this.access.execute((p_234647_2_, p_234647_3_) -> this.clearContainer(p_75134_1_, p_234647_2_, this.inputSlots));
    }

    @Override
    protected void clearContainer(PlayerEntity p_193327_1_, World p_193327_2_, IInventory p_193327_3_) {
        super.clearContainer(p_193327_1_, p_193327_2_, p_193327_3_);
    }

    public boolean stillValid(PlayerEntity p_75145_1_) {
        return this.access.evaluate((p_234646_2_, p_234646_3_) -> p_75145_1_.distanceToSqr((double)p_234646_3_.getX() + 0.5D, (double)p_234646_3_.getY() + 0.5D, (double)p_234646_3_.getZ() + 0.5D) <= 64.0D, true);
    }

    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack p_241210_1_) {
        return this.recipes.stream().anyMatch((p_241444_1_) -> p_241444_1_.isAdditionIngredient(p_241210_1_));
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack p_94530_1_, Slot p_94530_2_) {
        return p_94530_2_.container != this.resultSlots && super.canTakeItemForPickAll(p_94530_1_, p_94530_2_);
    }

    @Nonnull
    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
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

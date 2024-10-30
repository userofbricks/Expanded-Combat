package com.userofbricks.expanded_combat.inventory.container;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.PlacementInShield;
import com.userofbricks.expanded_combat.init.DataMaps;
import com.userofbricks.expanded_combat.init.ECContainers;
import com.userofbricks.expanded_combat.init.ECRecipeInit;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import com.userofbricks.expanded_combat.item.recipes.IShieldSmithingRecipe;
import com.userofbricks.expanded_combat.item.recipes.ShieldSmithingRecipeInput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class ShieldSmithingMenu extends ItemCombinerMenu {
    private final Level level;
    private RecipeHolder<IShieldSmithingRecipe> selectedRecipe;
    private static final Supplier<ItemCombinerMenuSlotDefinition> slotDefinition = () -> ItemCombinerMenuSlotDefinition.create()
            .withSlot(0, 27, 47, stack -> stack.get(ItemDataComponents.SHIELD_MATERIALS) != null)
            .withSlot(1, 67, 29, stack -> stack.getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP) != null)
            .withSlot(2, 85, 29, stack -> stack.getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP) != null)
            .withSlot(3, 76, 47, stack -> {
                Material value = stack.getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
                return value != null && value.defense().placementInShield() == PlacementInShield.ALL;
            })
            .withSlot(4, 67, 65, stack -> stack.getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP) != null)
            .withSlot(5, 86, 65, stack -> stack.getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP) != null)
            .withResultSlot(6, 134, 47)
            .build();

    public ShieldSmithingMenu(int id, Inventory playerInventory, ContainerLevelAccess access) {
        super(ECContainers.SHIELD_SMITHING.get(), id, playerInventory, access, slotDefinition.get());
        this.level = playerInventory.player.level();
    }

    public ShieldSmithingMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, ContainerLevelAccess.NULL);
    }

    @Override
    protected boolean isValidBlock(BlockState pState) {
        return true; //pState.is(Blocks.SMITHING_TABLE);
    }

    @Override
    protected boolean mayPickup(Player pPlayer, boolean pHasStack) {
        return this.selectedRecipe != null && this.selectedRecipe.value().matches(
                new ShieldSmithingRecipeInput(
                        inputSlots.getItem(0), inputSlots.getItem(1), inputSlots.getItem(2), inputSlots.getItem(3), inputSlots.getItem(4), inputSlots.getItem(5)
                ),
                this.level
        );
    }

    /**
     * shrink itemStacks from each input slot when
     */
    protected void onTake(Player player, ItemStack itemStack) {
        itemStack.onCraftedBy(player.level(), player, itemStack.getCount());
        this.resultSlots.awardUsedRecipes(player, Collections.singletonList(itemStack));
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.shrinkStackInSlot(2);
        this.shrinkStackInSlot(3);
        this.shrinkStackInSlot(4);
        this.shrinkStackInSlot(5);
        this.access.execute((p_234653_0_, p_234653_1_) -> p_234653_0_.levelEvent(1044, p_234653_1_, 0));
    }

    /**
     * shrinks stack in slot by one
     */
    private void shrinkStackInSlot(int slot) {
        ItemStack itemstack = this.inputSlots.getItem(slot);
        if (!itemstack.isEmpty()) {
            itemstack.shrink(1);
            this.inputSlots.setItem(slot, itemstack);
        }
    }

    private ShieldSmithingRecipeInput createRecipeInput() {
        return new ShieldSmithingRecipeInput(
                inputSlots.getItem(0), inputSlots.getItem(1), inputSlots.getItem(2), inputSlots.getItem(3), inputSlots.getItem(4), inputSlots.getItem(5)
        );
    }

    /**
     * takes the input slots and matches it to the recipe and then asks the recipe to create its result
     */
    public void createResult() {
        ShieldSmithingRecipeInput shieldSmithingrecipeinput = this.createRecipeInput();
        Optional<RecipeHolder<IShieldSmithingRecipe>> optional;
        if (this.level instanceof ServerLevel serverlevel) {
            optional = serverlevel.recipeAccess().getRecipeFor(ECRecipeInit.SHIELD_TYPE.get(), shieldSmithingrecipeinput, serverlevel);
        } else {
            optional = Optional.empty();
        }

        optional.ifPresentOrElse(recipe -> {
            ItemStack itemstack = recipe.value().assemble(shieldSmithingrecipeinput, this.level.registryAccess());
            this.resultSlots.setRecipeUsed(recipe);
            this.resultSlots.setItem(0, itemstack);
        }, () -> {
            this.resultSlots.setRecipeUsed(null);
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        });
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack p_94530_1_, Slot p_94530_2_) {
        return p_94530_2_.container != this.resultSlots && super.canTakeItemForPickAll(p_94530_1_, p_94530_2_);
    }
}

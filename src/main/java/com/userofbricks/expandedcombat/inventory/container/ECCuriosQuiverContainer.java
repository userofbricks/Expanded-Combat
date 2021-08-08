package com.userofbricks.expandedcombat.inventory.container;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CosmeticCurioSlot;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.network.NetworkHandler;
import top.theillusivec4.curios.common.network.client.CPacketScroll;
import top.theillusivec4.curios.common.network.server.SPacketScroll;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Map;

public class ECCuriosQuiverContainer extends InventoryMenu {

    private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[] {
            InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS,
            InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET};
    private static final EquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EquipmentSlot[] {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS,
            EquipmentSlot.FEET};

    public final LazyOptional<ICuriosItemHandler> curiosHandler;

    private final Player player;
    private final boolean isLocalWorld;

    private int lastScrollIndex;
    private boolean cosmeticColumn;

    public ECCuriosQuiverContainer(int windowId, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        this(windowId, playerInventory);
    }

    public ECCuriosQuiverContainer(int windowId, Inventory playerInventory) {
        super(playerInventory, playerInventory.player.level.isClientSide, playerInventory.player);
        this.menuType = ECContainers.EC_QUIVER_CURIOS.get();
        this.containerId = windowId;
        this.lastSlots.clear();
        this.slots.clear();
        this.player = playerInventory.player;
        this.isLocalWorld = this.player.level.isClientSide;
        this.curiosHandler = CuriosApi.getCuriosHelper().getCuriosHandler(this.player);

        //armor slots
        for(int i = 0; i < 4; ++i) {
            final EquipmentSlot equipmentslottype = VALID_EQUIPMENT_SLOTS[i];
            this.addSlot(new Slot(playerInventory, 36 + (3 - i), 8, 8 + i * 18) {
                public int getMaxStackSize() {
                    return 1;
                }

                public boolean mayPlace(@Nonnull ItemStack stack) {
                    return stack.canEquip(equipmentslottype, ECCuriosQuiverContainer.this.player);
                }

                public boolean mayPickup(@Nonnull Player playerIn) {
                    ItemStack itemstack = this.getItem();
                    return (itemstack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.mayPickup(playerIn);
                }

                @OnlyIn(Dist.CLIENT)
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, ECCuriosQuiverContainer.ARMOR_SLOT_TEXTURES[equipmentslottype.getIndex()]);
                }
            });
        }

        //main inventory
        for (int l = 0; l < 3; ++l) {

            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        //hot bar
        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }

        //offhand
        this.addSlot(new Slot(playerInventory, 40, 77, 62) {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

        //curios slots
        this.curiosHandler.ifPresent(curios -> {
            Map<String, ICurioStacksHandler> curioMap = curios.getCurios();
            int slots = 0;
            int yOffset = 12;

            for (String identifier : curioMap.keySet()) {
                ICurioStacksHandler stacksHandler = curioMap.get(identifier);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();

                if (identifier.equals("quiver") && stacksHandler.getSlots() > 0) {
                    this.addSlot(new CurioSlot(this.player, stackHandler, 0, identifier, 77, 18, stacksHandler.getRenders()) {
                        public boolean mayPickup(@Nonnull Player playerIn) {
                            return false;
                        }
                        public boolean mayPlace(@Nonnull ItemStack stack) {
                            return false;
                        }
                    });
                }
                if (identifier.equals("arrows") && stacksHandler.getSlots() > 0) {
                    int slotLeft = 98;
                    int slotTop = 18;
                    int rowCount = 0;
                    for (int i = 0; i < stacksHandler.getSlots(); ++i ) {
                        this.addSlot(new CurioSlot(this.player, stackHandler, i, identifier, slotLeft, slotTop, stacksHandler.getRenders()));
                        ++rowCount;
                        if (rowCount == 4) {
                            slotTop += 18;
                            slotLeft = 98;
                            rowCount = 0;
                        } else {
                            slotLeft +=18;
                        }
                    }
                }

                if (stacksHandler.isVisible()) {

                    for (int i = 0; i < stackHandler.getSlots() && slots < 8; i++) {
                        this.addSlot(new CurioSlot(this.player, stackHandler, i, identifier, -18, yOffset,
                                stacksHandler.getRenders()));

                        if (stacksHandler.hasCosmetic()) {
                            IDynamicStackHandler cosmeticHandler = stacksHandler.getCosmeticStacks();
                            this.cosmeticColumn = true;
                            this.addSlot(
                                    new CosmeticCurioSlot(this.player, cosmeticHandler, i, identifier, -37, yOffset));
                        }
                        yOffset += 18;
                        slots++;
                    }
                }
            }
        });
        this.scrollToIndex(0);
    }

    public boolean hasCosmeticColumn() {
        return this.cosmeticColumn;
    }

    public void scrollToIndex(int indexIn) {
        this.curiosHandler.ifPresent((curios) -> {
            Map<String, ICurioStacksHandler> curioMap = curios.getCurios();
            int slots = 0;
            int yOffset = 12;
            int index = 0;
            int arrows = curioMap.get("arrows").getSlots();
            int quiver = curioMap.get("quiver").getSlots();
            this.slots.subList(41 + arrows + quiver, this.slots.size()).clear();
            if (this.lastSlots != null) {
                this.lastSlots.subList(41 + arrows + quiver, this.lastSlots.size()).clear();
            }

            Iterator var7 = curioMap.keySet().iterator();

            while(true) {
                String identifier;
                ICurioStacksHandler stacksHandler;
                IDynamicStackHandler stackHandler;
                do {
                    if (!var7.hasNext()) {
                        if (!this.isLocalWorld) {
                            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> {
                                return (ServerPlayer)this.player;
                            }), new SPacketScroll(this.containerId, indexIn));
                        }

                        this.lastScrollIndex = indexIn;
                        return;
                    }

                    identifier = (String)var7.next();
                    stacksHandler = curioMap.get(identifier);
                    stackHandler = stacksHandler.getStacks();
                } while(!stacksHandler.isVisible());

                for(int i = 0; i < stackHandler.getSlots() && slots < 8; ++i) {
                    if (index >= indexIn) {
                        this.addSlot(new CurioSlot(this.player, stackHandler, i, identifier, -18, yOffset, stacksHandler.getRenders()));
                        if (stacksHandler.hasCosmetic()) {
                            IDynamicStackHandler cosmeticHandler = stacksHandler.getCosmeticStacks();
                            this.cosmeticColumn = true;
                            this.addSlot(new CosmeticCurioSlot(this.player, cosmeticHandler, i, identifier, -37, yOffset));
                        }

                        yOffset += 18;
                        ++slots;
                    }

                    ++index;
                }
            }
        });
    }

    public void scrollTo(float pos) {
        this.curiosHandler.ifPresent((curios) -> {
            int k = curios.getVisibleSlots() - 8;
            int j = (int)((double)(pos * (float)k) + 0.5D);
            if (j < 0) {
                j = 0;
            }

            if (j != this.lastScrollIndex) {
                if (this.isLocalWorld) {
                    NetworkHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CPacketScroll(this.containerId, j));
                }

            }
        });
    }

    public void slotsChanged(@Nonnull Container inventoryIn) {}


    public void removed(@Nonnull Player p_75134_1_) {}

    public boolean canScroll() {
        return this.curiosHandler.map((curios) -> curios.getVisibleSlots() > 8 ? 1 : 0).orElse(0) == 1;
    }

    public boolean stillValid(@Nonnull Player playerIn) {
        return true;
    }

    @Nonnull
    public ItemStack quickMoveStack(@Nonnull Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            EquipmentSlot entityequipmentslot = Mob.getEquipmentSlotForItem(itemstack);
            if (index < 4) {
                if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (entityequipmentslot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(3 - entityequipmentslot.getIndex()).hasItem()) {
                int i = 3 - entityequipmentslot.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 41 && !CuriosApi.getCuriosHelper().getCurioTags(itemstack.getItem()).isEmpty()) {
                if (!this.moveItemStackTo(itemstack1, 41, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (entityequipmentslot == EquipmentSlot.OFFHAND && !this.slots.get(41).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 40, 41, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 31) {
                if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 40) {
                if (!this.moveItemStackTo(itemstack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
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
        }

        return itemstack;
    }

    @Nonnull
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    public void fillCraftSlotsStackedContents(@Nonnull StackedContents itemHelperIn) {}

    public void clearCraftingContent() {}

    public boolean recipeMatches(Recipe<? super CraftingContainer> recipeIn) { return false; }

    public int getResultSlotIndex() {
        return -1;
    }

    public int getGridWidth() { return 0; }

    public int getGridHeight() { return 0; }

    public int getSize() { return 0; }
}

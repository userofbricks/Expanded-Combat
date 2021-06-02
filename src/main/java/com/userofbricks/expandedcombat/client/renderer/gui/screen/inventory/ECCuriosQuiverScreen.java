package com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.userofbricks.expandedcombat.client.KeyRegistry;
import com.userofbricks.expandedcombat.inventory.container.ECCuriosQuiverContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.client.CuriosClientConfig;
import top.theillusivec4.curios.client.gui.RenderButton;
import top.theillusivec4.curios.common.inventory.CosmeticCurioSlot;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.network.client.CPacketToggleRender;

import javax.annotation.Nonnull;

public class ECCuriosQuiverScreen  extends ContainerScreen<ECCuriosQuiverContainer> {
    public static final ResourceLocation CURIO_INVENTORY = new ResourceLocation("curios", "textures/gui/inventory.png");
    public static final ResourceLocation QUIVER_INVENTORY = new ResourceLocation("expanded_combat", "textures/gui/container/quiver.png");
    private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    private static float currentScroll;
    public boolean hasScrollBar;
    private CuriosButton buttonCurios;
    private QuiverButton buttonQuiver;
    private boolean isScrolling;
    private boolean buttonClicked;
    private boolean isRenderButtonHovered;

    public ECCuriosQuiverScreen(ECCuriosQuiverContainer curiosContainer, PlayerInventory playerInventory, ITextComponent title) {
        super(curiosContainer, playerInventory, title);
        this.passEvents = true;
    }

    public static Tuple<Integer, Integer> getButtonOffset(boolean isCuriosButton) {
        int x = 0;
        int y = 0;
        if (isCuriosButton) {
            CuriosClientConfig.Client client = CuriosClientConfig.CLIENT;
            CuriosClientConfig.Client.ButtonCorner corner = client.buttonCorner.get();
            x = x + corner.getXoffset() + client.buttonXOffset.get();
            y = y + corner.getYoffset() + client.buttonYOffset.get();
        } else {
            //TODO add Configs
            if (CuriosClientConfig.CLIENT.buttonCorner.get() == CuriosClientConfig.Client.ButtonCorner.TOP_RIGHT) {
                x = x + 61;
                y = y + -61;
            } else {
                x = x + 61;
                y = y + -75;
            }
        }

        return new Tuple<>(x, y);
    }

    public void init() {
        super.init();
        if (this.minecraft != null) {
            if (this.minecraft.player != null) {
                this.hasScrollBar = CuriosApi.getCuriosHelper().getCuriosHandler(this.minecraft.player).map((handler) -> handler.getVisibleSlots() > 8).orElse(false);
                if (this.hasScrollBar) {
                    this.menu.scrollTo(currentScroll);
                }
            }

            Tuple<Integer, Integer> curiosOffsets = getButtonOffset(true);
            Tuple<Integer, Integer> quiverOffsets = getButtonOffset(false);
            this.buttonCurios = new CuriosButton(this, this.getGuiLeft() + curiosOffsets.getA(), this.height / 2 + curiosOffsets.getB(), 14, 14, 50, 0, 14, CURIO_INVENTORY);
            this.addButton(this.buttonCurios);
            this.buttonQuiver = new QuiverButton(this, this.getGuiLeft() + quiverOffsets.getA(), this.height / 2 + quiverOffsets.getB(), 14, 14, 194, 0, 14, QUIVER_INVENTORY);
            this.addButton(this.buttonQuiver);

            this.updateRenderButtons();
        }
    }

    public void updateRenderButtons() {
        this.buttons.removeIf((widget) -> widget instanceof RenderButton);
        this.children.removeIf((widget) -> widget instanceof RenderButton);

        for (Slot inventorySlot : this.menu.slots) {
            if (inventorySlot instanceof CurioSlot && !(inventorySlot instanceof CosmeticCurioSlot)) {
                this.addButton(new RenderButton((CurioSlot) inventorySlot, this.leftPos + inventorySlot.x + 11, this.topPos + inventorySlot.y - 3, 8, 8, 75, 0, 8, CURIO_INVENTORY, (button) -> {
                    top.theillusivec4.curios.common.network.NetworkHandler.INSTANCE
                            .send(PacketDistributor.SERVER.noArg(), new CPacketToggleRender(((CurioSlot) inventorySlot).getIdentifier(), inventorySlot.getSlotIndex()));
                }));
            }
        }

    }

    public void tick() {
        super.tick();
    }

    private boolean inScrollBar(double mouseX, double mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        int k = i - 34;
        int l = j + 12;
        int i1 = k + 14;
        int j1 = l + 139;
        if (this.menu.hasCosmeticColumn()) {
            i1 -= 19;
            k -= 19;
        }

        return mouseX >= (double)k && mouseY >= (double)l && mouseX < (double)i1 && mouseY < (double)j1;
    }

    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        boolean isButtonHovered = false;

        for (Widget button : this.buttons) {
            if (button instanceof RenderButton) {
                ((RenderButton) button).renderButtonOverlay(matrixStack, mouseX, mouseY, partialTicks);
                if (button.isHovered()) {
                    isButtonHovered = true;
                }
            }
        }

        this.isRenderButtonHovered = isButtonHovered;
        ClientPlayerEntity clientPlayer = Minecraft.getInstance().player;
        if (!this.isRenderButtonHovered && clientPlayer != null && clientPlayer.inventory.getCarried().isEmpty() && this.getSlotUnderMouse() != null) {
            Slot slot = this.getSlotUnderMouse();
            if (slot instanceof CurioSlot && !slot.hasItem()) {
                CurioSlot slotCurio = (CurioSlot)slot;
                this.renderTooltip(matrixStack, new StringTextComponent(slotCurio.getSlotName()), mouseX, mouseY);
            }
        }

        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    protected void renderTooltip(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY) {
        Minecraft mc = this.minecraft;
        if (mc != null) {
            ClientPlayerEntity clientPlayer = mc.player;
            if (clientPlayer != null && clientPlayer.inventory.getCarried().isEmpty()) {
                if (this.isRenderButtonHovered) {
                    this.renderTooltip(matrixStack, new TranslationTextComponent("gui.curios.toggle"), mouseX, mouseY);
                } else if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
                    this.renderTooltip(matrixStack, this.hoveredSlot.getItem(), mouseX, mouseY);
                }
            }
        }

    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (KeyRegistry.openQuiver.isActiveAndMatches(InputMappings.getKey(p_keyPressed_1_, p_keyPressed_2_))) {
            ClientPlayerEntity playerEntity = this.getMinecraft().player;
            if (playerEntity != null) {
                playerEntity.closeContainer();
            }

            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }

    protected void renderLabels(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null) {
            this.font.draw(matrixStack, this.title, 97.0F, 6.0F, 4210752);
        }

    }

    protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.minecraft.getTextureManager().bind(QUIVER_INVENTORY);
            int i = this.leftPos;
            int j = this.topPos;
            this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
            InventoryScreen.renderEntityInInventory(i + 51, j + 75, 30, (float)(i + 51) - (float)mouseX, (float)(j + 75 - 50) - (float)mouseY, this.minecraft.player);
            for (Slot slot : this.menu.slots) {
                if (slot instanceof CurioSlot && ((CurioSlot)slot).getIdentifier().equals("arrows")) {
                    int x = this.leftPos + slot.x - 1;
                    int y = this.topPos + slot.y - 1;
                    this.getMinecraft().getTextureManager().bind(QUIVER_INVENTORY);
                    this.blit(matrixStack, x, y, 176, 0, 18, 18);
                }
            }
            CuriosApi.getCuriosHelper().getCuriosHandler(this.minecraft.player).ifPresent((handler) -> {
                int slotCount = handler.getVisibleSlots();
                if (slotCount > 0) {
                    int upperHeight = 7 + Math.min(slotCount, 9) * 18;
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.getMinecraft().getTextureManager().bind(CURIO_INVENTORY);
                    int xTexOffset = 0;
                    int width = 27;
                    int xOffset = -26;
                    if (this.menu.hasCosmeticColumn()) {
                        xTexOffset = 92;
                        width = 46;
                        xOffset -= 19;
                    }

                    this.blit(matrixStack, i + xOffset, j + 4, xTexOffset, 0, width, upperHeight);
                    if (slotCount <= 8) {
                        this.blit(matrixStack, i + xOffset, j + 4 + upperHeight, xTexOffset, 151, width, 7);
                    } else {
                        this.blit(matrixStack, i + xOffset - 16, j + 4, 27, 0, 23, 158);
                        this.getMinecraft().getTextureManager().bind(CREATIVE_INVENTORY_TABS);
                        this.blit(matrixStack, i + xOffset - 8, j + 12 + (int)(127.0F * currentScroll), 232, 0, 12, 15);
                    }

                    for (Slot slot : this.menu.slots) {
                        if (slot instanceof CosmeticCurioSlot) {
                            int x = this.leftPos + slot.x - 1;
                            int y = this.topPos + slot.y - 1;
                            this.getMinecraft().getTextureManager().bind(CURIO_INVENTORY);
                            this.blit(matrixStack, x, y, 138, 0, 18, 18);
                        }
                    }
                }
            });
        }

    }

    protected boolean isHovering(int rectX, int rectY, int rectWidth, int rectHeight, double pointX, double pointY) {
        if (this.isRenderButtonHovered) {
            return false;
        } else {
            return super.isHovering(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (this.inScrollBar(mouseX, mouseY)) {
            this.isScrolling = this.needsScrollBars();
            return true;
        } else {
            return super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public boolean mouseReleased(double mouseReleased1, double mouseReleased3, int mouseReleased5) {
        if (mouseReleased5 == 0) {
            this.isScrolling = false;
        }

        if (this.buttonClicked) {
            this.buttonClicked = false;
            return true;
        } else {
            return super.mouseReleased(mouseReleased1, mouseReleased3, mouseReleased5);
        }
    }

    public boolean mouseDragged(double pMouseDragged1, double pMouseDragged3, int pMouseDragged5, double pMouseDragged6, double pMouseDragged8) {
        if (this.isScrolling) {
            int i = this.topPos + 8;
            int j = i + 148;
            currentScroll = ((float)pMouseDragged3 - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            currentScroll = MathHelper.clamp(currentScroll, 0.0F, 1.0F);
            this.menu.scrollTo(currentScroll);
            return true;
        } else {
            return super.mouseDragged(pMouseDragged1, pMouseDragged3, pMouseDragged5, pMouseDragged6, pMouseDragged8);
        }
    }

    public boolean mouseScrolled(double pMouseScrolled1, double pMouseScrolled3, double pMouseScrolled5) {
        if (!this.needsScrollBars()) {
            return false;
        } else {
            int i = this.menu.curiosHandler.map(ICuriosItemHandler::getVisibleSlots).orElse(1);
            currentScroll = (float)((double)currentScroll - pMouseScrolled5 / (double)i);
            currentScroll = MathHelper.clamp(currentScroll, 0.0F, 1.0F);
           this.menu.scrollTo(currentScroll);
            return true;
        }
    }

    private boolean needsScrollBars() {
        return this.menu.canScroll();
    }

    protected void slotClicked(@Nonnull Slot slotIn, int slotId, int mouseButton, @Nonnull ClickType type) {
        super.slotClicked(slotIn, slotId, mouseButton, type);
    }

    public void removed() {
        super.removed();
    }
}

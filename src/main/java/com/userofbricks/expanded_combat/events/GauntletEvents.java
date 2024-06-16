package com.userofbricks.expanded_combat.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.userofbricks.expanded_combat.api.client.IGauntletRenderer;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import com.userofbricks.expanded_combat.plugins.CustomWeaponsPlugin;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.ApiStatus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;
import java.util.Optional;

import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;

@Mod.EventBusSubscriber(modid = "expanded_combat", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GauntletEvents
{
    public static void DamageGauntletEvent(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (player.isCreative()) return;
        LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(player);
        if(optionalCuriosInventory.resolve().isEmpty()) return;
        ICuriosItemHandler playerCuriosInventory = optionalCuriosInventory.resolve().get();
        List<SlotResult> slotResults = playerCuriosInventory.findCurios(itemStack -> itemStack.getItem() instanceof ECGauntletItem);
        if (slotResults.isEmpty()) return;
        for (SlotResult slotResult : slotResults) {
            ItemStack stack = slotResult.stack();
            SlotContext slotContext = slotResult.slotContext();
            if (stack.getItem() instanceof ECGauntletItem) {
                stack.hurtAndBreak(1, (LivingEntity) player, damager -> CuriosApi.broadcastCurioBreakEvent(slotContext));
            }
        }
    }
    @SubscribeEvent
    public static void moreDamageSources(LivingAttackEvent ev) {
        LivingEntity target = ev.getEntity();
        LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(target);
        if(optionalCuriosInventory.resolve().isEmpty()) return;
        ICuriosItemHandler entityCuriosInventory = optionalCuriosInventory.resolve().get();
        Optional<SlotResult> optionalSlotResult = entityCuriosInventory.findFirstCurio(CustomWeaponsPlugin.MAULERS.getGauntletEntry().get());
        if (optionalSlotResult.isPresent()) {
            SlotResult slotResult = optionalSlotResult.get();
            int charge = slotResult.stack().getOrCreateTag().getInt("charge");
            if (charge >= 20) {
                target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5*20, 2));
            } else {
                slotResult.stack().getOrCreateTag().putInt("charge", charge + 1);
            }
        }
    }

    @SubscribeEvent
    public static void pulOutArrow(PlayerInteractEvent.RightClickEmpty event) {
        Player player = event.getEntity();
        LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(player);
        if(optionalCuriosInventory.resolve().isEmpty()) return;
        ICuriosItemHandler playerCuriosInventory = optionalCuriosInventory.resolve().get();
        Optional<SlotResult> optionalSlotResult = playerCuriosInventory.findFirstCurio(CustomWeaponsPlugin.FIGHTER.getGauntletEntry().get());
        if (optionalSlotResult.isPresent() && player.getArrowCount() >= 1) {
            Optional<SlotResult> optionalQuiverSlotResult = playerCuriosInventory.findFirstCurio(stack -> stack.getItem() instanceof ECQuiverItem);
            if (optionalQuiverSlotResult.isPresent()) {
                ECQuiverItem quiverItem = (ECQuiverItem) optionalQuiverSlotResult.get().stack().getItem();
                IDynamicStackHandler arrowStackHandler = playerCuriosInventory.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
                int slots = arrowStackHandler.getSlots();
                boolean found = false;
                for (int s = 0; s < slots; s++) {
                    ItemStack currentStack = arrowStackHandler.getStackInSlot(s);
                    if ((currentStack.getItem() == Items.ARROW || currentStack.isEmpty()) && quiverItem.providedSlots > s) {
                        found = arrowStackHandler.insertItem(s, new ItemStack(Items.ARROW), false).isEmpty();
                    }
                }
                if (!found) {
                    player.getInventory().placeItemBackInInventory(new ItemStack(Items.ARROW));
                }
            } else {
                player.getInventory().placeItemBackInInventory(new ItemStack(Items.ARROW));
            }
            player.setArrowCount(player.getArrowCount() - 1);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public static void onRenderArm(RenderArmEvent event) {
        CuriosApi.getCuriosInventory(event.getPlayer()).ifPresent(handler -> {
            ICurioStacksHandler stacksHandler = handler.getCurios().get(SlotTypePreset.HANDS.getIdentifier());
            if (stacksHandler != null) {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                IDynamicStackHandler cosmeticStacks = stacksHandler.getCosmeticStacks();
                ItemStack stack = cosmeticStacks.getStackInSlot(0);
                boolean cosmetic = true;
                if (stack.isEmpty() && stacksHandler.getRenders().get(0)) {
                    stack = stacks.getStackInSlot(0);
                    cosmetic = false;
                }
                if (MinecraftForge.EVENT_BUS.post(new GauntletRenderFirstPersonEvent(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer(), event.getArm(), stack, cosmetic))) return;

                if (stack.getItem() instanceof ECGauntletItem ecGauntletItem) {
                    ICurioRenderer iCurioRenderer = ecGauntletItem.getGauntletRenderer().get();
                    if (iCurioRenderer instanceof IGauntletRenderer gauntletRenderer) {
                        gauntletRenderer.renderFirstPersonArm(stack, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer(), event.getArm(), stack.hasFoil());
                    }
                }
            }
        });
    }



    /**
     * Fired before the player's gauntlet is rendered in first person. This is a more targeted version of {@link RenderArmEvent},
     * and can be used to replace the rendering of the player's gauntlet.
     *
     * <p>This event is {@linkplain Cancelable cancellable}, and does not {@linkplain HasResult have a result}.
     * If this event is cancelled, then the gauntlet will not be rendered, however unlike in {@link RenderArmEvent} the arm will still render.</p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}.</p>
     */
    @Cancelable
    public static class GauntletRenderFirstPersonEvent extends Event {
        private final PoseStack poseStack;
        private final MultiBufferSource multiBufferSource;
        private final int packedLight;
        private final AbstractClientPlayer player;
        private final HumanoidArm arm;
        private final ItemStack gauntlet;
        private final boolean cosmetic;

        @ApiStatus.Internal
        public GauntletRenderFirstPersonEvent(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, AbstractClientPlayer player, HumanoidArm arm, ItemStack gauntlet, boolean cosmetic)
        {
            this.poseStack = poseStack;
            this.multiBufferSource = multiBufferSource;
            this.packedLight = packedLight;
            this.player = player;
            this.arm = arm;
            this.gauntlet = gauntlet;
            this.cosmetic = cosmetic;
        }

        /**
         * {@return the arm being rendered}
         */
        public HumanoidArm getArm()
        {
            return arm;
        }

        /**
         * {@return the pose stack used for rendering}
         */
        public PoseStack getPoseStack()
        {
            return poseStack;
        }

        /**
         * {@return the source of rendering buffers}
         */
        public MultiBufferSource getMultiBufferSource()
        {
            return multiBufferSource;
        }

        /**
         * {@return the amount of packed (sky and block) light for rendering}
         *
         * @see LightTexture
         */
        public int getPackedLight()
        {
            return packedLight;
        }

        /**
         * {@return the client player that is having their arm rendered} In general, this will be the same as
         * {@link net.minecraft.client.Minecraft#player}.
         */
        public AbstractClientPlayer getPlayer()
        {
            return player;
        }
        /**
         * {@return the gauntlet ItemStack being rendering}
         */
        public ItemStack getGauntlet() {
            return gauntlet;
        }
        /**
         * {@return weather the gauntlet ItemStack being rendering is cosmetic or not}
         */
        public boolean isCosmetic() {
            return cosmetic;
        }
    }
}

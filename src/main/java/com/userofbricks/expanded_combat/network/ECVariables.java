package com.userofbricks.expanded_combat.network;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ECVariables {

    public static int getArrowSlot(Entity entity) {
        return entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).arrowSlot;
    }
    public static int getKatanaArrowBlockNumber(Entity entity) {
        return entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).katanaArrowBlockNumber;
    }
    public static int getKatanaTimeSinceBlock(Entity entity) {
        return entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).katanaTimeSinceBlock;
    }

    public static void setArrowSlotTo(LivingEntity entity, int slot) {
        entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.arrowSlot = slot;
            capability.syncPlayerVariables(entity);
        });
    }

    public static void setKatanaTimeSinceBlock(LivingEntity entity, int ticks) {
        entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.katanaTimeSinceBlock = ticks;
            capability.syncPlayerVariables(entity);
        });
    }

    public static void setKatanaArrowBlockNumber(LivingEntity entity, int arrowBlocks) {
        entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.katanaArrowBlockNumber = arrowBlocks;
            capability.syncPlayerVariables(entity);
        });
    }


    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        ECNetworkHandler.register(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handler);
    }

    @SubscribeEvent
    public static void init(RegisterCapabilitiesEvent event) {
        event.register(PlayerVariables.class);
    }

    @Mod.EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.getEntity().level().isClientSide())
                event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            if (!event.getEntity().level().isClientSide())
                event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (!event.getEntity().level().isClientSide())
                event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            event.getOriginal().revive();
            PlayerVariables original = event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
            PlayerVariables clone = event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
            clone.arrowSlot = original.arrowSlot;
            clone.katanaArrowBlockNumber = original.katanaArrowBlockNumber;
            clone.katanaTimeSinceBlock = original.katanaTimeSinceBlock;
        }
    }

    public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    @Mod.EventBusSubscriber
    private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
                event.addCapability(new ResourceLocation("expanded_combat", "player_variables"), new PlayerVariablesProvider());
        }

        private final PlayerVariables playerVariables = new PlayerVariables();
        private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

        @Override
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
            return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
        }

        @Override
        public Tag serializeNBT() {
            return playerVariables.writeNBT();
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            playerVariables.readNBT(nbt);
        }
    }

    public static class PlayerVariables {
        public int arrowSlot = 0;
        public int katanaArrowBlockNumber = 0;
        public int katanaTimeSinceBlock = 0;

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer)
                ECNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
            else if (entity instanceof LocalPlayer localPlayer) {
                ECNetworkHandler.INSTANCE.send(ECNetworkHandler.LOCAL_PLAYER.with(() -> localPlayer), new PlayerVariablesSyncMessage(this));
            }
        }

        public Tag writeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("arrowSlot", arrowSlot);
            nbt.putInt("katanaArrowBlockNumber", katanaArrowBlockNumber);
            nbt.putInt("katanaTimeSinceBlock", katanaTimeSinceBlock);
            return nbt;
        }

        public void readNBT(Tag Tag) {
            CompoundTag nbt = (CompoundTag) Tag;
            arrowSlot = nbt.getInt("arrowSlot");
            katanaArrowBlockNumber = nbt.getInt("katanaArrowBlockNumber");
            katanaTimeSinceBlock = nbt.getInt("katanaTimeSinceBlock");
        }
    }

    public static class PlayerVariablesSyncMessage {
        public PlayerVariables data;

        public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
            this.data = new PlayerVariables();
            this.data.readNBT(buffer.readNbt());
        }

        public PlayerVariablesSyncMessage(PlayerVariables data) {
            this.data = data;
        }

        public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
            buffer.writeNbt((CompoundTag) message.data.writeNBT());
        }

        public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    assert Minecraft.getInstance().player != null;
                    PlayerVariables variables = Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
                    variables.arrowSlot = message.data.arrowSlot;
                    variables.katanaArrowBlockNumber = message.data.katanaArrowBlockNumber;
                    variables.katanaTimeSinceBlock = message.data.katanaTimeSinceBlock;
                } else {
                    ServerPlayer serverPlayer = context.getSender();
                    assert serverPlayer != null;
                    PlayerVariables variables = serverPlayer.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
                    variables.arrowSlot = message.data.arrowSlot;
                    variables.katanaArrowBlockNumber = message.data.katanaArrowBlockNumber;
                    variables.katanaTimeSinceBlock = message.data.katanaTimeSinceBlock;
                }
            });
            context.setPacketHandled(true);
        }
    }
}

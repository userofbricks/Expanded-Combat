package com.userofbricks.expanded_combat.network;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
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

import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ECVariables {

    public static int getStolenHealth(Entity entity) {
        return entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).stolenHealth;
    }
    public static int getAddedHealth(Entity entity) {
        return entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).addedHealth;
    }
    public static int getArrowSlot(Entity entity) {
        return entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).arrowSlot;
    }
    public static int getKatanaArrowBlockNumber(Entity entity) {
        return entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).katanaArrowBlockNumber;
    }
    public static int getKatanaTimeSinceBlock(Entity entity) {
        return entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).katanaTimeSinceBlock;
    }

    public static void addToStolenHealth(LivingEntity entity, int health) {
        entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.stolenHealth += health;
            capability.addedHealth += health;
            capability.syncPlayerVariables(entity);
        });
    }
    public static void reduceAddedHealth(LivingEntity entity, int health) {
        entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.addedHealth -= health;
            capability.stolenHealth = Math.max(capability.stolenHealth - health/4, 0);
            capability.syncPlayerVariables(entity);
        });
    }
    public static void setStolenHealth(LivingEntity entity, int health) {
        entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.stolenHealth = health;
            capability.syncPlayerVariables(entity);
        });
    }
    public static void setAddedHealth(LivingEntity entity, int health) {
        entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.addedHealth = health;
            capability.syncPlayerVariables(entity);
        });
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
        ECNetworkHandler.register(SavedDataSyncMessage.class, SavedDataSyncMessage::buffer, SavedDataSyncMessage::new, SavedDataSyncMessage::handler);
    }

    @SubscribeEvent
    public static void init(RegisterCapabilitiesEvent event) {
        event.register(PlayerVariables.class);
    }

    @Mod.EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.getEntity().level().isClientSide()) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getEntity());
            if (!event.getEntity().level().isClientSide()) {
                SavedData worlddata = WorldVariables.get(event.getEntity().level());
                if (worlddata != null)
                    ECNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new SavedDataSyncMessage(1, worlddata));
            }
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            if (!event.getEntity().level().isClientSide()) {
                PlayerVariables player = event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
                player.stolenHealth = Math.max(player.stolenHealth - 10, 0);
                player.addedHealth = 0;
                player.syncPlayerVariables(event.getEntity());
            }

        }

        @SubscribeEvent
        public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (!event.getEntity().level().isClientSide()) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getEntity());
            if (!event.getEntity().level().isClientSide()) {
                SavedData worlddata = WorldVariables.get(event.getEntity().level());
                if (worlddata != null)
                    ECNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new SavedDataSyncMessage(1, worlddata));
            }
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            event.getOriginal().revive();
            PlayerVariables original = event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
            PlayerVariables clone = event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
            clone.arrowSlot = original.arrowSlot;
            clone.katanaArrowBlockNumber = original.katanaArrowBlockNumber;
            clone.katanaTimeSinceBlock = original.katanaTimeSinceBlock;
            clone.stolenHealth = original.stolenHealth;
            clone.addedHealth = original.addedHealth;
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
        public int stolenHealth = 0;
        public int addedHealth = 0;

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
            nbt.putInt("stolenHealth", stolenHealth);
            nbt.putInt("addedHealth", addedHealth);
            return nbt;
        }

        public void readNBT(Tag Tag) {
            CompoundTag nbt = (CompoundTag) Tag;
            arrowSlot = nbt.getInt("arrowSlot");
            katanaArrowBlockNumber = nbt.getInt("katanaArrowBlockNumber");
            katanaTimeSinceBlock = nbt.getInt("katanaTimeSinceBlock");
            stolenHealth = nbt.getInt("stolenHealth");
            addedHealth = nbt.getInt("addedHealth");
        }
    }

    public static class WorldVariables extends SavedData {
        public static final String DATA_NAME = "examples_for_expanded_combat_worldvars";
        protected double heartstealerCount = 0;

        public static WorldVariables load(CompoundTag tag) {
            WorldVariables data = new WorldVariables();
            data.read(tag);
            return data;
        }

        public void read(CompoundTag nbt) {
            heartstealerCount = nbt.getDouble("heartstealerCount");
        }

        @Override
        public @NotNull CompoundTag save(CompoundTag nbt) {
            nbt.putDouble("heartstealerCount", heartstealerCount);
            return nbt;
        }

        public void syncData(LevelAccessor world) {
            this.setDirty();
            if (world instanceof Level level && !level.isClientSide())
                ECNetworkHandler.INSTANCE.send(PacketDistributor.DIMENSION.with(level::dimension), new SavedDataSyncMessage(1, this));
        }

        static WorldVariables clientSide = new WorldVariables();

        public static WorldVariables get(LevelAccessor world) {
            if (world instanceof ServerLevel level) {
                return level.getDataStorage().computeIfAbsent(WorldVariables::load, WorldVariables::new, DATA_NAME);
            } else {
                return clientSide;
            }
        }
        public static double getHeartStealerCount(LevelAccessor world) {
            return get(world).heartstealerCount;
        }
        public static double increaseHeartStealerCount(LevelAccessor world) {
            double count = get(world).heartstealerCount += 1;
            get(world).syncData(world);
            return count;
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
                    variables.stolenHealth = message.data.stolenHealth;
                    variables.addedHealth = message.data.addedHealth;
                } else {
                    ServerPlayer serverPlayer = context.getSender();
                    assert serverPlayer != null;
                    PlayerVariables variables = serverPlayer.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
                    variables.arrowSlot = message.data.arrowSlot;
                    variables.katanaArrowBlockNumber = message.data.katanaArrowBlockNumber;
                    variables.katanaTimeSinceBlock = message.data.katanaTimeSinceBlock;
                    variables.stolenHealth = message.data.stolenHealth;
                    variables.addedHealth = message.data.addedHealth;
                }
            });
            context.setPacketHandled(true);
        }
    }

    public static class SavedDataSyncMessage {
        public int type;
        public SavedData data;

        public SavedDataSyncMessage(FriendlyByteBuf buffer) {
            this.type = buffer.readInt();
            this.data = new WorldVariables();
            WorldVariables _worldvars = (WorldVariables) this.data;
            _worldvars.read(Objects.requireNonNull(buffer.readNbt()));
        }

        public SavedDataSyncMessage(int type, SavedData data) {
            this.type = type;
            this.data = data;
        }

        public static void buffer(SavedDataSyncMessage message, FriendlyByteBuf buffer) {
            buffer.writeInt(message.type);
            buffer.writeNbt(message.data.save(new CompoundTag()));
        }

        public static void handler(SavedDataSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    WorldVariables.clientSide = (WorldVariables) message.data;
                }
            });
            context.setPacketHandled(true);
        }
    }
}

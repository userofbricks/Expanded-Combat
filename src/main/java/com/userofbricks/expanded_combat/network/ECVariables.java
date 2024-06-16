package com.userofbricks.expanded_combat.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ECVariables {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        ECNetworkHandler.register(SavedDataSyncMessage.class, SavedDataSyncMessage::buffer, SavedDataSyncMessage::new, SavedDataSyncMessage::handler);
    }

    @EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.getEntity().level().isClientSide()) {
                SavedData worlddata = WorldVariables.get(event.getEntity().level());
                if (worlddata != null)
                    ECNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new SavedDataSyncMessage(1, worlddata));
            }
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

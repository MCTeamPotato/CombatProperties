package me.kall.combatproperties.network;

import me.kall.combatproperties.CombatProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ParticlePacket(int entity, byte particle) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ParticlePacket> CODEC = CustomPacketPayload.codec(ParticlePacket::toBytes, ParticlePacket::new);
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CombatProperties.MOD_ID, "particle");
    public static final Type<ParticlePacket> TYPE = new Type<>(ID);

    public ParticlePacket(@NotNull FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readByte());
    }

    public void toBytes(@NotNull FriendlyByteBuf buf) {
        buf.writeInt(this.entity);
        buf.writeByte(this.particle);
    }

    public static void handle(ParticlePacket packet, @NotNull IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            ClientLevel level = mc.level;
            if (level == null) return;
            Entity target = level.getEntity(packet.entity);
            if (target == null) return;
            ParticleOptions particle = ParticlesConstant.PARTICLES.get(packet.particle);
            if (particle == null) return;
            mc.particleEngine.createTrackingEmitter(target, particle);
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

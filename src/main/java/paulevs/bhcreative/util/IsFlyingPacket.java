package paulevs.bhcreative.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import paulevs.bhcreative.BHCreative;
import paulevs.bhcreative.mixin.server.ServerPlayerPacketHandlerAccessor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IsFlyingPacket extends Packet implements ManagedPacket<IsFlyingPacket> {
	public static final PacketType<IsFlyingPacket> TYPE = PacketType.builder(true, true, IsFlyingPacket::new).build();
	private static final String STATION_ID = StationAPI.NAMESPACE.id("id").toString();
	private static final Identifier ID = BHCreative.id("is_flying");
	private boolean flight;
	
	public IsFlyingPacket() {}
	
	public IsFlyingPacket(boolean flight) {
		this.flight = flight;
	}
	
	@Override
	public void read(DataInputStream stream) {
		try {
			flight = stream.readBoolean();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void write(DataOutputStream stream) {
		try {
			stream.writeBoolean(flight);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void apply(NetworkHandler handler) {
		if (handler instanceof ServerPlayNetworkHandler serverHandler) {
			ServerPlayerPacketHandlerAccessor accessor = (ServerPlayerPacketHandlerAccessor) serverHandler;
			ServerPlayerEntity player = accessor.creative_getServerPlayer();
			player.creative_setFlying(flight);
		}
	}
	
	@Override
	public int size() {
		return 1;
	}

	@Override
	public @NotNull PacketType<IsFlyingPacket> getType() {
		return TYPE;
	}
}

package paulevs.bhcreative.util;

import net.minecraft.entity.living.player.ServerPlayer;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import net.minecraft.server.network.ServerPlayerPacketHandler;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.bhcreative.BHCreative;
import paulevs.bhcreative.mixin.server.ServerPlayerPacketHandlerAccessor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IsFlyingPacket extends AbstractPacket implements IdentifiablePacket {
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
	public void apply(PacketHandler handler) {
		if (handler instanceof ServerPlayerPacketHandler serverHandler) {
			ServerPlayerPacketHandlerAccessor accessor = (ServerPlayerPacketHandlerAccessor) serverHandler;
			ServerPlayer player = accessor.creative_getServerPlayer();
			player.creative_setFlying(flight);
		}
	}
	
	@Override
	public int length() {
		return 1;
	}
	
	@Override
	public Identifier getId() {
		return ID;
	}
	
	public static void register() {
		IdentifiablePacket.register(ID, true, true, IsFlyingPacket::new);
	}
}

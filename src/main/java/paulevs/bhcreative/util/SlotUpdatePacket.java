package paulevs.bhcreative.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
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

public class SlotUpdatePacket extends Packet implements ManagedPacket<SlotUpdatePacket> {
	public static final PacketType<SlotUpdatePacket> TYPE = PacketType.builder(true, true, SlotUpdatePacket::new).build();
	private static final String STATION_ID = StationAPI.NAMESPACE.id("id").toString();
	private static final Identifier ID = BHCreative.id("update_slot");
	private int slot;
	private ItemStack stack;
	
	public SlotUpdatePacket() {}
	
	public SlotUpdatePacket(int slot, ItemStack stack) {
		this.stack = stack;
		this.slot = slot;
	}
	
	@Override
	public void read(DataInputStream stream) {
		stack = null;
		try {
			slot = stream.readShort();
			int count = Byte.toUnsignedInt(stream.readByte());
			if (count > 0) {
				int id = stream.readInt();
				int damage = stream.readInt();
				stack = new ItemStack(id, count, damage);
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void write(DataOutputStream stream) {
		try {
			stream.writeShort((short) slot);
			if (stack == null) {
				stream.writeByte(0);
				return;
			}
			stream.writeByte((byte) stack.count);
			if (stack.count > 0) {
				stream.writeInt(stack.itemId);
				stream.writeInt(stack.getDamage());
			}
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
			if (slot == -1) player.inventory.setCursorStack(stack);
			else player.inventory.setStack(slot, stack);
		}
	}
	
	@Override
	public int size() {
		return 11;
	}

	@Override
	public @NotNull PacketType<SlotUpdatePacket> getType() {
		return TYPE;
	}
}

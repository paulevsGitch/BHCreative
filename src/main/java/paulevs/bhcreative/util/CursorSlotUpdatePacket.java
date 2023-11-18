package paulevs.bhcreative.util;

import net.minecraft.entity.living.player.ServerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;
import net.minecraft.server.network.ServerPlayerPacketHandler;
import paulevs.bhcreative.mixin.server.ServerPlayerPacketHandlerAccessor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CursorSlotUpdatePacket extends AbstractPacket {
	private ItemStack stack;
	
	public CursorSlotUpdatePacket() {}
	
	public CursorSlotUpdatePacket(ItemStack stack) {
		this.stack = stack;
	}
	
	@Override
	public void read(DataInputStream stream) {
		stack = null;
		try {
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
	public void apply(PacketHandler handler) {
		if (handler instanceof ServerPlayerPacketHandler serverHandler) {
			ServerPlayerPacketHandlerAccessor accessor = (ServerPlayerPacketHandlerAccessor) serverHandler;
			ServerPlayer player = accessor.creative_getServerPlayer();
			player.inventory.setCursorItem(stack);
		}
	}
	
	@Override
	public int length() {
		return 9;
	}
}

package paulevs.bhcreative.mixin.server;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayNetworkHandler.class)
public interface ServerPlayerPacketHandlerAccessor {
	@Accessor("player")
	ServerPlayerEntity creative_getServerPlayer();
}

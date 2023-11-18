package paulevs.bhcreative.mixin.common;

import net.minecraft.packet.AbstractPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.util.CursorSlotUpdatePacket;

@Mixin(AbstractPacket.class)
@SuppressWarnings("rawtypes")
public abstract class AbstractPacketMixin {
	@Shadow static void register(int i, boolean server, boolean client, Class class_) {}
	
	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void creative_registerPacket(CallbackInfo info) {
		register(72, true, true, CursorSlotUpdatePacket.class);
	}
}

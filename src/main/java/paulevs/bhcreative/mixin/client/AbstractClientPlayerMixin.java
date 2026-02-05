package paulevs.bhcreative.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.util.IsFlyingPacket;

@Mixin(ClientPlayerEntity.class)
public abstract class AbstractClientPlayerMixin extends PlayerEntity {
	@Unique private long creative_timeout;
	@Unique private long creative_count;
	
	@Shadow protected Minecraft minecraft;
	
	public AbstractClientPlayerMixin(World arg) {
		super(arg);
	}
	
	@Inject(method = "shouldSuffocate", at = @At("HEAD"), cancellable = true)
	private void creative_getCanSuffocate(int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		if (this.creative_isCreative()) {
			info.setReturnValue(false);
			info.cancel();
		}
	}
	
	@Inject(method = "updateKey(IZ)V", at = @At("HEAD"))
	public void creative_onKeyPress(int key, boolean pressed, CallbackInfo info) {
		if (key != minecraft.options.jumpKey.code) return;
		if (!creative_isCreative()) return;
		
		if (pressed) {
			long time = System.currentTimeMillis();
			creative_timeout = time - creative_timeout;
			if (creative_count > 0 && creative_timeout < 500) {
				boolean flying = !creative_isFlying();
				creative_setFlying(flying);
				if (world.isRemote) PacketHelper.send(new IsFlyingPacket(flying));
				creative_count = 0;
			}
			creative_timeout = System.currentTimeMillis();
			creative_count = 0;
		}
		else {
			creative_count++;
		}
	}
}

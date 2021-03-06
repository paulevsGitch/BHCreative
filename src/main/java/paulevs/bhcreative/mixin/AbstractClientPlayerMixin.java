package paulevs.bhcreative.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.interfaces.CreativePlayer;
import paulevs.bhcreative.interfaces.FlyOption;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends PlayerBase {
	public AbstractClientPlayerMixin(Level arg) {
		super(arg);
	}
	
	@Shadow
	protected Minecraft minecraft;
	
	@Inject(method = "getCanSuffocate", at = @At("HEAD"), cancellable = true)
	private void creative_getCanSuffocate(int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		if (((CreativePlayer) this).creative_isCreative()) {
			info.setReturnValue(false);
			info.cancel();
		}
	}
	
	@Inject(method = "method_136", at = @At("HEAD"), cancellable = true)
	public void creative_onKeyPress(int i, boolean flag, CallbackInfo info) {
		CreativePlayer player = (CreativePlayer) this;
		if (player.creative_isCreative()) {
			if (flag && i == ((FlyOption) minecraft.options).creative_getFlyKey().key) {
				boolean fly = !player.creative_isFlying();
				player.creative_setFlying(fly);
				if (fly) {
					AbstractClientPlayer client = (AbstractClientPlayer) (Object) this;
					if (client.velocityY <= 0) client.velocityY = 0.01F;
					//client.setPositionAndAngles(client.x, client.y - client.standingEyeHeight + 0.01, client.z, client.yaw, client.pitch);
					//client.velocityY = Math.max(client.velocityY * 0.7, 0.01);
				}
				info.cancel();
			}
		}
	}
}

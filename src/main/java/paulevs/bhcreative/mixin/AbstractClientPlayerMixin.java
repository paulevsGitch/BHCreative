package paulevs.bhcreative.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.interfaces.CreativePlayer;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends PlayerBase {
	@Unique private long creative_timeout;
	@Unique private long creative_count;
	
	@Shadow protected Minecraft minecraft;
	
	public AbstractClientPlayerMixin(Level arg) {
		super(arg);
	}
	
	@Inject(method = "getCanSuffocate", at = @At("HEAD"), cancellable = true)
	private void creative_getCanSuffocate(int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		if (((CreativePlayer) this).creative_isCreative()) {
			info.setReturnValue(false);
			info.cancel();
		}
	}
	
	@Inject(method = "method_136", at = @At("HEAD"), cancellable = true)
	public void creative_onKeyPress(int i, boolean flag, CallbackInfo info) {
		if (i != minecraft.options.jumpKey.key) return;
		CreativePlayer player = (CreativePlayer) this;
		
		if (flag) {
			if (creative_count > 0) {
				System.out.println("Double Jump!");
				player.creative_setFlying(!player.creative_isFlying());
				creative_count = 0;
			}
			else creative_timeout = System.currentTimeMillis();
		}
		else {
			long time = System.currentTimeMillis();
			creative_timeout = time - creative_timeout;
			if (creative_timeout < 200) creative_count++;
			creative_timeout = time;
		}
		
		if (flag && this.jumping && player.creative_isCreative() && i == minecraft.options.jumpKey.key) {
			//if (creative_timeout)
			//player.creative_setFlying(true);
			//info.cancel();
			/*if (flag && i == ((FlyOption) minecraft.options).creative_getFlyKey().key) {
				boolean fly = !player.creative_isFlying();
				player.creative_setFlying(fly);
				if (fly) {
					AbstractClientPlayer client = (AbstractClientPlayer) (Object) this;
					if (client.velocityY <= 0) client.velocityY = 0.01F;
					//client.setPositionAndAngles(client.x, client.y - client.standingEyeHeight + 0.01, client.z, client.yaw, client.pitch);
					//client.velocityY = Math.max(client.velocityY * 0.7, 0.01);
				}
				info.cancel();
			}*/
		}
	}
}

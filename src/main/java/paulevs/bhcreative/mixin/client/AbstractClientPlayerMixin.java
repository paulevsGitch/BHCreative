package paulevs.bhcreative.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.living.player.AbstractClientPlayer;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.util.IsFlyingPacket;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends PlayerEntity {
	@Unique
	private long creative_timeout;
	@Unique
	private boolean creative_debounce;

	@Shadow
	protected Minecraft minecraft;

	public AbstractClientPlayerMixin(Level arg) {
		super(arg);
	}

	@Inject(method = "getCanSuffocate", at = @At("HEAD"), cancellable = true)
	private void creative_getCanSuffocate(int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		if (this.creative_isCreative()) {
			info.setReturnValue(false);
			info.cancel();
		}
	}

	@Inject(method = "onKeyPressed(IZ)V", at = @At("HEAD"))
	public void creative_onKeyPress(int key, boolean pressed, CallbackInfo info) {
		if (key != minecraft.options.jumpKey.key) return;
		if (!creative_isCreative()) return;

		if (pressed) {
			long time = System.currentTimeMillis();
			creative_timeout = time - creative_timeout;
			if (creative_debounce && creative_timeout < 500) {
				boolean flying = !creative_isFlying();
				creative_setFlying(flying);
				if (level.isRemote) PacketHelper.send(new IsFlyingPacket(flying));
			} else {
				creative_timeout = System.currentTimeMillis();
				creative_debounce = false;
			}
		} else {
			creative_debounce = true;
		}

		// Force timeout if the player lands
		if (onGround && creative_timeout < 500) {
			creative_timeout += 500;
		}
	}
}

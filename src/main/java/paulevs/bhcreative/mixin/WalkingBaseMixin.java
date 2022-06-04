package paulevs.bhcreative.mixin;

import net.minecraft.entity.EntityBase;
import net.minecraft.entity.WalkingBase;
import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.Creative;

@Mixin(WalkingBase.class)
public class WalkingBaseMixin {
	@Shadow protected EntityBase entity;
	
	@Inject(method = "tickHandSwing()V", at = @At("HEAD"), cancellable = true)
	protected void tickHandSwing(CallbackInfo info) {
		if (entity instanceof PlayerBase && Creative.isInCreative((PlayerBase) entity)) {
			info.cancel();
		}
	}
}

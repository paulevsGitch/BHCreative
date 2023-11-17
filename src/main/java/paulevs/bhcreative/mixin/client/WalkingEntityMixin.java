package paulevs.bhcreative.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.WalkingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.BHCreative;

@Mixin(WalkingEntity.class)
public class WalkingEntityMixin {
	@Shadow protected Entity entity;
	
	@Inject(method = "tickHandSwing()V", at = @At("HEAD"), cancellable = true)
	protected void tickHandSwing(CallbackInfo info) {
		if (entity instanceof PlayerEntity && BHCreative.isInCreative((PlayerEntity) entity)) {
			info.cancel();
		}
	}
}

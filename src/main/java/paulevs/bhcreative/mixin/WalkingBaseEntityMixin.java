package paulevs.bhcreative.mixin;

import net.minecraft.entity.BaseEntity;
import net.minecraft.entity.WalkingBaseEntity;
import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.Creative;

@Mixin(WalkingBaseEntity.class)
public class WalkingBaseEntityMixin {
	@Shadow protected BaseEntity entity;
	
	@Inject(method = "tickHandSwing()V", at = @At("HEAD"), cancellable = true)
	protected void tickHandSwing(CallbackInfo info) {
		if (entity instanceof PlayerBase && Creative.isInCreative((PlayerBase) entity)) {
			info.cancel();
		}
	}
}

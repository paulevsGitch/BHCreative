package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class WalkingEntityMixin {
	@Shadow protected Entity target;
	
	@Inject(method = "tickLiving()V", at = @At("RETURN"))
	protected void tickHandSwing(CallbackInfo info) {
		if (target instanceof PlayerEntity player && player.creative_isCreative()) {
			target = null;
		}
	}
}

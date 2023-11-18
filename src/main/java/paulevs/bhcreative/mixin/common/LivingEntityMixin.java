package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "noObstaclesTo", at = @At("HEAD"), cancellable = true)
	private void creative_noObstaclesTo(Entity entity, CallbackInfoReturnable<Boolean> info) {
		if (entity instanceof PlayerEntity player && player.creative_isCreative()) {
			info.setReturnValue(false);
		}
	}
}

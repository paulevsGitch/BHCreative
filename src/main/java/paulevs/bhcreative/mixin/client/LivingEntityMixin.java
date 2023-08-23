package paulevs.bhcreative.mixin.client;

import net.minecraft.entity.BaseEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.BHCreative;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "method_928", at = @At("HEAD"), cancellable = true)
	private void creative_method_928(BaseEntity entity, CallbackInfoReturnable<Boolean> info) {
		if (entity instanceof PlayerBase && BHCreative.isInCreative((PlayerBase) entity)) {
			info.setReturnValue(false);
		}
	}
}

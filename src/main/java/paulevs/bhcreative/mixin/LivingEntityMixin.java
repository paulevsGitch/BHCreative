package paulevs.bhcreative.mixin;

import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.Creative;

@Mixin(Living.class)
public class LivingEntityMixin {
	@Inject(method = "method_928", at = @At("HEAD"), cancellable = true)
	private void creative_method_928(EntityBase entity, CallbackInfoReturnable<Boolean> info) {
		if (entity instanceof PlayerBase && Creative.isInCreative((PlayerBase) entity)) {
			info.setReturnValue(false);
		}
	}
}

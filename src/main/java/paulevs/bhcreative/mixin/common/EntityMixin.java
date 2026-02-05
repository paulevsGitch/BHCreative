package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
	@Shadow public float horizontalSpeed;
	@Shadow private int nextStepSoundDistance;
	
	@Inject(method = "move", at = @At(
		value = "FIELD",
		target = "Lnet/minecraft/entity/Entity;horizontalSpeed:F",
		shift = Shift.AFTER,
		ordinal = 1
	))
	private void creative_fixFallSound(double e, double f, double par3, CallbackInfo info) {
		Entity entity = Entity.class.cast(this);
		if (!(entity instanceof PlayerEntity player)) return;
		if (!player.creative_isCreative()) return;
		if (!player.creative_isFlying()) return;
		this.nextStepSoundDistance = (int) (this.horizontalSpeed + 2);
	}
}

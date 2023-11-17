package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.BHCreative;

@Mixin(Entity.class)
public class EntityMixin {
	@Shadow public float walkedDistance;
	@Shadow private int stepsCount;
	
	@Inject(method = "move", at = @At(
		value = "FIELD",
		target = "Lnet/minecraft/entity/Entity;walkedDistance:F",
		shift = Shift.AFTER,
		ordinal = 1
	))
	private void on_assign(double e, double f, double par3, CallbackInfo info) {
		Entity entity = Entity.class.cast(this);
		if (!(entity instanceof PlayerEntity player)) return;
		if (!BHCreative.isInCreative(player)) return;
		if (!BHCreative.isFlying(player)) return;
		this.stepsCount = (int) (this.walkedDistance + 2);
	}
}

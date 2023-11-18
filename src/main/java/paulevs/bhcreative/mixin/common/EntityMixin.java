package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
	private void creative_fixFallSound(double e, double f, double par3, CallbackInfo info) {
		Entity entity = Entity.class.cast(this);
		if (!(entity instanceof PlayerEntity player)) return;
		if (!player.creative_isCreative()) return;
		if (!player.creative_isFlying()) return;
		this.stepsCount = (int) (this.walkedDistance + 2);
	}
}

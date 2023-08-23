package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.BaseEntity;
import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.BHCreative;

@Mixin(BaseEntity.class)
public class BaseEntityMixin {
	@Shadow public float field_1635;
	@Shadow private int field_1611;
	
	@Inject(method = "move", at = @At(
		value = "FIELD",
		target = "Lnet/minecraft/entity/BaseEntity;field_1635:F",
		shift = Shift.AFTER,
		ordinal = 1
	))
	private void on_assign(double e, double f, double par3, CallbackInfo info) {
		BaseEntity entity = BaseEntity.class.cast(this);
		if (!(entity instanceof PlayerBase player)) return;
		if (!BHCreative.isInCreative(player)) return;
		if (!BHCreative.isFlying(player)) return;
		this.field_1611 = (int) (this.field_1635 + 2);
	}
}

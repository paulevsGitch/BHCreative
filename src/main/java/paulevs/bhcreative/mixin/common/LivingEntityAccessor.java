package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
	@Accessor("sidewaysSpeed")
	float creative_getFrontMovement();
	
	@Accessor("forwardSpeed")
	float creative_getRightMovement();
}

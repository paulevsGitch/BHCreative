package paulevs.bhcreative.mixin.client;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
	@Accessor("perpendicularMovement")
	float creative_getFrontMovement();
	
	@Accessor("parallelMovement")
	float creative_getRightMovement();
}

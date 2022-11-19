package paulevs.bhcreative.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
	@Accessor("field_1060")
	public float getPerpendicularMovement();
	
	@Accessor("field_1029")
	public float getParallelMovement();
}

package paulevs.bhcreative.mixin;

import net.minecraft.entity.Living;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Living.class)
public interface LivingAccessor {
	@Accessor("field_1060")
	public float getPerpendicularMovement();
	
	@Accessor("field_1029")
	public float getParallelMovement();
}

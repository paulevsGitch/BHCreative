package paulevs.bhcreative.mixin;

import net.minecraft.entity.Living;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Living.class)
public interface LivingEntityAccessor {
	@Accessor("field_1060")
	float creative_getFrontMovement();
	
	@Accessor("field_1029")
	float creative_getRightMovement();
}

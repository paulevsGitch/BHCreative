package paulevs.bhcreative.mixin.client;

import net.minecraft.level.LevelProperties;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.interfaces.CreativeLevel;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements CreativeLevel {
	@Unique private boolean creative_isCreative;
	
	@Inject(method = "<init>(Lnet/minecraft/util/io/CompoundTag;)V", at = @At("TAIL"))
	private void creative_onInit(CompoundTag tag, CallbackInfo ci) {
		creative_isCreative = tag.getBoolean("Creative");
		if (!creative_isCreative && tag.containsKey("Player")) {
			creative_isCreative = tag.getCompoundTag("Player").getBoolean("Creative");
		}
	}
	
	@Override
	public boolean creative_isCreative() {
		return creative_isCreative;
	}
	
	@Override
	public void creative_setCreative(boolean creative) {
		creative_isCreative = creative;
	}
}

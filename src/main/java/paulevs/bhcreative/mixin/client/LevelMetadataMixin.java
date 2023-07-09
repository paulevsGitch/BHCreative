package paulevs.bhcreative.mixin.client;

import net.minecraft.level.storage.LevelMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import paulevs.bhcreative.interfaces.CreativeLevel;

@Mixin(LevelMetadata.class)
public class LevelMetadataMixin implements CreativeLevel {
	@Unique private boolean creative_isCreative;
	
	@Override
	public boolean creative_isCreative() {
		return creative_isCreative;
	}
	
	@Override
	public void creative_setCreative(boolean creative) {
		creative_isCreative = creative;
	}
}

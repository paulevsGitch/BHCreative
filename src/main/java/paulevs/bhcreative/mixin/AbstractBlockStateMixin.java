package paulevs.bhcreative.mixin;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.BlockView;
import net.minecraft.util.maths.TilePos;
import net.modificationstation.stationapi.api.block.AbstractBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.Creative;

@Mixin(value = AbstractBlockState.class, remap = false)
public class AbstractBlockStateMixin {
	@Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
	private void creative_calcBlockBreakingDelta(PlayerBase player, BlockView world, TilePos pos, CallbackInfoReturnable<Float> info) {
		if (Creative.isInCreative(player)) {
			info.setReturnValue(1.0F);
		}
	}
}

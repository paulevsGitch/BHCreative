package paulevs.bhcreative.mixin.common;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.player.PlayerBase;
import net.modificationstation.stationapi.impl.entity.player.PlayerAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.BHCreative;

@Mixin(value = PlayerAPI.class, remap = false)
public class PlayerAPIMixin {
	@Inject(method = "getCurrentPlayerStrVsBlock", at = @At("HEAD"), cancellable = true)
	private static void creative_getCurrentPlayerStrVsBlock(PlayerBase player, BlockBase block, float f, CallbackInfoReturnable<Float> info) {
		if (BHCreative.isInCreative(player)) {
			info.setReturnValue(0F);
		}
	}
	
	@Inject(method = "canHarvestBlock", at = @At("HEAD"), cancellable = true)
	private static void creative_canRemoveBlock(PlayerBase player, BlockBase block, CallbackInfoReturnable<Boolean> info) {
		if (BHCreative.isInCreative(player)) {
			info.setReturnValue(true);
		}
	}
}

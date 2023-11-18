package paulevs.bhcreative.mixin.common;

import net.minecraft.block.Block;
import net.minecraft.entity.living.player.PlayerEntity;
import net.modificationstation.stationapi.impl.entity.player.PlayerAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerAPI.class, remap = false)
public class PlayerAPIMixin {
	@Inject(method = "getCurrentPlayerStrVsBlock", at = @At("HEAD"), cancellable = true)
	private static void creative_getCurrentPlayerStrVsBlock(PlayerEntity player, Block block, float f, CallbackInfoReturnable<Float> info) {
		if (player.creative_isCreative()) {
			info.setReturnValue(0F);
		}
	}
	
	@Inject(method = "canHarvestBlock", at = @At("HEAD"), cancellable = true)
	private static void creative_canRemoveBlock(PlayerEntity player, Block block, CallbackInfoReturnable<Boolean> info) {
		if (player.creative_isCreative()) {
			info.setReturnValue(true);
		}
	}
}

package paulevs.bhcreative.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.Creative;

@Mixin(value = BlockBase.class, priority = 1)
public class BlockBaseMixin {
	@Inject(method = "getHardness(Lnet/minecraft/entity/player/PlayerBase;)F", at = @At("HEAD"), cancellable = true)
	private void creative_getHardness(PlayerBase player, CallbackInfoReturnable<Float> info) {
		System.out.println(player);
		if (Creative.isInCreative(player)) {
			info.setReturnValue(0F);
		}
	}
}

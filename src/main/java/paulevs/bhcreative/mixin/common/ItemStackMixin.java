package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Unique private static int creative_count;
	@Shadow public int count;
	
	@Inject(method = "useOnBlock", at = @At("HEAD"))
	private void creative_beforeUseOnTile(PlayerEntity player, Level arg2, int i, int j, int k, int l, CallbackInfoReturnable<Boolean> info) {
		if (player.creative_isCreative()) creative_count = count;
	}
	
	@Inject(method = "useOnBlock", at = @At("RETURN"))
	private void creative_afterUseOnTile(PlayerEntity player, Level arg2, int i, int j, int k, int l, CallbackInfoReturnable<Boolean> info) {
		if (player.creative_isCreative()) count = creative_count;
	}
}

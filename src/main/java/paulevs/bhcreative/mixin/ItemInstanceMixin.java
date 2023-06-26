package paulevs.bhcreative.mixin;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.Creative;

@Mixin(ItemInstance.class)
public class ItemInstanceMixin {
	@Unique private static int creative_count;
	@Shadow public int count;
	
	@Inject(method = "useOnTile", at = @At("HEAD"))
	private void creative_beforeUseOnTile(PlayerBase player, Level arg2, int i, int j, int k, int l, CallbackInfoReturnable<Boolean> info) {
		if (Creative.isInCreative(player)) creative_count = count;
	}
	
	@Inject(method = "useOnTile", at = @At("RETURN"))
	private void creative_afterUseOnTile(PlayerBase player, Level arg2, int i, int j, int k, int l, CallbackInfoReturnable<Boolean> info) {
		if (Creative.isInCreative(player)) count = creative_count;
	}
}

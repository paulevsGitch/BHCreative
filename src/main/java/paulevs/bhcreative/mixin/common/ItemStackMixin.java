package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Unique private static int creative_count;
	@Unique private static int creative_damage;
	@Shadow public int count;
	@Shadow private int damage;
	
	@Inject(method = "useOnBlock", at = @At("HEAD"))
	private void creative_beforeUseOnBlock(PlayerEntity player, World arg2, int i, int j, int k, int l, CallbackInfoReturnable<Boolean> info) {
		if (player.creative_isCreative()) {
			creative_count = count;
			creative_damage = damage;
		}
	}
	
	@Inject(method = "useOnBlock", at = @At("RETURN"))
	private void creative_afterUseOnBlock(PlayerEntity player, World arg2, int i, int j, int k, int l, CallbackInfoReturnable<Boolean> info) {
		if (player.creative_isCreative()) {
			count = creative_count;
			damage = creative_damage;
		}
	}
	
	@Inject(method = "use", at = @At("HEAD"))
	private void creative_beforeUse(World level, PlayerEntity player, CallbackInfoReturnable<ItemStack> info) {
		if (player.creative_isCreative()) {
			creative_count = count;
			creative_damage = damage;
		}
	}
	
	@Inject(method = "use", at = @At("RETURN"))
	private void creative_afterUse(World level, PlayerEntity player, CallbackInfoReturnable<ItemStack> info) {
		if (player.creative_isCreative()) {
			count = creative_count;
			damage = creative_damage;
		}
	}
	
	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private void creative_applyDamage(int damage, Entity entity, CallbackInfo info) {
		if (!(entity instanceof PlayerEntity player)) return;
		if (!player.creative_isCreative()) return;
		info.cancel();
	}
}

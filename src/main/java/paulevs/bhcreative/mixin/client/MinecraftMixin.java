package paulevs.bhcreative.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.living.player.AbstractClientPlayer;
import net.minecraft.inventory.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.BHCreative;
import paulevs.bhcreative.api.BlockSelectAPI;
import paulevs.bhcreative.registry.TabRegistry;
import paulevs.bhcreative.registry.TabRegistryEvent;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow public AbstractClientPlayer player;
	@Shadow public HitResult hitResult;
	@Shadow public Level level;
	@Shadow private int attackCooldown;
	
	@Inject(method = "pickupHitBlock", at = @At("HEAD"), cancellable = true)
	private void creative_setMouseButtonItem(CallbackInfo info) {
		if (!this.player.creative_isCreative() || this.hitResult == null) return;
		
		BlockState state = level.getBlockState(this.hitResult.x, this.hitResult.y, this.hitResult.z);
		int meta = level.getBlockMeta(this.hitResult.x, this.hitResult.y, this.hitResult.z);
		ItemStack stack = BlockSelectAPI.convert(state, meta);
		
		if (stack == null) return;
		
		PlayerInventory inventory = this.player.inventory;
		
		info.cancel();
		
		int selectedSlot = inventory.selectedHotbarSlot;
		boolean selectEmpty = true;
		
		for (byte slot = 0; slot < 9; slot++) {
			ItemStack itemInv = inventory.getItem(slot);
			if (itemInv == null) {
				if (selectEmpty) {
					selectedSlot = slot;
					selectEmpty = false;
				}
			}
			else if (itemInv.itemId == stack.itemId && itemInv.getDamage() == stack.getDamage()) {
				inventory.selectedHotbarSlot = slot;
				return;
			}
		}
		
		inventory.selectedHotbarSlot = selectedSlot;
		inventory.setItem(selectedSlot, stack);
	}
	
	@Inject(method = "processAttack", at = @At("RETURN"))
	private void creative_blockBreakDelay(int i, CallbackInfo info) {
		if (i != 0 || !this.player.creative_isCreative()) return;
		if (this.hitResult == null || this.hitResult.type != HitType.BLOCK) return;
		this.attackCooldown = 100;
	}
	
	@Inject(method = "run", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/Minecraft;init()V",
		shift = Shift.AFTER
	))
	private void creative_onGameInit(CallbackInfo info) {
		BHCreative.LOGGER.info("Register creative tabs");
		StationAPI.EVENT_BUS.post(new TabRegistryEvent());
		TabRegistry.sort();
	}
}

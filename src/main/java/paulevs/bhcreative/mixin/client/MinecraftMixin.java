package paulevs.bhcreative.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
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
import paulevs.bhcreative.registry.TabRegistryEvent;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow public AbstractClientPlayer player;
	
	@Shadow public HitResult hitResult;
	
	@Shadow public Level level;
	
	@Shadow private int attackCooldown;
	
	@Inject(method = "method_2103", at = @At("HEAD"), cancellable = true)
	private void creative_setMouseButtonItem(CallbackInfo info) {
		if (!BHCreative.isInCreative(this.player) || this.hitResult == null) return;
		
		BlockState state = level.getBlockState(this.hitResult.x, this.hitResult.y, this.hitResult.z);
		ItemBase item = state.getBlock().asItem();
		
		if (item == null) return;
		
		PlayerInventory inventory = this.player.inventory;
		int meta = level.getTileMeta(this.hitResult.x, this.hitResult.y, this.hitResult.z);
		
		info.cancel();
		
		int selectedSlot = inventory.selectedHotbarSlot;
		boolean selectEmpty = true;
		
		for (byte slot = 0; slot < 9; slot++) {
			ItemInstance itemInv = inventory.getInventoryItem(slot);
			if (itemInv == null) {
				if (selectEmpty) {
					selectedSlot = slot;
					selectEmpty = false;
				}
			}
			else if (itemInv.itemId == item.id && itemInv.getDamage() == meta) {
				inventory.selectedHotbarSlot = slot;
				return;
			}
		}
		
		inventory.selectedHotbarSlot = selectedSlot;
		inventory.setInventoryItem(selectedSlot, new ItemInstance(item, 1, meta));
	}
	
	@Inject(method = "method_2107", at = @At("RETURN"))
	private void creative_blockBreakDelay(int i, CallbackInfo info) {
		if (i != 0 || !BHCreative.isInCreative(this.player)) return;
		if (this.hitResult == null || this.hitResult.type != HitType.field_789) return;
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
	}
}

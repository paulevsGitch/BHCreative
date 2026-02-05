package paulevs.bhcreative.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
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
import paulevs.bhcreative.util.SlotUpdatePacket;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow public ClientPlayerEntity player;
	@Shadow public HitResult crosshairTarget;
	@Shadow public World world;
	@Shadow private int attackCooldown;
	
	@Inject(method = "handlePickBlock", at = @At("HEAD"), cancellable = true)
	private void creative_setMouseButtonItem(CallbackInfo info) {
		if (!this.player.creative_isCreative() || this.crosshairTarget == null) return;
		
		BlockState state = world.getBlockState(this.crosshairTarget.blockX, this.crosshairTarget.blockY, this.crosshairTarget.blockZ);
		int meta = world.getBlockMeta(this.crosshairTarget.blockX, this.crosshairTarget.blockY, this.crosshairTarget.blockZ);
		ItemStack stack = BlockSelectAPI.convert(state, meta);
		
		if (stack == null) return;
		
		PlayerInventory inventory = this.player.inventory;
		
		info.cancel();
		
		int selectedSlot = inventory.selectedSlot;
		boolean selectEmpty = true;
		
		for (byte slot = 0; slot < 9; slot++) {
			ItemStack itemInv = inventory.getStack(slot);
			if (itemInv == null) {
				if (selectEmpty) {
					selectedSlot = slot;
					selectEmpty = false;
				}
			}
			else if (itemInv.itemId == stack.itemId && itemInv.getDamage() == stack.getDamage()) {
				inventory.selectedSlot = slot;
				return;
			}
		}
		
		inventory.selectedSlot = selectedSlot;
		inventory.setStack(selectedSlot, stack);
		PacketHelper.send(new SlotUpdatePacket(selectedSlot, stack));
	}
	
	@Inject(method = "handleMouseClick", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/InteractionManager;attackBlock(IIII)V",
		shift = Shift.AFTER
	))
	private void creative_blockBreakDelay(int type, CallbackInfo info) {
		if (!this.player.creative_isCreative()) return;
		this.attackCooldown = 5;
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

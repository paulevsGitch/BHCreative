package paulevs.bhcreative.mixin.server;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.storage.AlphaWorldStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.BHCreative;

@Mixin(AlphaWorldStorage.class)
public class DimensionFileMixin {
	@Unique private boolean creative_changeMode;
	
	@Inject(method = "loadPlayerData(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/world/storage/AlphaWorldStorage;loadPlayerData(Ljava/lang/String;)Lnet/minecraft/nbt/NbtCompound;",
		shift = Shift.AFTER
	))
	private void creative_loadPlayer(PlayerEntity player, CallbackInfo info) {
		if (creative_changeMode) {
			player.creative_setCreative(BHCreative.serverIsCreative);
		}
	}
	
	@Inject(method = "loadPlayerData(Ljava/lang/String;)Lnet/minecraft/nbt/NbtCompound;", at = @At("RETURN"))
	private void creative_getPlayerData(String name, CallbackInfoReturnable<NbtCompound> info) {
		creative_changeMode = info.getReturnValue() == null;
	}
}

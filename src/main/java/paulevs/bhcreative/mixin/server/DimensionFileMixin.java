package paulevs.bhcreative.mixin.server;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.level.dimension.DimensionFile;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.BHCreative;

@Mixin(DimensionFile.class)
public class DimensionFileMixin {
	@Unique private boolean creative_changeMode;
	
	@Inject(method = "loadPlayer", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/level/dimension/DimensionFile;getPlayerData(Ljava/lang/String;)Lnet/minecraft/util/io/CompoundTag;",
		shift = Shift.AFTER
	))
	private void creative_loadPlayer(PlayerEntity player, CallbackInfo info) {
		if (creative_changeMode) {
			player.creative_setCreative(BHCreative.serverIsCreative);
		}
	}
	
	@Inject(method = "getPlayerData", at = @At("RETURN"))
	private void creative_getPlayerData(String name, CallbackInfoReturnable<CompoundTag> info) {
		creative_changeMode = info.getReturnValue() == null;
	}
}

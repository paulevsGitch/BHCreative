package paulevs.bhcreative.mixin.client;

import net.minecraft.level.LevelProperties;
import net.minecraft.level.storage.LevelMetadata;
import net.minecraft.util.io.CompoundTag;
import net.modificationstation.stationapi.impl.level.storage.StationFlatteningWorldStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import paulevs.bhcreative.interfaces.CreativeLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mixin(value = StationFlatteningWorldStorage.class, remap = false)
public class StationFlatteningWorldStorageMixin {
	@SuppressWarnings("rawtypes")
	@Inject(method = "getMetadata", at = @At(
		value = "INVOKE",
		target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z",
		shift = Shift.AFTER
	), locals = LocalCapture.CAPTURE_FAILSOFT)
	private void creative_setMetadata(CallbackInfoReturnable<List> info, ArrayList worlds, File[] var2, int var3, int var4, File worldPath, String worldFolder, LevelProperties data, CompoundTag worldTag, boolean requiresUpdating, String worldName) {
		LevelMetadata meta = (LevelMetadata) worlds.get(worlds.size() - 1);
		System.out.println("Get meta " + meta);
		boolean isCreative = CreativeLevel.cast(data).creative_isCreative();
		System.out.println("Is meta creative " + isCreative);
		CreativeLevel.cast(meta).creative_setCreative(isCreative);
	}
}

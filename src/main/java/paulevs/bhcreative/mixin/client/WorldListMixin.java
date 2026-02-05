package paulevs.bhcreative.mixin.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.world.storage.WorldSaveInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = "net.minecraft.client.gui.screen.world.SelectWorldScreen$WorldListWidget")
public abstract class WorldListMixin {
	@Inject(method = "renderEntry", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/gui/screen/world/SelectWorldScreen;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
		ordinal = 2,
		shift = Shift.AFTER
	), locals = LocalCapture.CAPTURE_FAILSOFT)
	protected void creative_renderEntry(
		int i, int x, int y, int l, Tessellator arg, CallbackInfo info,
		WorldSaveInfo meta, String levelName, String var8, long var9, String var11
	) {
		@SuppressWarnings("deprecation")
		Minecraft minecraft = (Minecraft) FabricLoader.getInstance().getGameInstance();
		int offset = minecraft.textRenderer.getWidth(levelName) + 6;
		minecraft.textRenderer.drawWithShadow("[", x + offset, y + 1, 0xFFFFFF);
		offset += minecraft.textRenderer.getWidth("[");
		boolean isCreative = meta.creative_isCreative();
		String gameMode = isCreative ? "Creative" : "Survival";
		int color = isCreative ? 0x00FFFF : 0x00FF00;
		minecraft.textRenderer.drawWithShadow(gameMode, x + offset, y + 1, color);
		offset += minecraft.textRenderer.getWidth(gameMode);
		minecraft.textRenderer.drawWithShadow("]", x + offset, y + 1, 0xFFFFFF);
	}
}

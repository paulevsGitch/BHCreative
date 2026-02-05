package paulevs.bhcreative.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Shadow private Minecraft client;
	
	@Inject(method = "applyViewBobbing", at = @At("HEAD"), cancellable = true)
	private void creative_cancelBobbing(float delta, CallbackInfo info) {
		if (client.camera instanceof PlayerEntity player) {
			if (player.creative_isCreative() && player.creative_isFlying()) {
				info.cancel();
			}
		}
	}
}

package paulevs.bhcreative.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcreative.Creative;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Shadow private Minecraft minecraft;
	
	@Inject(method = "method_1850(F)V", at = @At("HEAD"), cancellable = true)
	private void creative_cancelBobbing(float f, CallbackInfo info) {
		if (minecraft.viewEntity instanceof PlayerBase) {
			PlayerBase player = (PlayerBase) minecraft.viewEntity;
			if (Creative.isInCreative(player) && Creative.isFlying(player)) {
				info.cancel();
			}
		}
	}
}

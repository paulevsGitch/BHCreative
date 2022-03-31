package paulevs.bhcreative.mixin;

import net.minecraft.client.BaseClientInteractionManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.interfaces.CreativePlayer;

@Mixin(BaseClientInteractionManager.class)
public class ClientInteractionManagerMixin {
	@Shadow private Minecraft minecraft;
	
	@Inject(method = "method_1722", at = @At("HEAD"), cancellable = true)
	private void creative_renderHud(CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue(!((CreativePlayer) minecraft.player).isCreative());
	}
}

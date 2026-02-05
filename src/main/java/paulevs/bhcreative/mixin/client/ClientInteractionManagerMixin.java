package paulevs.bhcreative.mixin.client;

import net.minecraft.client.InteractionManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InteractionManager.class)
public class ClientInteractionManagerMixin {
	@Final @Shadow protected Minecraft minecraft;
	
	@Inject(method = "canBeRendered", at = @At("HEAD"), cancellable = true)
	private void creative_renderHud(CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue(!minecraft.player.creative_isCreative());
	}
}

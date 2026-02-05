package paulevs.bhcreative.mixin.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.BHCreative;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Shadow public ServerProperties properties;
	
	@Inject(method = "init", at = @At(
		value = "FIELD",
		target = "Lnet/minecraft/server/MinecraftServer;flightEnabled:Z"
	))
	private void creative_onServerStart(CallbackInfoReturnable<Boolean> info) {
		int gamemode = this.properties.getProperty("default-gamemode", 0);
		BHCreative.serverIsCreative = gamemode > 0;
	}
}

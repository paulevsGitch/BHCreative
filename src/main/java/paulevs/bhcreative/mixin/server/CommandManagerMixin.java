package paulevs.bhcreative.mixin.server;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.Command;
import net.minecraft.server.command.CommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
	@Shadow private MinecraftServer server;
	
	@Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
	public void creative_processCommand(Command command, CallbackInfo info) {
		if (command.commandString.startsWith("gm ")) {
			info.cancel();
			
			String name = command.source.getName();
			PlayerEntity player = this.server.serverPlayerConnectionManager.getServerPlayer(name);
			
			if (player == null) {
				command.source.sendFeedback("Command should be executed by player!");
				return;
			}
			
			int mode = 0;
			String[] args = command.commandString.split(" ");
			try {
				mode = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e) {
				command.source.sendFeedback("Usage: /gm <0|1>");
				return;
			}
			
			player.creative_setCreative(mode > 0);
		}
	}
}

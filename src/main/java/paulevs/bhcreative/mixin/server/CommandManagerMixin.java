package paulevs.bhcreative.mixin.server;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.packet.play.ChatMessagePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerPlayerConnectionManager;
import net.minecraft.server.command.Command;
import net.minecraft.server.command.CommandManager;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
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
		if (command.commandString.equals("gm")) {
			info.cancel();
			command.source.sendFeedback("Usage: /gm <0|1>: Set your gamemode to survival|creative");
		}
		else if (command.commandString.startsWith("gm ")) {
			info.cancel();
			
			String name = command.source.getName();
			PlayerEntity player = this.server.serverPlayerConnectionManager.getServerPlayer(name);
			
			if (player == null) {
				command.source.sendFeedback("Command should be executed by player!");
				return;
			}
			
			int mode;
			String[] args = command.commandString.split(" ");
			
			ServerPlayerConnectionManager manager = this.server.serverPlayerConnectionManager;
			
			if (args.length != 2) {
				PacketHelper.send(new ChatMessagePacket("Test"));
				manager.sendMessage(name, "Usage: /gm <0|1>: Set your gamemode to survival|creative");
				return;
			}
			
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

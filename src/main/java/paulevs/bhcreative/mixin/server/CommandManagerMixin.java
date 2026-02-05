package paulevs.bhcreative.mixin.server;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.play.ChatMessagePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.Command;
import net.minecraft.server.command.ServerCommandHandler;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommandHandler.class)
public class CommandManagerMixin {
	@Shadow private MinecraftServer server;
	
	@Inject(method = "executeCommand", at = @At("HEAD"), cancellable = true)
	public void creative_processCommand(Command command, CallbackInfo info) {
		if (command.commandAndArgs.equals("gm")) {
			info.cancel();
			command.output.sendMessage("Usage: /gm <0|1>: Set your gamemode to survival|creative");
		}
		else if (command.commandAndArgs.startsWith("gm ")) {
			info.cancel();
			
			String name = command.output.getName();
			PlayerEntity player = this.server.playerManager.getPlayer(name);
			
			if (player == null) {
				command.output.sendMessage("Command should be executed by player!");
				return;
			}
			
			int mode;
			String[] args = command.commandAndArgs.split(" ");
			
			PlayerManager manager = this.server.playerManager;
			
			if (args.length != 2) {
				PacketHelper.send(new ChatMessagePacket("Test"));
				manager.messagePlayer(name, "Usage: /gm <0|1>: Set your gamemode to survival|creative");
				return;
			}
			
			try {
				mode = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e) {
				command.output.sendMessage("Usage: /gm <0|1>");
				return;
			}
			
			player.creative_setCreative(mode > 0);
		}
	}
}

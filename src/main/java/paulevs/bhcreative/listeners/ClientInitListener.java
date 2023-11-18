package paulevs.bhcreative.listeners;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.GrassColor;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;

public class ClientInitListener {
	@EventListener(priority = ListenerPriority.HIGHEST)
	public void preInit(InitEvent event) {
		FabricLoader.getInstance().getEntrypointContainers("bhcreative:event_bus", Object.class).forEach(EntrypointManager::setup);
	}
	
	@EventListener
	public void registerColorProviders(ItemColorsRegisterEvent event) {
		event.itemColors.register(
			(item, damage) -> GrassColor.getGrassColor(0.5F, 0.5F),
			CommonInitListener.tallGrass,
			CommonInitListener.fern, Block.GRASS
		);
	}
}

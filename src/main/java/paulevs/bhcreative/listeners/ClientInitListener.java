package paulevs.bhcreative.listeners;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.client.render.block.GrassColour;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.event.mod.PostInitEvent;
import net.modificationstation.stationapi.api.event.mod.PreInitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import paulevs.bhcreative.BHCreative;
import paulevs.bhcreative.registry.TabRegistryEvent;

public class ClientInitListener {
	@EventListener
	public void postInit(PostInitEvent event) {
		BHCreative.LOGGER.info("Register creative tabs");
		StationAPI.EVENT_BUS.post(new TabRegistryEvent());
	}
	
	@EventListener
	public void preInit(PreInitEvent event) {
		FabricLoader.getInstance().getEntrypointContainers("bhcreative:event_bus", Object.class).forEach(EntrypointManager::setup);
	}
	
	@EventListener
	public void registerColorProviders(ItemColorsRegisterEvent event) {
		event.itemColors.register(
			(item, damage) -> GrassColour.get(0.5F, 0.5F),
			CommonInitListener.tallGrass,
			CommonInitListener.fern, BlockBase.GRASS
		);
	}
}

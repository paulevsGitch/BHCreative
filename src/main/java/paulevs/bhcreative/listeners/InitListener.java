package paulevs.bhcreative.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.mod.PostInitEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import paulevs.bhcreative.Creative;
import paulevs.bhcreative.registry.TabRegistryEvent;

public class InitListener {
	@EventListener
	public void postInit(PostInitEvent event) {
		/*CommonRegistries.EVENT_REGISTRY.put(
			TabRegistryEvent.class, () -> new TabRegistryEvent(TabRegistry.INSTANCE::register)
		);*/
		Creative.LOGGER.info("Register creative tabs");
		StationAPI.EVENT_BUS.post(new TabRegistryEvent());
	}
	
	/*@EventListener
	public static void afterInit2(AfterBlockAndItemsEvent event) {
		Creative.LOGGER.info("Registering creative tabs");
		BHAPI.processEntryPoints("bhcreative:events", CommonRegistries.EVENT_REGISTRY);
	}*/
}

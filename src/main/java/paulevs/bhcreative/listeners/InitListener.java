package paulevs.bhcreative.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.registry.AfterBlockAndItemRegisterEvent;
import paulevs.bhcreative.registry.TabRegistry;
import paulevs.bhcreative.registry.TabRegistryEvent;

public class InitListener {
	@EventListener
	public void afterInit(AfterBlockAndItemRegisterEvent event) {
		StationAPI.EVENT_BUS.post(new TabRegistryEvent(TabRegistry.INSTANCE::register));
	}
}

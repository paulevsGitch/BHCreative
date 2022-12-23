package paulevs.bhcreative.listeners;

import net.bhapi.BHAPI;
import net.bhapi.event.AfterBlockAndItemsEvent;
import net.bhapi.event.EventListener;
import net.bhapi.event.EventRegistrationEvent;
import net.bhapi.registry.CommonRegistries;
import paulevs.bhcreative.Creative;
import paulevs.bhcreative.registry.TabRegistry;
import paulevs.bhcreative.registry.TabRegistryEvent;

public class InitListener {
	@EventListener
	public void afterInit(EventRegistrationEvent event) {
		// if (!BHAPI.isClient()) return;
		CommonRegistries.EVENT_REGISTRY.put(
			TabRegistryEvent.class, () -> new TabRegistryEvent(TabRegistry.INSTANCE::register)
		);
	}
	
	@EventListener
	public static void afterInit2(AfterBlockAndItemsEvent event) {
		Creative.LOGGER.info("Registering creative tabs");
		BHAPI.processEntryPoints("bhcreative:events", CommonRegistries.EVENT_REGISTRY);
	}
}

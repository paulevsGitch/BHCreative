package paulevs.bhcreative.listeners;

import net.bhapi.BHAPI;
import net.bhapi.client.ClientRegistries;
import net.bhapi.client.event.AfterTextureLoadedEvent;
import net.bhapi.event.EventListener;
import net.bhapi.event.EventRegistrationEvent;
import paulevs.bhcreative.Creative;
import paulevs.bhcreative.registry.TabRegistry;
import paulevs.bhcreative.registry.TabRegistryEvent;

public class InitListener {
	@EventListener
	public void afterInit(EventRegistrationEvent event) {
		if (!BHAPI.isClient()) return;
		ClientRegistries.EVENT_REGISTRY_POST.put(
			TabRegistryEvent.class, () -> new TabRegistryEvent(TabRegistry.INSTANCE::register)
		);
	}
	
	@EventListener
	public static void afterInit2(AfterTextureLoadedEvent event) {
		Creative.LOGGER.info("Registering creative tabs");
		BHAPI.processEntryPoints("bhcreative:events", ClientRegistries.EVENT_REGISTRY_POST);
	}
}

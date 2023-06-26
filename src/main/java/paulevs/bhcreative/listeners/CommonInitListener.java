package paulevs.bhcreative.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import paulevs.bhcreative.Creative;
import paulevs.bhcreative.util.GrassPlacerItem;

public class CommonInitListener {
	public static GrassPlacerItem tallGrass;
	public static GrassPlacerItem fern;
	
	@EventListener
	public void registerItems(ItemRegistryEvent event) {
		tallGrass = new GrassPlacerItem(Creative.id("tall_grass"), 1);
		fern = new GrassPlacerItem(Creative.id("fern"), 2);
	}
}

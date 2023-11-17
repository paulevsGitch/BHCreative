package paulevs.bhcreative.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import paulevs.bhcreative.BHCreative;
import paulevs.bhcreative.util.GrassPlacerItem;

public class CommonInitListener {
	public static GrassPlacerItem tallGrass;
	public static GrassPlacerItem fern;
	
	@EventListener
	public void registerItems(ItemRegistryEvent event) {
		tallGrass = new GrassPlacerItem(BHCreative.id("tall_grass"), 1);
		fern = new GrassPlacerItem(BHCreative.id("fern"), 2);
		Block.DEADBUSH.setTranslationKey(BHCreative.id("dead_bush").toString());
	}
}

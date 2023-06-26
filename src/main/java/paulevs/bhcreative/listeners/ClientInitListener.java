package paulevs.bhcreative.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.client.render.block.GrassColour;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.color.item.ItemColors;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.event.mod.PostInitEvent;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.impl.vanillafix.client.color.block.VanillaBlockColorProviders;
import paulevs.bhcreative.Creative;
import paulevs.bhcreative.registry.TabRegistryEvent;

import java.awt.Color;

public class ClientInitListener {
	@EventListener
	public void postInit(PostInitEvent event) {
		Creative.LOGGER.info("Register creative tabs");
		StationAPI.EVENT_BUS.post(new TabRegistryEvent());
	}
	
	@EventListener
	public void postInit(ItemColorsRegisterEvent event) {
		event.itemColors.register(
			(item, damage) -> GrassColour.get(0.5F, 0.5F),
			CommonInitListener.tallGrass,
			CommonInitListener.fern
		);
	}
}

package paulevs.bhcreative.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.entity.player.IsPlayerUsingEffectiveToolEvent;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.registry.PacketTypeRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import paulevs.bhcreative.BHCreative;
import paulevs.bhcreative.util.GrassPlacerItem;
import paulevs.bhcreative.util.IsFlyingPacket;
import paulevs.bhcreative.util.SlotUpdatePacket;

public class CommonInitListener {
	public static GrassPlacerItem tallGrass;
	public static GrassPlacerItem fern;
	
	@EventListener
	public void registerItems(ItemRegistryEvent event) {
		tallGrass = new GrassPlacerItem(BHCreative.id("tall_grass"), 1);
		fern = new GrassPlacerItem(BHCreative.id("fern"), 2);
		Block.DEAD_BUSH.setTranslationKey(BHCreative.id("dead_bush").toString());
	}

	@EventListener
	public static void registerPackets(PacketRegisterEvent event){
		Registry.register(PacketTypeRegistry.INSTANCE, BHCreative.NAMESPACE.id("is_flying"), IsFlyingPacket.TYPE);
		Registry.register(PacketTypeRegistry.INSTANCE, BHCreative.NAMESPACE.id("update_slot"), SlotUpdatePacket.TYPE);
	}

	@EventListener
	public void isUsingEffectiveTool(IsPlayerUsingEffectiveToolEvent event) {
		if (null != event.player) {
			if (event.player.creative_isCreative()) {
				event.resultProvider = () -> false;
			}
		}
	}
}

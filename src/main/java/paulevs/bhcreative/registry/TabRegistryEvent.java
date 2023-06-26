package paulevs.bhcreative.registry;

import net.mine_diver.unsafeevents.Event;
import paulevs.bhcreative.api.CreativeTab;

public class TabRegistryEvent extends Event {
	public static final int ID = NEXT_ID.incrementAndGet();
	
	public TabRegistryEvent() {}
	
	public void register(CreativeTab tab) {
		TabRegistry.register(tab.getID(), tab);
	}
	
	@Override
	protected int getEventID() {
		return ID;
	}
}

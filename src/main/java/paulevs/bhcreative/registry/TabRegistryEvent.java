package paulevs.bhcreative.registry;

import net.mine_diver.unsafeevents.Event;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.bhcreative.api.CreativeTab;

import java.util.function.BiConsumer;

public class TabRegistryEvent extends Event {
	public final BiConsumer<Identifier, CreativeTab> register;
	public static final int ID = NEXT_ID.incrementAndGet();
	
	public TabRegistryEvent(BiConsumer<Identifier, CreativeTab> register) {
		this.register = register;
	}
	
	public final void register(Identifier id, CreativeTab tab) {
		register.accept(id, tab);
	}
	
	@Override
	protected int getEventID() {
		return ID;
	}
}

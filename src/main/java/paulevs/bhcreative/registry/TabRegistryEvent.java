package paulevs.bhcreative.registry;

import net.bhapi.event.EventPriorities;
import net.bhapi.event.RegistryEvent;
import net.bhapi.util.Identifier;
import paulevs.bhcreative.api.CreativeTab;

import java.util.function.BiConsumer;

public class TabRegistryEvent extends RegistryEvent<Identifier, CreativeTab> {
	public TabRegistryEvent(BiConsumer<Identifier, CreativeTab> register) {
		super(register);
	}
	
	@Override
	public int getPriority() {
		return EventPriorities.AFTER_BLOCK_AND_ITEMS + 1;
	}
	
	public void register(CreativeTab tab) {
		this.register(tab.getID(), tab);
	}
}

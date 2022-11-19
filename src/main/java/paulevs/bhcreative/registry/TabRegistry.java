package paulevs.bhcreative.registry;

import net.bhapi.registry.Registry;
import net.bhapi.util.Identifier;
import paulevs.bhcreative.api.CreativeTab;

import java.util.ArrayList;
import java.util.List;

public class TabRegistry extends Registry<CreativeTab> {
	public static final TabRegistry INSTANCE = new TabRegistry();
	public static List<CreativeTab> orderedTabs = new ArrayList<>();
	
	private TabRegistry() {}
	
	@Override
	public CreativeTab register(Identifier id, CreativeTab tab) {
		orderedTabs.add(tab);
		return super.register(id, tab);
	}
	
	public CreativeTab getTabByIndex(int index) {
		return orderedTabs.get(index);
	}
	
	public int getTabsCount() {
		return orderedTabs.size();
	}
}

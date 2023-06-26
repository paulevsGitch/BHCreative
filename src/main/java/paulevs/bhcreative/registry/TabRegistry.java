package paulevs.bhcreative.registry;

import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.bhcreative.api.CreativeTab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabRegistry {
	private static Map<Identifier, CreativeTab> idToTab = new HashMap<>();
	private static List<CreativeTab> orderedTabs = new ArrayList<>();
	
	public static CreativeTab register(Identifier id, CreativeTab tab) {
		idToTab.put(id, tab);
		orderedTabs.add(tab);
		return tab;
	}
	
	public static CreativeTab getTabByIndex(int index) {
		return orderedTabs.get(index);
	}
	
	public static int getTabsCount() {
		return orderedTabs.size();
	}
}

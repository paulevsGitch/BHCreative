package paulevs.bhcreative.registry;

import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.bhcreative.BHCreative;
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
	
	public static void sort() {
		orderedTabs.sort((t1, t2) -> {
			Identifier id1 = t1.getID();
			Identifier id2 = t2.getID();
			if (id1.modID == id2.modID) {
				return id1.modID == BHCreative.MOD_ID ? 0 : id1.id.compareTo(id2.id);
			}
			if (id1.modID == BHCreative.MOD_ID) return -1;
			if (id2.modID == BHCreative.MOD_ID) return 1;
			return id1.modID.compareTo(id2.modID);
		});
	}
	
	public static CreativeTab getTabByIndex(int index) {
		return orderedTabs.get(index);
	}
	
	public static int getTabsCount() {
		return orderedTabs.size();
	}
}

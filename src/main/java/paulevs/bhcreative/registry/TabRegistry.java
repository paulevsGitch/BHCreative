package paulevs.bhcreative.registry;

import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import paulevs.bhcreative.BHCreative;
import paulevs.bhcreative.api.CreativeTab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabRegistry {
	private static final Map<Namespace, List<CreativeTab>> PRE_ORDERED_TABS = new HashMap<>();
	private static final List<CreativeTab> ORDERED_TABS = new ArrayList<>();
	
	public static void register(Identifier id, CreativeTab tab) {
		PRE_ORDERED_TABS.computeIfAbsent(id.namespace, k -> new ArrayList<>()).add(tab);
	}
	
	public static void sort() {
		PRE_ORDERED_TABS.keySet().stream().sorted((modID1, modID2) -> {
			if (modID1 == BHCreative.NAMESPACE) return -1;
			if (modID2 == BHCreative.NAMESPACE) return 1;
			return modID1.compareTo(modID2);
		}).forEach(id -> ORDERED_TABS.addAll(PRE_ORDERED_TABS.get(id)));
	}
	
	public static CreativeTab getTabByIndex(int index) {
		return ORDERED_TABS.get(index);
	}
	
	public static int getTabsCount() {
		return ORDERED_TABS.size();
	}
}

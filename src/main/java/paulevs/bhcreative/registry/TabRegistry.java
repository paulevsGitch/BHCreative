package paulevs.bhcreative.registry;

import com.google.common.collect.Lists;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.Registry;
import org.jetbrains.annotations.NotNull;
import paulevs.bhcreative.Creative;
import paulevs.bhcreative.api.CreativeTab;

import java.util.List;

public class TabRegistry extends Registry<CreativeTab> {
	public static final TabRegistry INSTANCE = new TabRegistry(Creative.id("tabs"));
	public static List<CreativeTab> orderedTabs = Lists.newArrayList();
	
	/**
	 * Default registry constructor.
	 *
	 * @param identifier registry's identifier.
	 */
	public TabRegistry(@NotNull Identifier identifier) {
		super(identifier);
	}
	
	@Override
	public void register(Identifier id, CreativeTab tab) {
		super.register(id, tab);
		orderedTabs.add(tab);
	}
	
	public CreativeTab getTabByIndex(int index) {
		return orderedTabs.get(index);
	}
	
	public int getTabsCount() {
		return orderedTabs.size();
	}
}

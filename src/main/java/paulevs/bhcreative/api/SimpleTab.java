package paulevs.bhcreative.api;

import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;

public class SimpleTab extends CreativeTab {
	private final ItemInstance icon;
	
	public SimpleTab(Identifier id, ItemInstance icon) {
		super(id);
		this.icon = icon;
	}
	
	public SimpleTab(Identifier id, ItemBase icon) {
		this(id, new ItemInstance(icon));
	}

	@Override
	public ItemInstance getIcon() {
		return icon;
	}
}

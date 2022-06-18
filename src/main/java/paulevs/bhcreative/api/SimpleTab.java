package paulevs.bhcreative.api;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import net.modificationstation.stationapi.api.registry.Identifier;

public class SimpleTab extends CreativeTab {
	private final ItemStack icon;
	
	public SimpleTab(Identifier id, ItemStack icon) {
		super(id);
		this.icon = icon;
	}
	
	public SimpleTab(Identifier id, ItemConvertible icon) {
		this(id, new ItemStack(icon.asItem()));
	}

	@Override
	public ItemStack getIcon() {
		return icon;
	}
}

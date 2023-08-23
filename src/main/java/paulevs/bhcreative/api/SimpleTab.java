package paulevs.bhcreative.api;

import net.minecraft.item.BaseItem;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.Identifier;

public class SimpleTab extends CreativeTab {
	private final ItemStack icon;
	
	public SimpleTab(Identifier id, ItemStack icon) {
		super(id);
		this.icon = icon;
	}
	
	public SimpleTab(Identifier id, BaseItem icon) {
		this(id, new ItemStack(icon));
	}

	@Override
	public ItemStack getIcon() {
		return icon;
	}
}

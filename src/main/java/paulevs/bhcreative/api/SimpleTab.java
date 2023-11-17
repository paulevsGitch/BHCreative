package paulevs.bhcreative.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class SimpleTab extends CreativeTab {
	private final ItemStack icon;
	
	public SimpleTab(Identifier id, ItemStack icon) {
		super(id);
		this.icon = icon;
	}
	
	public SimpleTab(Identifier id, Item icon) {
		this(id, new ItemStack(icon));
	}

	@Override
	public ItemStack getIcon() {
		return icon;
	}
}

package paulevs.bhcreative.api;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class CreativeTab implements Comparable<CreativeTab> {
	private final List<ItemStack> items = new ArrayList<>();
	private final String translationKey;
	private final Identifier id;
	
	public CreativeTab(Identifier id) {
		this.translationKey = "tab." + id.toString() + ".name";//.replace(':', '.');
		this.id = id;
	}
	
	public abstract ItemStack getIcon();
	
	public List<ItemStack> getItems() {
		return items;
	}
	
	public String getTranslationKey() {
		return translationKey;
	}
	
	public void addItem(ItemStack item) {
		items.add(item);
	}
	
	public Identifier getID() {
		return id;
	}
	
	@Override
	public int compareTo(CreativeTab tab) {
		String key1 = this.getTranslationKey();
		String key2 = tab.getTranslationKey();
		return key1.compareTo(key2);
	}
	
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "[tab, %s, %s]", id.modID, id.id);
	}
}

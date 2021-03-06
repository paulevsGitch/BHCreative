package paulevs.bhcreative.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;
import net.minecraft.block.BaseBlock;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import paulevs.bhcreative.Creative;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

import java.util.Optional;

public class VanillaTabListener {
	public static SimpleTab tabFullBlocks;
	public static SimpleTab tabOtherBlocks;
	public static SimpleTab tabTools;
	public static SimpleTab tabWeapons;
	public static SimpleTab tabResources;
	public static SimpleTab tabFood;
	public static SimpleTab tabItems;
	
	@EventListener(priority = ListenerPriority.HIGHEST)
	public void registerVanillaTabs(TabRegistryEvent event) {
		initFullBlocks(event);
		initNotFullBlocks(event);
		initTools(event);
		initWeapons(event);
		initResources(event);
		initFood(event);
		initItems(event);
	}
	
	private void initFullBlocks(TabRegistryEvent event) {
		tabFullBlocks = new SimpleTab(Creative.id("full_blocks"), (ItemConvertible) BaseBlock.STONE);
		event.register(tabFullBlocks);
		
		addItem(tabFullBlocks, "stone");
		addItem(tabFullBlocks, "grass_block");
		addItem(tabFullBlocks, "dirt");
		addItem(tabFullBlocks, "cobblestone");
		addItem(tabFullBlocks, "planks");
		addItem(tabFullBlocks, "bedrock");
		addItem(tabFullBlocks, "sand");
		addItem(tabFullBlocks, "gravel");
		addItem(tabFullBlocks, "gold_ore");
		addItem(tabFullBlocks, "iron_ore");
		addItem(tabFullBlocks, "coal_ore");
		addItem(tabFullBlocks, "log", 3);
		addItem(tabFullBlocks, "leaves", 3);
		addItem(tabFullBlocks, "sponge");
		addItem(tabFullBlocks, "glass");
		addItem(tabFullBlocks, "lapis_ore");
		addItem(tabFullBlocks, "lapis_block");
		addItem(tabFullBlocks, "dispenser");
		addItem(tabFullBlocks, "sandstone");
		addItem(tabFullBlocks, "note_block");
		addItem(tabFullBlocks, "wool", 16);
		addItem(tabFullBlocks, "gold_block");
		addItem(tabFullBlocks, "iron_block");
		addItem(tabFullBlocks, "double_slab");
		addItem(tabFullBlocks, "bricks");
		addItem(tabFullBlocks, "tnt");
		addItem(tabFullBlocks, "bookshelf");
		addItem(tabFullBlocks, "mossy_cobblestone");
		addItem(tabFullBlocks, "obsidian");
		addItem(tabFullBlocks, "spawner");
		addItem(tabFullBlocks, "chest");
		addItem(tabFullBlocks, "diamond_ore");
		addItem(tabFullBlocks, "diamond_block");
		addItem(tabFullBlocks, "crafting_table");
		addItem(tabFullBlocks, "furnace");
		addItem(tabFullBlocks, "redstone_ore");
		addItem(tabFullBlocks, "ice");
		addItem(tabFullBlocks, "snow_block");
		addItem(tabFullBlocks, "clay");
		addItem(tabFullBlocks, "jukebox");
		addItem(tabFullBlocks, "pumpkin");
		addItem(tabFullBlocks, "netherrack");
		addItem(tabFullBlocks, "soul_sand");
		addItem(tabFullBlocks, "glowstone");
		addItem(tabFullBlocks, "jack_o_lantern");
		addItem(tabFullBlocks, "locked_chest");
	}
	
	private void initNotFullBlocks(TabRegistryEvent event) {
		tabOtherBlocks = new SimpleTab(Creative.id("other_blocks"), (ItemConvertible) BaseBlock.LADDER);
		event.register(tabOtherBlocks);
		
		addItem(tabOtherBlocks, "sapling");
		addItem(tabOtherBlocks, "powered_rail");
		addItem(tabOtherBlocks, "detector_rail");
		addItem(tabOtherBlocks, "sticky_piston");
		addItem(tabOtherBlocks, "cobweb");
		addItem(tabOtherBlocks, "grass", 4);
		addItem(tabOtherBlocks, "piston");
		addItem(tabOtherBlocks, "dandelion");
		addItem(tabOtherBlocks, "rose");
		addItem(tabOtherBlocks, "brown_mushroom");
		addItem(tabOtherBlocks, "red_mushroom");
		addItem(tabOtherBlocks, "torch");
		addItem(tabOtherBlocks, "slab");
		addItem(tabOtherBlocks, "fire");
		addItem(tabOtherBlocks, "oak_stairs");
		addItem(tabOtherBlocks, "farmland");
		addItem(tabOtherBlocks, "ladder");
		addItem(tabOtherBlocks, "rail");
		addItem(tabOtherBlocks, "cobblestone_stairs");
		addItem(tabOtherBlocks, "lever");
		addItem(tabOtherBlocks, "oak_pressure_plate");
		addItem(tabOtherBlocks, "stone_pressure_plate");
		addItem(tabOtherBlocks, "button");
		addItem(tabOtherBlocks, "snow");
		addItem(tabOtherBlocks, "cactus");
		addItem(tabOtherBlocks, "fence");
	}
	
	private void initTools(TabRegistryEvent event) {
		tabTools = new SimpleTab(Creative.id("tools"), (ItemConvertible) ItemBase.ironPickaxe);
		event.register(tabTools);
		
		addItem(tabTools, "iron_shovel");
		addItem(tabTools, "iron_pickaxe");
		addItem(tabTools, "iron_axe");
		addItem(tabTools, "flint_and_steel");
		addItem(tabTools, "wooden_shovel");
		addItem(tabTools, "wooden_pickaxe");
		addItem(tabTools, "wooden_axe");
		addItem(tabTools, "stone_shovel");
		addItem(tabTools, "stone_pickaxe");
		addItem(tabTools, "stone_axe");
		addItem(tabTools, "diamond_shovel");
		addItem(tabTools, "diamond_pickaxe");
		addItem(tabTools, "diamond_axe");
		addItem(tabTools, "golden_shovel");
		addItem(tabTools, "golden_pickaxe");
		addItem(tabTools, "golden_axe");
		addItem(tabTools, "wooden_hoe");
		addItem(tabTools, "stone_hoe");
		addItem(tabTools, "iron_hoe");
		addItem(tabTools, "diamond_hoe");
		addItem(tabTools, "golden_hoe");
		addItem(tabTools, "fishing_rod");
		addItem(tabTools, "clock");
		addItem(tabTools, "compass");
		addItem(tabTools, "shears");
	}
	
	private void initWeapons(TabRegistryEvent event) {
		tabWeapons = new SimpleTab(Creative.id("weapons"), (ItemConvertible) ItemBase.ironSword);
		event.register(tabWeapons);
		
		addItem(tabWeapons, "bow");
		addItem(tabWeapons, "arrow");
		addItem(tabWeapons, "iron_sword");
		addItem(tabWeapons, "wooden_sword");
		addItem(tabWeapons, "stone_sword");
		addItem(tabWeapons, "diamond_sword");
		addItem(tabWeapons, "golden_sword");
		addItem(tabWeapons, "leather_helmet");
		addItem(tabWeapons, "leather_chestplate");
		addItem(tabWeapons, "leather_leggings");
		addItem(tabWeapons, "leather_boots");
		addItem(tabWeapons, "chainmail_helmet");
		addItem(tabWeapons, "chainmail_chestplate");
		addItem(tabWeapons, "chainmail_leggings");
		addItem(tabWeapons, "chainmail_boots");
		addItem(tabWeapons, "iron_helmet");
		addItem(tabWeapons, "iron_chestplate");
		addItem(tabWeapons, "iron_leggings");
		addItem(tabWeapons, "iron_boots");
		addItem(tabWeapons, "diamond_helmet");
		addItem(tabWeapons, "diamond_chestplate");
		addItem(tabWeapons, "diamond_leggings");
		addItem(tabWeapons, "diamond_boots");
		addItem(tabWeapons, "golden_helmet");
		addItem(tabWeapons, "golden_chestplate");
		addItem(tabWeapons, "golden_leggings");
		addItem(tabWeapons, "golden_boots");
	}
	
	private void initResources(TabRegistryEvent event) {
		tabResources = new SimpleTab(Creative.id("resources"), (ItemConvertible) ItemBase.ironIngot);
		event.register(tabResources);
		
		addItem(tabResources, "coal");
		addItem(tabResources, "diamond");
		addItem(tabResources, "iron_ingot");
		addItem(tabResources, "gold_ingot");
		addItem(tabResources, "stick");
		addItem(tabResources, "string");
		addItem(tabResources, "feather");
		addItem(tabResources, "gunpowder");
		addItem(tabResources, "flint");
		addItem(tabResources, "redstone");
		addItem(tabResources, "brick");
		addItem(tabResources, "clay_ball");
		addItem(tabResources, "paper");
		addItem(tabResources, "slime_ball");
		addItem(tabResources, "glowstone_dust");
		addItem(tabResources, "bone");
	}
	
	private void initFood(TabRegistryEvent event) {
		tabFood = new SimpleTab(Creative.id("food"), (ItemConvertible) ItemBase.apple);
		event.register(tabFood);
		
		addItem(tabFood, "apple");
		addItem(tabFood, "bowl");
		addItem(tabFood, "mushroom_stew");
		addItem(tabFood, "wheat_seeds");
		addItem(tabFood, "wheat");
		addItem(tabFood, "bread");
		addItem(tabFood, "porkchop");
		addItem(tabFood, "cooked_porkchop");
		addItem(tabFood, "golden_apple");
		addItem(tabFood, "egg");
		addItem(tabFood, "fish");
		addItem(tabFood, "cooked_fish");
		addItem(tabFood, "sugar");
		addItem(tabFood, "cookie");
	}
	
	private void initItems(TabRegistryEvent event) {
		tabItems = new SimpleTab(Creative.id("other_items"), (ItemConvertible) ItemBase.slimeball);
		event.register(tabItems);
		
		addItem(tabItems, "painting");
		addItem(tabItems, "sign");
		addItem(tabItems, "oak_door");
		addItem(tabItems, "bucket");
		addItem(tabItems, "water_bucket");
		addItem(tabItems, "lava_bucket");
		addItem(tabItems, "minecart");
		addItem(tabItems, "saddle");
		addItem(tabItems, "iron_door");
		addItem(tabItems, "snowball");
		addItem(tabItems, "oak_boat");
		addItem(tabItems, "leather");
		addItem(tabItems, "milk_bucket");
		addItem(tabItems, "sugar_cane");
		addItem(tabItems, "book");
		addItem(tabItems, "chest_minecart");
		addItem(tabItems, "furnace_minecart");
		addItem(tabItems, "compass");
		addItem(tabItems, "dye");
		addItem(tabItems, "cake");
		addItem(tabItems, "bed");
		addItem(tabItems, "repeater");
		addItem(tabItems, "map");
	}
	
	private void addItem(CreativeTab tab, String name) {
		Optional<ItemBase> optional = ItemRegistry.INSTANCE.get(Identifier.of(name));
		if (!optional.isPresent()) return;
		tab.addItem(new ItemStack(optional.get()));
	}
	
	private void addItem(CreativeTab tab, String name, int variants) {
		Optional<ItemBase> optional = ItemRegistry.INSTANCE.get(Identifier.of(name));
		if (!optional.isPresent()) return;
		for (byte i = 0; i < variants; i++) {
			tab.addItem(new ItemStack(optional.get(), 1, i));
		}
	}
}

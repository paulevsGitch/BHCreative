package paulevs.bhcreative.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.bhcreative.BHCreative;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

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
		BHCreative.LOGGER.info("Registering vanilla tabs");
		initFullBlocks(event);
		initNotFullBlocks(event);
		initTools(event);
		initWeapons(event);
		initResources(event);
		initFood(event);
		initItems(event);
	}
	
	private void initFullBlocks(TabRegistryEvent event) {
		tabFullBlocks = new SimpleTab(BHCreative.id("full_blocks"), new ItemStack(Block.STONE));
		event.register(tabFullBlocks);
		
		addItem(tabFullBlocks, "stone");
		addItem(tabFullBlocks, "grass_block");
		addItem(tabFullBlocks, "dirt");
		addItem(tabFullBlocks, "cobblestone");
		addItem(tabFullBlocks, "oak_planks");
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
		addItem(tabFullBlocks, "netherrack");
		addItem(tabFullBlocks, "soul_sand");
		addItem(tabFullBlocks, "glowstone");
		addItem(tabFullBlocks, "jack_o_lantern");
		addItem(tabFullBlocks, "locked_chest");
	}
	
	private void initNotFullBlocks(TabRegistryEvent event) {
		tabOtherBlocks = new SimpleTab(BHCreative.id("other_blocks"), new ItemStack(Block.LADDER));
		event.register(tabOtherBlocks);
		
		addItem(tabOtherBlocks, "sapling", 3);
		
		addItem(tabOtherBlocks, "dead_bush");
		addItem(tabOtherBlocks, "bhcreative:tall_grass");
		addItem(tabOtherBlocks, "bhcreative:fern");
		
		addItem(tabOtherBlocks, "dandelion");
		addItem(tabOtherBlocks, "rose");
		addItem(tabOtherBlocks, "brown_mushroom");
		addItem(tabOtherBlocks, "red_mushroom");
		addItem(tabOtherBlocks, "cactus");
		addItem(tabOtherBlocks, "cobweb");
		addItem(tabOtherBlocks, "torch");
		addItem(tabOtherBlocks, "redstone_torch_lit");
		addItem(tabOtherBlocks, "lever");
		addItem(tabOtherBlocks, "ladder");
		addItem(tabOtherBlocks, "rail");
		addItem(tabOtherBlocks, "powered_rail");
		addItem(tabOtherBlocks, "detector_rail");
		addItem(tabOtherBlocks, "slab", 4);
		addItem(tabOtherBlocks, "oak_stairs");
		addItem(tabOtherBlocks, "cobblestone_stairs");
		addItem(tabOtherBlocks, "oak_fence");
		addItem(tabOtherBlocks, "snow");
		addItem(tabOtherBlocks, "piston");
		addItem(tabOtherBlocks, "sticky_piston");
		addItem(tabOtherBlocks, "oak_pressure_plate");
		addItem(tabOtherBlocks, "stone_pressure_plate");
		addItem(tabOtherBlocks, "stone_button");
		addItem(tabOtherBlocks, "painting");
		addItem(tabOtherBlocks, "oak_sign");
		addItem(tabOtherBlocks, "oak_door");
		addItem(tabOtherBlocks, "oak_trapdoor");
		addItem(tabOtherBlocks, "iron_door");
		addItem(tabOtherBlocks, "red_bed");
		addItem(tabOtherBlocks, "repeater");
	}
	
	private void initTools(TabRegistryEvent event) {
		tabTools = new SimpleTab(BHCreative.id("tools"), new ItemStack(Item.ironPickaxe));
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
		tabWeapons = new SimpleTab(BHCreative.id("weapons"), new ItemStack(Item.ironSword));
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
		tabResources = new SimpleTab(BHCreative.id("resources"), new ItemStack(Item.ironIngot));
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
		tabFood = new SimpleTab(BHCreative.id("food"), new ItemStack(Item.apple));
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
		addItem(tabFood, "cod");
		addItem(tabFood, "cooked_cod");
		addItem(tabFood, "sugar");
		addItem(tabFood, "cookie");
		addItem(tabFood, "milk_bucket");
		addItem(tabFood, "cake");
	}
	
	private void initItems(TabRegistryEvent event) {
		tabItems = new SimpleTab(BHCreative.id("other_items"), new ItemStack(Item.slimeball));
		event.register(tabItems);
		
		addItem(tabItems, "bucket");
		addItem(tabItems, "water_bucket");
		addItem(tabItems, "lava_bucket");
		addItem(tabItems, "minecart");
		addItem(tabItems, "chest_minecart");
		addItem(tabItems, "furnace_minecart");
		addItem(tabItems, "saddle");
		addItem(tabItems, "snowball");
		addItem(tabItems, "oak_boat");
		addItem(tabItems, "leather");
		addItem(tabItems, "sugar_cane");
		addItem(tabItems, "book");
		addItem(tabItems, "compass");
		addItem(tabItems, "dye", 16);
		addItem(tabItems, "map");
		addItem(tabItems, "music_disc_13");
		addItem(tabItems, "music_disc_cat");
	}
	
	private void addItem(CreativeTab tab, String name) {
		Item item = ItemRegistry.INSTANCE.get(Identifier.of(name));
		if (item != null) tab.addItem(new ItemStack(item));
	}
	
	private void addItem(CreativeTab tab, String name, int meta) {
		Item item = ItemRegistry.INSTANCE.get(Identifier.of(name));
		if (item == null) return;
		for (byte i = 0; i < meta; i++) {
			tab.addItem(new ItemStack(item, 1, i));
		}
	}
}
